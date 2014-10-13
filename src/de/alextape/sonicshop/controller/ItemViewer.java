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

import de.alextape.sonicshop.userTypes.DefaultUser;
import de.alextape.sonicshop.viewBeans.IndexBean;

/**
 * The Class ItemViewer.
 */
@WebServlet("/ItemViewer")
public class ItemViewer extends HttpServlet {

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

        // this servlet is able to setup the beans and redirect to the
        // itemviewer
        String redirectTarget = "itemView.jsp";

        if (!(knowHim instanceof DefaultUser) | knowHim == null) {
            log.debug("ItemViewer not authorized user detected");
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
            log.info("ItemViewer " + user.getEmail() + " returned as "
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
