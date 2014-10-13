package de.alextape.sonicshop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.userTypes.DefaultUser;

/**
 * The Class ProtectedResource.
 */
@WebServlet("/ProtectedResource")
public class ProtectedResource extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7033488871704480169L;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /*
     * this is the root class for protectedresources
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain");

        // log
        log.debug("ProtectedResource working");

        // get session
        HttpSession session = request.getSession();

        // check session is authed
        DefaultUser user;

        try {
            user = (DefaultUser) session.getAttribute("login.object");
            log.debug("ProtectedResource detected: " + user.toString());
        } catch (Exception e) {
            // not authed!
            log.debug("ProtectedResource session not authed");
            response.sendRedirect("/Webshop/Main?forward=loginView.jsp");
            return;
        }
        log.debug("ProtectedResource session authed");
        redirect(request, response);
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

    /*
     * overwrite method to get variable redirect in any derived class
     */
    /**
     * Redirect.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     */
    protected void redirect(HttpServletRequest request,
            HttpServletResponse response) {
        // default routine to redirect to secured jsp
        log.debug("ProtectedResource redirects request to Main");
        try {
            response.sendRedirect("/Webshop/Main");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
