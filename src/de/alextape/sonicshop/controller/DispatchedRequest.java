package de.alextape.sonicshop.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class DispatchedRequest.
 */
@Deprecated
// only testing
public class DispatchedRequest extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3437868801096717574L;

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        performTask(request, response);
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
        performTask(request, response);
    }

    /**
     * Perform task.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws ServletException
     *             the servlet exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void performTask(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String action = request.getParameter("action");
        if (action != null) {
            RequestDispatcher rd = request
                    .getRequestDispatcher("/Webshop/Main");
            if ("include".equalsIgnoreCase(action)) {
                rd.include(request, response);
            } else if ("forward".equalsIgnoreCase(action)) {
                rd.forward(request, response);
            }
        }
    }
}