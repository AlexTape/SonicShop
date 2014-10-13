package de.alextape.sonicshop.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.connectivity.ClassPools;
import de.alextape.sonicshop.connectivity.ConnectionPool;
import de.alextape.sonicshop.methods.MD5;
import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;

/**
 * The Class RegistrationHandler.
 */
@WebServlet("/RegistrationHandler")
public class RegistrationHandler extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2554923405032257579L;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    // protected void doGet(HttpServletRequest request,
    // HttpServletResponse response) throws ServletException, IOException {
    // log.warn("RegistrationHanlder recognized unauthed GET request");
    // }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // if recieved form valid?
        boolean isValid = false;

        Map<String, String[]> registrationData = request.getParameterMap();
        if (registrationData.containsKey("name") == true
                && registrationData.containsKey("lastname") == true
                && registrationData.containsKey("email") == true
                && registrationData.containsKey("password") == true
                && registrationData.containsKey("password2") == true
                && registrationData.containsKey("street") == true
                && registrationData.containsKey("place") == true
                && registrationData.containsKey("plz") == true
                && registrationData.containsKey("country") == true
                && (registrationData.size() >= 9 && registrationData.size() <= 10)) {
            if (registrationData.containsKey("agbs") == true) {
                if (registrationData.size() == 10) {
                    log.debug("RegistrationHandler all parameters are there");
                    isValid = true;
                } else {
                    notValid(response);
                    return;
                }
            } else {
                if (registrationData.size() == 9) {
                    log.debug("RegistrationHandler validate form without agbs");
                    notValid(response);
                    return;
                } else {
                    notValid(response);
                    return;
                }
            }
        } else {
            notValid(response);
        }
        if (isValid) {
            // validate data
            for (Map.Entry<String, String[]> entry : registrationData
                    .entrySet()) {
                String[] recieved = entry.getValue();
                if (recieved[0] == null) {
                    notValid(response);
                    return;
                }
                if (recieved[0].equals("") == true) {
                    notValid(response);
                    return;
                }
            }
            if (request.getParameter("password").equals(
                    request.getParameter("password2"))) {
                log.debug("RegistrationHandler passwords match, no parameter is null");
                if (registerUser(registrationData)) {
                    RequestDispatcher rd = request
                            .getRequestDispatcher("/LoginHandler");
                    rd.forward(request, response);
                    return;
                }

            } else {
                notValid(response);
                return;
            }
        }
    }

    /**
     * Not valid.
     *
     * @param response
     *            the response
     */
    private void notValid(HttpServletResponse response) {
        log.debug("RegistrationHanlder recieved incomplete data");
        redirect(response);
    }

    /**
     * Redirect.
     *
     * @param response
     *            the response
     */
    private void redirect(HttpServletResponse response) {
        try {
            response.sendRedirect("/Webshop/Main?forward=registrationView.jsp&validate=false");
        } catch (IOException e) {
            log.warn("RegistrationHanlder is not able to redirect user");
        }
    }

    /**
     * Register user.
     *
     * @param registrationData
     *            the registration data
     * @return true, if successful
     */
    private boolean registerUser(Map<String, String[]> registrationData) {

        // connectionPool reference
        ConnectionPool pool = null;

        // shell for connection
        Connection con = null;

        log.debug("RegistrationHandler tries to validate credentials");
        log.debug("RegistrationHandler try to reach relevant pool");
        if (pool == null) {
            try {
                log.debug("RegistrationHandler initialize ClassPools.PAGE");
                ClassPools.initialize(SecurityLevel.PAGE);
                log.debug("RegistrationHandler gets Pool of ClassPools.PAGE");
                pool = ClassPools.getPool(SecurityLevel.PAGE);
            } catch (Exception e) {
                log.warn("RegistrationHandler could not initialize Connection Pool");
            }
        }
        log.debug("RegistrationHandler try to get connection");
        try {
            con = pool.getConnection();

            log.debug("RegistrationHandler db connection established");
            // enable transaction
            con.setAutoCommit(false);

            log.debug("RegistrationHandler transaction start to add a new user");

            String prprdQuery;

            prprdQuery = "INSERT INTO UserTab (EMail, Name, LastName) VALUES (?, ?, ?);";
            log.debug("RegistrationHandler doing: " + prprdQuery);

            PreparedStatement registerUser;
            registerUser = con.prepareStatement(prprdQuery);
            registerUser.setString(1, (registrationData.get("email"))[0]);
            registerUser.setString(2, (registrationData.get("name"))[0]);
            registerUser.setString(3, (registrationData.get("lastname"))[0]);
            registerUser.executeUpdate();

            log.debug("RegistrationHandler result: updated");

            prprdQuery = "SELECT UserID FROM UserTab WHERE Email=?";
            log.debug("RegistrationHandler doing: " + prprdQuery);

            registerUser = con.prepareStatement(prprdQuery);
            registerUser.setString(1, (registrationData.get("email"))[0]);
            ResultSet resultSet = registerUser.executeQuery();

            resultSet.next();
            int userID = resultSet.getInt("UserID");

            log.debug("RegistrationHandler result: userID: " + userID);

            prprdQuery = "INSERT INTO AddressTab (UserID, Street, PLZ, Place, Country) VALUES (?, ?, ?, ?, ?);";
            log.debug("RegistrationHandler doing: " + prprdQuery);

            registerUser = con.prepareStatement(prprdQuery);
            registerUser.setInt(1, userID);
            registerUser.setString(2, (registrationData.get("street"))[0]);
            registerUser.setInt(3,
                    Integer.parseInt((registrationData.get("plz"))[0]));
            registerUser.setString(4, (registrationData.get("place"))[0]);
            registerUser.setString(5, (registrationData.get("country"))[0]);
            registerUser.executeUpdate();

            log.debug("RegistrationHandler result: updated");

            prprdQuery = "INSERT INTO AgbTab (UserID, AGBaccepted) VALUES (?, ?);";
            log.debug("RegistrationHandler doing: " + prprdQuery);

            registerUser = con.prepareStatement(prprdQuery);
            registerUser.setInt(1, userID);
            registerUser.setBoolean(2,
                    Boolean.parseBoolean((registrationData.get("agbs"))[0]));
            registerUser.executeUpdate();

            log.debug("RegistrationHandler result: updated");

            prprdQuery = "INSERT INTO AuthTab (UserID, PW) VALUES (?, ?);";
            log.debug("RegistrationHandler doing: " + prprdQuery);

            // md5 encrypt
            MD5 encryptor = new MD5();
            String newPassword = encryptor.md5((registrationData
                    .get("password"))[0]);

            registerUser = con.prepareStatement(prprdQuery);
            registerUser.setInt(1, userID);
            registerUser.setString(2, newPassword);
            registerUser.executeUpdate();

            log.debug("RegistrationHandler result: updated");

            prprdQuery = "INSERT INTO RightsTab (UserID, AdminRight, Supporter) VALUES (?, false, false);";
            log.debug("RegistrationHandler doing: " + prprdQuery);
            registerUser = con.prepareStatement(prprdQuery);
            registerUser.setInt(1, userID);
            registerUser.executeUpdate();

            log.debug("RegistrationHandler result: updated");

            con.commit();

            log.debug("RegistrationHandler result: Transaction complete");

            log.debug("RegistrationHandler transaction complete");
        } catch (Exception e1) {
            log.debug(e1.toString());
            try {
                log.debug("RegistrationHandler transaction error - try rollback");
                con.rollback();
            } catch (Exception e2) {
                log.warn("RegistrationHandler rollback error - transaction aborted with: "
                        + e2.toString());
            }
        } finally {
            if (con != null) {
                // return connection to pool
                pool.returnConnection(con);
                log.debug("RegistrationHandler db connection returned to pool");
            }
        }
        return true;
    }

}
