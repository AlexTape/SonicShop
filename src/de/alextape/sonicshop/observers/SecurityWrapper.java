package de.alextape.sonicshop.observers;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * The Class SecurityWrapper.
 */
public class SecurityWrapper implements Filter {

    /*
     * essential enum to identify and relate userRoles to privilegs of the db
     * connections they get out of the relevant pool
     */
    /**
     * The Enum SecurityLevel.
     */
    public static enum SecurityLevel {

        /** The administrator. */
        ADMINISTRATOR("postgres", "123", 5, ADMINISTRATOR_LEVEL),
        /** The page. */
        PAGE("postgres", "123", 20, "PAGE"),
        /** The supporter. */
        SUPPORTER("postgres", "123", 10, SUPPORTER_LEVEL),
        /** The user. */
        USER("postgres", "123", 15, USER_LEVEL);

        /** The connection count. */
        private int connectionCount;

        /** The descriptor. */
        private String descriptor;

        /** The password. */
        private String password;

        /** The user. */
        private String user;

        /**
         * Instantiates a new security level.
         *
         * @param user
         *            the user
         * @param password
         *            the password
         * @param connectionCount
         *            the connection count
         * @param descriptor
         *            the descriptor
         */
        private SecurityLevel(String user, String password,
                int connectionCount, String descriptor) {
            this.user = user;
            this.password = password;
            this.connectionCount = connectionCount;
            this.descriptor = descriptor;
        }

        /**
         * Gets the connection count.
         *
         * @return the connection count
         */
        synchronized public int getConnectionCount() {
            return connectionCount;
        }

        /**
         * Gets the password.
         *
         * @return the password
         */
        synchronized public String getPassword() {
            return password;
        }

        /**
         * Gets the user.
         *
         * @return the user
         */
        synchronized public String getUser() {
            return user;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Enum#toString()
         */
        @Override
        synchronized public String toString() {
            return descriptor;
        }
    }

    /**
     * The Class SecurityWrapperHttpServletRequest.
     */
    private static class SecurityWrapperHttpServletRequest extends
            HttpServletRequestWrapper {

        /**
         * Instantiates a new security wrapper http servlet request.
         *
         * @param request
         *            the request
         */
        public SecurityWrapperHttpServletRequest(HttpServletRequest request) {
            super(request);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * javax.servlet.http.HttpServletRequestWrapper#isUserInRole(java.lang
         * .String)
         */
        public boolean isUserInRole(String role) {
            switch (role) {
            case ADMINISTRATOR_LEVEL:
                return true;
            case SUPPORTER_LEVEL:
                return true;
            case USER_LEVEL:
                return true;
            default:
                return false;
            }
        }
    }

    /** The Constant ADMINISTRATOR_LEVEL. */
    public static final String ADMINISTRATOR_LEVEL = "ADMINISTRATOR";

    /** The Constant SUPPORTER_LEVEL. */
    public static final String SUPPORTER_LEVEL = "SUPPORTER";

    /** The Constant USER_LEVEL. */
    public static final String USER_LEVEL = "USER";

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest wrappedRequest = new SecurityWrapperHttpServletRequest(
                (HttpServletRequest) servletRequest);
        filterChain.doFilter(wrappedRequest, servletResponse);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}