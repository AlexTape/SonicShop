package de.alextape.sonicshop.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.userTypes.DefaultUser;
import de.alextape.sonicshop.viewBeans.IndexBean;

/**
 * The Class Dashboard.
 */
@WebServlet("/Dashboard")
public class Dashboard extends ProtectedResource {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3999943207360554614L;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /*
     * (non-Javadoc)
     * 
     * @see
     * controller.ProtectedResource#redirect(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void redirect(HttpServletRequest request,
            HttpServletResponse response) {

        // log
        log.debug("Dashboard initialized");

        // get session
        HttpSession session = request.getSession();

        // cast back the user object
        DefaultUser user = null;
        try {
            user = (DefaultUser) session.getAttribute("login.object");
            log.info("Dashboard " + user.getEmail() + " try to initialize as "
                    + user.getRole().toString());
        } catch (Exception e) {
            log.debug("Dashboard invoke with invalid user object");
            log.warn("Dashboard error");
        }
        if (user != null) {
            // authed
            log.debug("Dashboard creates user bean");
            IndexBean userBean = new IndexBean(user);
            request.setAttribute("IndexBean", userBean);
            log.debug("Dashboard dispatched");
            RequestDispatcher dispatchUser = request
                    .getRequestDispatcher("userView.jsp");
            try {
                dispatchUser.forward(request, response);
            } catch (ServletException e1) {
                log.warn("Dashboard error: " + e1.toString());
                e1.printStackTrace();
            } catch (IOException e1) {
                log.warn("Dashboard error: " + e1.toString());
                e1.printStackTrace();
            }
            return;
        } else {
            log.debug("Dashboard invoke with invalid user object");
            log.warn("Dashboard error");
        }
    }
}