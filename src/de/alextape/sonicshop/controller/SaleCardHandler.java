package de.alextape.sonicshop.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.catalog.ItemCatalog;
import de.alextape.sonicshop.modell.EntityFactory;
import de.alextape.sonicshop.userTypes.DefaultUser;
import de.alextape.sonicshop.viewBeans.IndexBean;

/**
 * The Class SaleCardHandler.
 */
@WebServlet("/SaleCardHandler")
public class SaleCardHandler extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -328542864864719850L;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /*
     * handle default shop get-request
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get session
        HttpSession session = request.getSession();

        // check session is authed
        Object knowHim = session.getAttribute("login.object");

        // each salecardhandler action redirect to the salecard
        // TODO not enough time for looping back to article page or e.g.
        String redirectTarget = "salecardView.jsp";

        String addThisItem = request.getParameter("item");
        int articleID = 0;

        // if salecardhandler is invoked with item parameter it only adds the
        // item
        if (addThisItem != null) {
            try {
                articleID = Integer.parseInt(addThisItem);
            } catch (NumberFormatException e) {
                log.debug("SaleCardHandler not able to parse articleid redirect to main");
                IndexBean guestBean = new IndexBean();
                request.setAttribute("IndexBean", guestBean);
                RequestDispatcher dispatchGuest = request
                        .getRequestDispatcher("/Main");
                dispatchGuest.include(request, response);
                return;
            }

            DefaultUser defUser = (DefaultUser) knowHim;
            ItemCatalog shoppingCardCatalog;
            try {
                shoppingCardCatalog = defUser.getShoppingCard();
            } catch (NullPointerException e) {
                // guest user tries to buy anything
                log.debug("SaleCardHandler guest try to buy something. force him to register");
                IndexBean guestBean = new IndexBean();
                request.setAttribute("IndexBean", guestBean);
                RequestDispatcher dispatchGuest = request
                        .getRequestDispatcher("/Main?forward=loginView.jsp");
                dispatchGuest.include(request, response);
                return;
            }
            // add item to shoppingcard
            log.debug("SaleCardHandler adds new item to shoppingcard");
            shoppingCardCatalog.add(new EntityFactory().getCatalogItem(String
                    .valueOf(articleID)));
        }

        String removeThisItem = request.getParameter("remove");
        articleID = 0;

        // if salecardhandler is invoked with item parameter it only adds the
        // item
        if (removeThisItem != null) {
            try {
                articleID = Integer.parseInt(removeThisItem);
            } catch (NumberFormatException e) {
                log.debug("SaleCardHanlder not able to parse articleid redirect to main");
                IndexBean guestBean = new IndexBean();
                request.setAttribute("IndexBean", guestBean);
                RequestDispatcher dispatchGuest = request
                        .getRequestDispatcher("/Main");
                dispatchGuest.include(request, response);
                return;
            }

            DefaultUser defUser = (DefaultUser) knowHim;
            ItemCatalog shoppingCardCatalog;
            try {
                shoppingCardCatalog = defUser.getShoppingCard();
            } catch (NullPointerException e) {
                // guest user tries to buy anything
                log.debug("SaleCardHandler guest try to buy something. force him to register");
                IndexBean guestBean = new IndexBean();
                request.setAttribute("IndexBean", guestBean);
                RequestDispatcher dispatchGuest = request
                        .getRequestDispatcher("/Main?forward=loginView.jsp");
                dispatchGuest.include(request, response);
                return;
            }
            // remove item from shoppingcard
            log.debug("SaleCardHandler removes item to shoppingcard");
            shoppingCardCatalog.remove(articleID);
        }

        if (!(knowHim instanceof DefaultUser) | knowHim == null) {
            log.debug("SaleCardHandler not authorized user detected");
            // not authed!
            IndexBean guestBean = new IndexBean();
            request.setAttribute("IndexBean", guestBean);
            RequestDispatcher dispatchGuest = request
                    .getRequestDispatcher(redirectTarget);
            dispatchGuest.include(request, response);
            return;
        } else {
            // cast back the user object
            DefaultUser user = (DefaultUser) knowHim;
            log.info("SaleCardHandler " + user.getEmail() + " returned as "
                    + user.getRole().toString());
            // revalidate user
            request.isUserInRole(user.getRole().toString());
            // authed
            IndexBean userBean = new IndexBean(user);
            request.setAttribute("IndexBean", userBean);
            RequestDispatcher dispatchUser = request
                    .getRequestDispatcher(redirectTarget);
            dispatchUser.include(request, response);
            return;
        }
    }

    /*
     * handle default shop post-request
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // redirect to doGet()
        doGet(req, res);
    }
}
