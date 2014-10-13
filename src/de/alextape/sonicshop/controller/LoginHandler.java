package de.alextape.sonicshop.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.connectivity.ClassPools;
import de.alextape.sonicshop.connectivity.ConnectionPool;
import de.alextape.sonicshop.methods.MD5;
import de.alextape.sonicshop.modell.UserFactory;
import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;
import de.alextape.sonicshop.userTypes.DefaultUser;

/**
 * The Class LoginHandler.
 */
@WebServlet("/LoginHandler")
public class LoginHandler extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1490383214890879462L;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    // logout via get request
    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String parameter = request.getParameter("logout");
        if (parameter != null && parameter.compareTo("true") == 0) {
            // get session
            HttpSession session = request.getSession();
            // logout
            session.invalidate();
            // redirect to index
            response.sendRedirect("/Webshop/Main");
        } else {
            doPost(request, response);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        // read credentials
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // check credentials

        if (!validateUser(email, password)) {

            // non valid credentials
            log.info("login try for \"" + email
                    + "\": invalid credentials detected");
            response.sendRedirect("/Webshop/Main?forward=loginView.jsp");

        } else {

            log.info("LoginHandler authed \"" + email + "\" successfull");

            // get user permissions
            DefaultUser connectedUser = getUserPermissions(email);

            // valid credentials
            HttpSession session = request.getSession();

            // redirect to target page
            try {
                String target = (String) session.getAttribute("login.target");
                if (target == null) {
                    // user dashboard
                    target = "/Webshop/Dashboard";
                }
                log.debug("LoginHandler \"" + connectedUser.getEmail()
                        + "\" try to get " + target
                        + " logged in as [ObjectID:" + connectedUser.toString()
                        + "|SessionID: " + session.getId() + "]");
                log.debug("LoginHandler dispatch to: " + target);

                // relate user to session
                session.setAttribute("login.object", connectedUser);
                RequestDispatcher dispatchThis = request
                        .getRequestDispatcher("Main");
                log.debug("LoginHandler dispatching "
                        + connectedUser.toString() + " now");
                dispatchThis.include(request, response);
            } catch (Exception ignored) {
                // default page if dispatch is not possible
                log.warn("LoginHandler error due dispatching to resource");
                response.sendRedirect("/Webshop/Main?forward=loginView.jsp");
            }
        }

    }

    /**
     * Gets the user permissions.
     *
     * @param email
     *            the email
     * @return the user permissions
     */
    public DefaultUser getUserPermissions(String email) {

        // connectionPool reference
        ConnectionPool pool = null;

        // shell for connection
        Connection con = null;

        // DefaultUser holder
        DefaultUser connectedUser = null;

        log.debug("LoginHandler tries to get user data and permissions");
        log.debug("LoginHandler try to reach relevant pool");
        if (pool == null) {
            try {
                log.debug("LoginHandler initialize ClassPools.PAGE");
                ClassPools.initialize(SecurityLevel.PAGE);
                log.debug("LoginHandler gets Pool of ClassPools.PAGE");
                pool = ClassPools.getPool(SecurityLevel.PAGE);
            } catch (Exception e) {
                log.warn("LoginHandler could not initialize Connection Pool");
            }
        }
        log.debug("LoginHandler try to get connection");
        try {
            con = pool.getConnection();
            log.debug("LoginHandler db connection established");
            // enable transaction
            con.setAutoCommit(false);
            log.debug("LoginHandler transaction start: getUserPermissions()");
            String prprdQuery = "select * from usertab inner join rightstab on usertab.userid = rightstab.userid  where LOWER(usertab.email)=LOWER(?);";
            PreparedStatement userDataQuery = con.prepareStatement(prprdQuery);
            userDataQuery.setString(1, email);
            ResultSet resultSet = userDataQuery.executeQuery();
            log.debug("LoginHandler doing: " + prprdQuery);
            // compare
            resultSet.next();
            // get the initialized user as DefaultUser via UserFactory
            connectedUser = new UserFactory().getUser(
                    resultSet.getInt("userid"), resultSet.getString("name"),
                    resultSet.getString("lastname"),
                    resultSet.getString("email"),
                    resultSet.getBoolean("adminright"),
                    resultSet.getBoolean("supporter"));

            con.commit();
            log.debug("LoginHandler transaction complete");
        } catch (Exception e1) {
            log.debug(e1.toString());
            // if error during transaction rollback()
            try {
                log.debug("LoginHandler transaction error - try rollback");
                con.rollback();
            } catch (Exception e2) {
                log.warn("LoginHandler rollback error - transaction aborted with: "
                        + e2.toString());
            }
        } finally {
            if (con != null) {
                // return connection to pool
                pool.returnConnection(con);
                log.debug("LoginHandler db connection returned to pool");
            }
        }

        // return validate boolean
        return connectedUser;
    }

    /**
     * Validate user.
     *
     * @param email
     *            the email
     * @param webPassword
     *            the web password
     * @return true, if successful
     */
    public boolean validateUser(String email, String webPassword) {

        // connectionPool reference
        ConnectionPool pool = null;

        // shell for connection
        Connection con = null;

        // dbPasword holder
        String dbPassword = null;

        log.debug("LoginHandler tries to validate credentials");
        log.debug("LoginHandler try to reach relevant pool");
        if (pool == null) {
            try {
                log.debug("LoginHandler initialize ClassPools.PAGE");
                ClassPools.initialize(SecurityLevel.PAGE);
                log.debug("LoginHandler gets Pool of ClassPools.PAGE");
                pool = ClassPools.getPool(SecurityLevel.PAGE);
            } catch (Exception e) {
                log.warn("LoginHandler could not initialize Connection Pool");
            }
        }
        log.debug("LoginHandler try to get connection");
        try {
            con = pool.getConnection();

            log.debug("LoginHandler db connection established");
            // enable transaction
            con.setAutoCommit(false);

            log.debug("LoginHandler transaction start: validateUser()");

            String prprdQuery = "select pw from usertab inner join authtab on usertab.userid = authtab.userid where LOWER(usertab.email)=LOWER(?);";

            log.debug("LoginHandler doing: " + prprdQuery);

            PreparedStatement passwordQuery;
            passwordQuery = con.prepareStatement(prprdQuery);

            passwordQuery.setString(1, email);

            ResultSet resultSet = passwordQuery.executeQuery();

            resultSet.next();

            dbPassword = resultSet.getString("pw");

            con.commit();

            log.debug("LoginHandler transaction complete");
        } catch (Exception e1) {
            log.debug(e1.toString());
            try {
                log.debug("LoginHandler transaction error - try rollback");
                con.rollback();
            } catch (Exception e2) {
                log.warn("LoginHandler rollback error - transaction aborted with: "
                        + e2.toString());
            }
        } finally {
            if (con != null) {
                // return connection to pool
                pool.returnConnection(con);
                log.debug("LoginHandler db connection returned to pool");
            }
        }

        // is true if credentials are valid
        boolean returnThis = false;
        // md5 encrypt
        MD5 encryptor = new MD5();
        webPassword = encryptor.md5(webPassword);
        try {
            returnThis = dbPassword.equals(webPassword);
        } catch (NullPointerException e) {
            log.info("LoginHandler could not recieve password");
        }
        return returnThis;
    }
}
