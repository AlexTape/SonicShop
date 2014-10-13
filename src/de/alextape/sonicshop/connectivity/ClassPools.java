package de.alextape.sonicshop.connectivity;

import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;

/*
 * this class is holding the relevant connection pools for this webshop api
 */
/**
 * The Class ClassPools.
 */
public class ClassPools {

    /** The administrator pool. */
    public static ConnectionPool administratorPool = null;

    /** The Constant DB_NAME. */
    private static final String DB_NAME = "Webshop";

    /** The Constant DB_SERVER. */
    private static final String DB_SERVER = "localhost:5432";

    /*
     * All valid Credentials for a DB Query
     */
    /** The Constant DRIVER. */
    private static final String DRIVER = "org.postgresql.Driver";

    /** The log. */
    private static Logger log = Logger.getLogger("WebshopLogger");

    /** The page pool. */
    public static ConnectionPool pagePool = null;

    /** The supporter pool. */
    public static ConnectionPool supporterPool = null;

    /** The Constant URL. */
    private static final String URL = "jdbc:postgresql://" + DB_SERVER + "/"
            + DB_NAME;

    /** The user pool. */
    public static ConnectionPool userPool = null;

    /**
     * Gets the administrator pool.
     *
     * @return the administrator pool
     */
    synchronized private static ConnectionPool getAdministratorPool() {
        return administratorPool;
    }

    /**
     * Gets the page pool.
     *
     * @return the page pool
     */
    synchronized private static ConnectionPool getPagePool() {
        return pagePool;
    }

    /*
     * this methods returns the correct pool for each user
     */
    /**
     * Gets the pool.
     *
     * @param userRole
     *            the user role
     * @return the pool
     */
    synchronized public static ConnectionPool getPool(SecurityLevel userRole) {
        switch (userRole) {
        case PAGE:
            return getPagePool();
        case USER:
            return getUserPool();
        case SUPPORTER:
            return getSupporterPool();
        case ADMINISTRATOR:
            return getAdministratorPool();
        default:
            return null;
        }
    }

    /**
     * Gets the supporter pool.
     *
     * @return the supporter pool
     */
    synchronized private static ConnectionPool getSupporterPool() {
        return supporterPool;
    }

    /**
     * Gets the user pool.
     *
     * @return the user pool
     */
    synchronized private static ConnectionPool getUserPool() {
        return userPool;
    }

    /*
     * this sync method will instance a needed set of pools/connections for
     * futher usage
     */
    /**
     * Initialize.
     *
     * @param userRole
     *            the user role
     * @throws SQLException
     *             the SQL exception
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    synchronized public static void initialize(SecurityLevel userRole)
            throws SQLException, ClassNotFoundException {
        Properties props = new Properties();
        props.put("connection.driver", DRIVER);
        props.put("connection.url", URL);
        props.put("user", userRole.getUser());
        props.put("password", userRole.getPassword());

        switch (userRole) {
        case PAGE:
            if (pagePool == null) {
                pagePool = new ConnectionPool(props,
                        userRole.getConnectionCount());
                log.info("ClassPools pagePool is online");
                return;
            } else {
                log.info("ClassPools pagePool already online");
                return;
            }
        case USER:
            if (userPool == null) {
                userPool = new ConnectionPool(props,
                        userRole.getConnectionCount());
                log.info("ClassPools userPool is online");
                return;
            } else {
                log.info("ClassPools userPool already online");
                return;
            }
        case SUPPORTER:
            if (supporterPool == null) {
                supporterPool = new ConnectionPool(props,
                        userRole.getConnectionCount());
                log.info("ClassPools supporterPool is online");
                return;
            } else {
                log.info("ClassPools supporterPool already online");
                return;
            }
        case ADMINISTRATOR:
            if (administratorPool == null) {
                pagePool = new ConnectionPool(props,
                        userRole.getConnectionCount());
                log.info("ClassPools administratorPool is online");
                return;
            } else {
                log.info("ClassPools administratorPool already online");
                return;

            }
        default:
            log.warn("ClassPools non valid configuration trys to instance new pool");
            return;
        }
    }

}