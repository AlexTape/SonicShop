package de.alextape.sonicshop.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
 * The Class PasswordChanger.
 */
@WebServlet("/PasswordChanger")
public class PasswordChanger extends ProtectedResource {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3999943207360554614L;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /**
     * Change password.
     *
     * @param email
     *            the email
     * @param newPassword
     *            the new password
     * @param userID
     *            the user id
     * @return true, if successful
     */
    private boolean changePassword(String email, String newPassword, int userID) {

        // md5 encrypt
        MD5 encryptor = new MD5();
        newPassword = encryptor.md5(newPassword);

        // connectionPool reference
        ConnectionPool pool = null;

        // shell for connection
        Connection con = null;

        log.debug("PasswordChanger tries to change password for " + email);
        log.debug("PasswordChanger try to reach relevant pool");
        if (pool == null) {
            try {
                log.debug("PasswordChanger initialize ClassPools.PAGE");
                ClassPools.initialize(SecurityLevel.PAGE);
                log.debug("PasswordChanger gets Pool of ClassPools.PAGE");
                pool = ClassPools.getPool(SecurityLevel.PAGE);
            } catch (Exception e) {
                log.warn("PasswordChanger could not initialize Connection Pool");
            }
        }
        log.debug("PasswordChanger try to get connection");
        try {
            con = pool.getConnection();

            log.debug("PasswordChanger db connection established");
            // enable transaction
            con.setAutoCommit(false);

            log.debug("PasswordChanger transaction start to add a new user");

            String prprdQuery;

            prprdQuery = "UPDATE AuthTab SET pw=? WHERE UserID=?";
            log.debug("PasswordChanger doing: " + prprdQuery);

            PreparedStatement changePassword;
            changePassword = con.prepareStatement(prprdQuery);
            changePassword.setString(1, newPassword);
            changePassword.setInt(2, userID);
            changePassword.executeUpdate();

            log.debug("PasswordChanger result: updated");

            con.commit();

            log.debug("PasswordChanger result: Transaction complete");

            log.debug("PasswordChanger transaction complete");
        } catch (Exception e1) {
            log.debug(e1.toString());
            try {
                log.debug("PasswordChanger transaction error - try rollback");
                con.rollback();
            } catch (Exception e2) {
                log.warn("PasswordChanger rollback error - transaction aborted with: "
                        + e2.toString());
            }
        } finally {
            if (con != null) {
                // return connection to pool
                pool.returnConnection(con);
                log.debug("PasswordChanger db connection returned to pool");
            }
        }
        return true;
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
        log.debug("PasswordChanger initialized");

        // get session
        HttpSession session = request.getSession();

        // cast back the user object
        DefaultUser user = null;
        try {
            user = (DefaultUser) session.getAttribute("login.object");
            log.info("PasswordChanger " + user.getEmail()
                    + " try to initialize as " + user.getRole().toString());
        } catch (Exception e) {
            log.debug("PasswordChanger invoke with invalid user object");
            log.warn("PasswordChanger error");
            RequestDispatcher rd = request.getRequestDispatcher("/Main");
            rd.forward(request, response);
        }
        if (user != null) {
            // authed
            log.debug("PasswordChanger creates user bean");
            IndexBean userBean = new IndexBean(user);
            request.setAttribute("IndexBean", userBean);
            RequestDispatcher dispatchUser = request
                    .getRequestDispatcher("passwordView.jsp");
            try {
                dispatchUser.include(request, response);
            } catch (ServletException e1) {
                log.warn("PasswordChanger error: " + e1.toString());
                e1.printStackTrace();
            } catch (IOException e1) {
                log.warn("PasswordChanger error: " + e1.toString());
                e1.printStackTrace();
            }
            return;
        } else {
            log.debug("PasswordChanger invoke with invalid user object");
            log.warn("PasswordChanger error");
            try {
                RequestDispatcher rd = request.getRequestDispatcher("/Main");
                rd.forward(request, response);
            } catch (IllegalStateException e) {
                log.warn("PasswordChanger unkown request");
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

        log.debug("PasswordChanger is changing the password");

        String email = request.getParameter("email");
        String oldPassword = request.getParameter("password_old");
        String newPassword = request.getParameter("password");
        String newPassword2 = request.getParameter("password2");

        // get session
        HttpSession session = request.getSession();

        // check session is authed
        DefaultUser thisUser = (DefaultUser) session
                .getAttribute("login.object");

        if (email.isEmpty() == false) {
            if (oldPassword.isEmpty() == false) {
                if ((newPassword.isEmpty() == false)
                        && (newPassword.equals(newPassword2))) {
                    if (changePassword(email, newPassword, thisUser.getUserID())) {
                        IndexBean userBean = new IndexBean(thisUser);
                        request.setAttribute("IndexBean", userBean);
                        RequestDispatcher rd = request
                                .getRequestDispatcher("/doneView.jsp");
                        rd.forward(request, response);
                        return;
                    }
                }
            }
        }
        log.debug("PasswordChanger detected invalid data");
        RequestDispatcher rd = request.getRequestDispatcher("/PasswordChanger");
        rd.forward(request, response);
        return;
    }

}
