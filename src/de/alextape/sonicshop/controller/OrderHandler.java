package de.alextape.sonicshop.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.catalog.CatalogItem;
import de.alextape.sonicshop.connectivity.ClassPools;
import de.alextape.sonicshop.connectivity.ConnectionPool;
import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;
import de.alextape.sonicshop.userTypes.DefaultUser;

/**
 * The Class OrderHandler.
 */
@WebServlet("/OrderHandler")
public class OrderHandler extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6649258846944603021L;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /** The pool. */
    private ConnectionPool pool = null;

    // TODO change to doPost because of security issue
    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // shell for connection
        Connection con = null;

        // get session
        HttpSession session = request.getSession();

        // check session is authed
        Object knowHim = session.getAttribute("login.object");
        DefaultUser user = null;

        // get items
        try {
            user = (DefaultUser) knowHim;
        } catch (Exception e3) {
            try {
                response.sendRedirect("/Webshop/Main?orderResult=false");
            } catch (IOException e) {
                log.warn("RegistrationHanlder is not able to redirect user");
            }
        }
        ArrayList<CatalogItem> card;
        try {
            card = user.getShoppingCard().getItems();
        } catch (NullPointerException e3) {
            log.debug("OrderHandler detected invalid userdata");
            response.sendRedirect("/Webshop/Main");
            return;
        }

        // get a bucket for the recieved entitys
        log.debug("OrderHandler Request");
        log.debug("OrderHandler try to reach relevant pool");
        if (pool == null) {
            try {
                log.debug("OrderHandler initialize ClassPools.PAGE");
                ClassPools.initialize(SecurityLevel.PAGE);
                log.debug("OrderHandler gets Pool of ClassPools.PAGE");
                pool = ClassPools.getPool(SecurityLevel.PAGE);
            } catch (Exception e) {
                log.warn("OrderHandler could not initialize Connection Pool");
            }
        }
        log.debug("OrderHandler try to get connection");
        try {
            con = pool.getConnection();
            log.debug("OrderHandler db connection established");
            // enable transaction
            con.setAutoCommit(false);
            log.debug("OrderHandler transaction start");

            String prprdQuery;
            PreparedStatement orderQuery;

            // take the items out of the stock
            for (CatalogItem item : card) {
                prprdQuery = "SELECT quantity FROM ArticleTab WHERE Artid = ?;";
                orderQuery = con.prepareStatement(prprdQuery);
                orderQuery.setInt(1, item.getArtid());
                log.debug("OrderHandler doing: " + prprdQuery + " with "
                        + item.getArtid());
                ResultSet resultSet = orderQuery.executeQuery();
                resultSet.next();
                int quantity = resultSet.getInt("quantity");
                quantity = quantity - 1;
                prprdQuery = "UPDATE ArticleTab SET quantity = ? WHERE Artid = ?;";
                orderQuery = con.prepareStatement(prprdQuery);
                orderQuery.setInt(1, quantity);
                orderQuery.setInt(2, item.getArtid());
                log.debug("OrderHandler doing: " + prprdQuery + " with "
                        + quantity + " and " + item.getArtid());
                orderQuery.executeUpdate();
            }

            // TODO make right data available.. no time to make it the proper
            // way.. insert defaults
            prprdQuery = "INSERT INTO OrderTab (UserID, ShipID, PayID, TotalPrice, AddressID, Date, Sent, ShippingID) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            orderQuery = con.prepareStatement(prprdQuery);
            orderQuery.setInt(1, user.getUserID());
            orderQuery.setInt(2, 2);
            orderQuery.setInt(3, 1);
            orderQuery.setFloat(4, (float) user.getShoppingCard().getPrice());
            orderQuery.setInt(5, user.getUserID());
            orderQuery.setDate(6, null);
            orderQuery.setBoolean(7, false);
            orderQuery.setInt(8, 0);
            log.debug("OrderHandler doing: " + prprdQuery);
            orderQuery.executeUpdate();

            con.commit();
            log.debug("OrderHandler transaction complete");
        } catch (Exception e1) {
            // if error during transaction rollback()
            log.debug(e1.toString());
            try {
                log.debug("OrderHandler transaction error - try rollback");
                con.rollback();
                try {
                    response.sendRedirect("/Webshop/Main?orderResult=false");
                } catch (IOException e) {
                    log.warn("RegistrationHanlder is not able to redirect user");
                }
            } catch (Exception e2) {
                log.warn("OrderHandler rollback error - transaction aborted with: "
                        + e2.toString());
                try {
                    response.sendRedirect("/Webshop/Main?orderResult=false");
                } catch (IOException e) {
                    log.warn("RegistrationHanlder is not able to redirect user");
                }
            }
        } finally {
            if (con != null) {
                // return connection to pool
                pool.returnConnection(con);
                log.debug("OrderHandler db connection returned to pool");
            }
        }
        response.sendRedirect("/Webshop/Main?forward=doneView.jsp");
        // at least, reset shopping card
        user.newShoppingCard();
    }
}
