package de.alextape.sonicshop.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.connectivity.ClassPools;
import de.alextape.sonicshop.connectivity.ConnectionPool;
import de.alextape.sonicshop.methods.MD5;
import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;
import de.alextape.sonicshop.userTypes.DefaultUser;
import de.alextape.sonicshop.viewBeans.IndexBean;

/**
 * The Class AccountDeleter.
 */
@WebServlet("/AccountDeleter")
public class AccountDeleter extends ProtectedResource {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3999943207360554614L;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /**
     * Delete user.
     *
     * @param userID
     *            the user id
     * @param password
     *            the password
     * @return true, if successful
     */
    private boolean deleteUser(int userID, String password) {

        // connectionPool reference
        ConnectionPool pool = null;

        // shell for connection
        Connection con = null;

        // returnValue
        boolean returnThis = true;

        log.debug("AccountDeleter tries to delete user with UserID:" + userID);
        log.debug("AccountDeleter try to reach relevant pool");
        if (pool == null) {
            try {
                log.debug("AccountDeleter initialize ClassPools.PAGE");
                ClassPools.initialize(SecurityLevel.PAGE);
                log.debug("AccountDeleter gets Pool of ClassPools.PAGE");
                pool = ClassPools.getPool(SecurityLevel.PAGE);
            } catch (Exception e) {
                log.warn("AccountDeleter could not initialize Connection Pool");
            }
        }
        log.debug("AccountDeleter try to get connection");
        try {
            con = pool.getConnection();

            log.debug("AccountDeleter db connection established");
            // enable transaction
            con.setAutoCommit(false);

            log.debug("AccountDeleter transaction start to delete this user");

            String prprdQuery;

            prprdQuery = "SELECT UserID FROM AuthTab WHERE PW=?;";
            log.debug("AccountDeleter doing: " + prprdQuery);

            MD5 encryptor = new MD5();
            password = encryptor.md5(password);

            PreparedStatement changePassword;
            changePassword = con.prepareStatement(prprdQuery);
            changePassword.setString(1, password);
            ResultSet resultSet = changePassword.executeQuery();

            resultSet.next();

            int dbUserID = resultSet.getInt("UserID");

            log.debug("AccountDeleter UserID: " + userID + " dbUserID: "
                    + dbUserID);

            if (dbUserID != userID) {
                changePassword.cancel();
                // abort it
            }

            // now make the dish ;)
            prprdQuery = "DELETE FROM AGBTab WHERE UserID=?;";
            log.debug("AccountDeleter doing: " + prprdQuery);
            changePassword = con.prepareStatement(prprdQuery);
            changePassword.setInt(1, dbUserID);
            changePassword.executeUpdate();
            log.debug("AccountDeleter result: updated");

            prprdQuery = "DELETE FROM AddressTab WHERE UserID=?;";
            log.debug("AccountDeleter doing: " + prprdQuery);
            changePassword = con.prepareStatement(prprdQuery);
            changePassword.setInt(1, dbUserID);
            changePassword.executeUpdate();
            log.debug("AccountDeleter result: updated");

            prprdQuery = "DELETE FROM AuthTab WHERE UserID=?;";
            log.debug("AccountDeleter doing: " + prprdQuery);
            changePassword = con.prepareStatement(prprdQuery);
            changePassword.setInt(1, dbUserID);
            changePassword.executeUpdate();
            log.debug("AccountDeleter result: updated");

            prprdQuery = "DELETE FROM RightsTab WHERE UserID=?;";
            log.debug("AccountDeleter doing: " + prprdQuery);
            changePassword = con.prepareStatement(prprdQuery);
            changePassword.setInt(1, dbUserID);
            changePassword.executeUpdate();
            log.debug("AccountDeleter result: updated");

            prprdQuery = "UPDATE OrderTab SET UserID=0 WHERE UserID=?;";
            log.debug("AccountDeleter doing: " + prprdQuery);
            changePassword = con.prepareStatement(prprdQuery);
            changePassword.setInt(1, dbUserID);
            changePassword.executeUpdate();
            log.debug("AccountDeleter result: updated");

            prprdQuery = "DELETE FROM UserTab WHERE UserID=?;";
            log.debug("AccountDeleter doing: " + prprdQuery);
            changePassword = con.prepareStatement(prprdQuery);
            changePassword.setInt(1, dbUserID);
            changePassword.executeUpdate();
            log.debug("AccountDeleter result: updated");

            con.commit();

            log.debug("AccountDeleter transaction complete");
        } catch (Exception e1) {
            log.debug(e1.toString());
            returnThis = false;
            try {
                log.debug("AccountDeleter transaction error - try rollback");
                con.rollback();
            } catch (Exception e2) {
                log.warn("AccountDeleter rollback error - transaction aborted with: "
                        + e2.toString());
            }
        } finally {
            if (con != null) {
                // return connection to pool
                pool.returnConnection(con);
                log.debug("AccountDeleter db connection returned to pool");
            }
        }
        return returnThis;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * controller.ProtectedResource#doGet(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // log
        log.debug("AccountDeleter initialized");

        // get session
        HttpSession session = request.getSession();

        // cast back the user object
        DefaultUser user = null;
        try {
            user = (DefaultUser) session.getAttribute("login.object");
            log.info("AccountDeleter " + user.getEmail()
                    + " try to initialize as " + user.getRole().toString());
        } catch (Exception e) {
            log.debug("AccountDeleter invoke with invalid user object");
            log.warn("AccountDeleter error");
            RequestDispatcher rd = request.getRequestDispatcher("/Main");
            rd.forward(request, response);
        }
        if (user != null) {
            // authed
            log.debug("AccountDeleter creates user bean");
            IndexBean userBean = new IndexBean(user);
            request.setAttribute("IndexBean", userBean);
            RequestDispatcher dispatchUser = request
                    .getRequestDispatcher("deleterView.jsp");
            try {
                dispatchUser.include(request, response);
            } catch (ServletException e1) {
                log.warn("AccountDeleter error: " + e1.toString());
                e1.printStackTrace();
            } catch (IOException e1) {
                log.warn("AccountDeleter error: " + e1.toString());
                e1.printStackTrace();
            }
            return;
        } else {
            log.debug("AccountDeleter invoke with invalid user object");
            log.warn("AccountDeleter error");
            try {
                RequestDispatcher rd = request.getRequestDispatcher("/Main");
                rd.forward(request, response);
            } catch (IllegalStateException e) {
                log.warn("AccountDeleter unkown request");
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * controller.ProtectedResource#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        log.debug("AccountDeleter is changing the password");

        String password = request.getParameter("password");

        // get session
        HttpSession session = request.getSession();

        // check session is authed
        DefaultUser thisUser = (DefaultUser) session
                .getAttribute("login.object");

        if (password.isEmpty() == false) {
            if (deleteUser(thisUser.getUserID(), password)) {
                // invalidate session
                session.invalidate();
                response.sendRedirect("/Webshop/Main?deleted=true");
                return;
            }
        }
        log.debug("AccountDeleter detected invalid data");
        RequestDispatcher rd = request.getRequestDispatcher("/Dashboard");
        rd.forward(request, response);
        return;
    }

}
