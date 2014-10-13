package de.alextape.sonicshop.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;
import de.alextape.sonicshop.userTypes.DefaultUser;
import de.alextape.sonicshop.viewBeans.IndexBean;

/**
 * The Class CategoryHandler.
 */
@WebServlet("/CategoryHandler")
public class CategoryHandler extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -534503346901758395L;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /** The pool. */
    private ConnectionPool pool = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // get session
        HttpSession session = request.getSession();

        // check session is authed
        Object knowHim = session.getAttribute("login.object");

        String category = request.getParameter("category");
        String subCategory = request.getParameter("item");
        log.debug("CategoryHandler request with \"" + category + "\" and \""
                + subCategory + "\"");
        String appendRedirect = validateCategory(category, subCategory);
        log.debug("CategoryHandler validate \"" + appendRedirect + "\"");
        if (appendRedirect == null) {
            log.debug("CategoryHandler redirect to Main append null");
            response.sendRedirect("/Webshop/Main");
            return;
        }
        if (appendRedirect.isEmpty() == true) {
            log.debug("CategoryHandler redirect to Main append empty");
            response.sendRedirect("/Webshop/Main");
            return;
        }
        String redirectTo = "categoryView.jsp" + appendRedirect;
        log.debug("CategoryHandler redirect to " + redirectTo);

        if (!(knowHim instanceof DefaultUser) | knowHim == null) {
            log.debug("CategoryHandler not authorized user detected");
            // not authed!
            IndexBean guestBean = new IndexBean();
            request.setAttribute("IndexBean", guestBean);
            RequestDispatcher dispatchGuest = request
                    .getRequestDispatcher(redirectTo);
            dispatchGuest.include(request, response);
            return;
        } else {
            // cast back the user object
            DefaultUser user = (DefaultUser) knowHim;
            log.info("CategoryHandler " + user.getEmail() + " returned as "
                    + user.getRole().toString());
            // revalidate user
            request.isUserInRole(user.getRole().toString());
            // authed
            IndexBean userBean = new IndexBean(user);
            request.setAttribute("IndexBean", userBean);
            RequestDispatcher dispatchUser = request
                    .getRequestDispatcher(redirectTo);
            dispatchUser.include(request, response);
            return;
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
        doGet(request, response);
    }

    /**
     * Validate category.
     *
     * @param category
     *            the category
     * @param subCategory
     *            the sub category
     * @return the string
     */
    public String validateCategory(String category, String subCategory) {
        // shell for connection
        Connection con = null;
        // are the incoming strings a valid category?
        boolean validCategory = false;
        boolean validSubCategory = false;
        log.debug("CategoryHandler Category Request");
        log.debug("CategoryHandler try to reach relevant pool");
        if (pool == null) {
            try {
                log.debug("CategoryHandler initialize ClassPools.PAGE");
                ClassPools.initialize(SecurityLevel.PAGE);
                log.debug("CategoryHandler gets Pool of ClassPools.PAGE");
                pool = ClassPools.getPool(SecurityLevel.PAGE);
            } catch (Exception e) {
                log.warn("CategoryHandler could not initialize Connection Pool");
            }
        }
        log.debug("CategoryHandler try to get connection");
        try {
            con = pool.getConnection();
            log.debug("CategoryHandler db connection established");
            // enable transaction
            con.setAutoCommit(false);
            log.debug("CategoryHandler transaction start: getCategory()");
            String prprdQuery = "SELECT COUNT(DISTINCT(Category)) FROM ArticleTab INNER JOIN CategoryTab USING (CategoryID) WHERE LOWER(Category) = LOWER(?);";
            log.debug("CategoryHandler doing: " + prprdQuery);
            PreparedStatement categoryQuery = con.prepareStatement(prprdQuery);
            categoryQuery.setString(1, category);
            ResultSet resultSet = categoryQuery.executeQuery();
            // if there is only one entity => subCategory match
            // if there is more then one entity => Category match
            resultSet.next();
            BigDecimal resultSize = resultSet.getBigDecimal("count");
            log.debug("CategoryHandler Query done");
            log.debug("CategoryHandler Query result: " + resultSize.toString());
            if (resultSize.intValueExact() == 1) {
                validCategory = true;
            }
            prprdQuery = "SELECT DISTINCT COUNT(*) FROM (ArticleTab INNER JOIN CategoryTab USING (CategoryID)) INNER JOIN SubcategoryTab USING (SubcategoryID) WHERE LOWER(Category) = LOWER(?) and LOWER(subcategory) = LOWER(?);";
            log.debug("CategoryHandler doing: " + prprdQuery);
            categoryQuery = con.prepareStatement(prprdQuery);
            categoryQuery.setString(1, category);
            categoryQuery.setString(2, subCategory);
            resultSet = categoryQuery.executeQuery();
            resultSet.next();
            resultSize = resultSet.getBigDecimal("count");
            log.debug("CategoryHandler Query done");
            log.debug("CategoryHandler Query result: " + resultSize.toString());
            if (resultSize.intValueExact() > 1) {
                validSubCategory = true;
            }

            con.commit();
            log.debug("CategoryHandler transaction complete");
        } catch (Exception e1) {
            // if error during transaction rollback()
            try {
                log.debug("CategoryHandler transaction error - try rollback");
                con.rollback();
                return null;
            } catch (Exception e2) {
                log.warn("CategoryHandler rollback error - transaction aborted with: "
                        + e2.toString());
                log.warn("CategoryHandler Error");
                return null;
            }
        } finally {
            if (con != null) {
                // return connection to pool
                pool.returnConnection(con);
                log.debug("CategoryHandler db connection returned to pool");
            }
        }
        String requestString = "";
        log.debug("CategoryHanlder validCategory is: " + validCategory
                + ", validSubCategory is: " + validSubCategory);
        if (validCategory) {
            requestString += "?category=" + category;
            if (validSubCategory) {
                requestString += "&item=" + subCategory;
            }
        } else {
            requestString = null;
        }
        return requestString;
    }

}
