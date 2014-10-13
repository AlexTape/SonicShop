package de.alextape.sonicshop.connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

/*
 * this is a connection pool used by the different userTypes to get a database connecten on the right way
 */
/**
 * The Class ConnectionPool.
 */
public class ConnectionPool {

    /** The connections. */
    private Hashtable connections = new Hashtable();

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /** The props. */
    private Properties props;

    /**
     * Instantiates a new connection pool.
     *
     * @param props
     *            the props
     * @param initialConnections
     *            the initial connections
     * @throws SQLException
     *             the SQL exception
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    public ConnectionPool(Properties props, int initialConnections)
            throws SQLException, ClassNotFoundException {
        this.props = props;
        log.debug("ConnectionPoolClass new instance [connectionCount= "
                + initialConnections + "] " + props.toString());
        initializePool(props, initialConnections);
    }

    /* grab a valid connection out of the pool */
    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws SQLException
     *             the SQL exception
     */
    public Connection getConnection() throws SQLException {

        Connection con = null;

        @SuppressWarnings("rawtypes")
        Enumeration cons = connections.keys();

        synchronized (connections) {
            while (cons.hasMoreElements()) {
                con = (Connection) cons.nextElement();

                Boolean b = (Boolean) connections.get(con);
                if (b == Boolean.FALSE) {
                    // got unused connection, check integrity
                    try {
                        con.setAutoCommit(true);
                    } catch (SQLException e) {
                        // connection error, replace instance
                        connections.remove(con);
                        con = getNewConnection();
                    }
                    // refresh hashtable to mark this con as used
                    connections.put(con, Boolean.TRUE);
                    return con;
                }
            }

            // there are no available cons
            // TODO maybe recycle connections here (after timeout)??

            con = getNewConnection();
            connections.put(con, Boolean.FALSE);
            return con;

        }
    }

    /* get a new connection for the pool */
    /**
     * Gets the new connection.
     *
     * @return the new connection
     * @throws SQLException
     *             the SQL exception
     */
    private Connection getNewConnection() throws SQLException {
        log.debug("ConnectionPoolClass deliver connection");
        return DriverManager.getConnection(props.getProperty("connection.url"),
                props);
    }

    /* init the connection pool */
    /**
     * Initialize pool.
     *
     * @param props
     *            the props
     * @param initialConnections
     *            the initial connections
     * @throws SQLException
     *             the SQL exception
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    public void initializePool(Properties props, int initialConnections)
            throws SQLException, ClassNotFoundException {
        // load driver
        Class.forName(props.getProperty("connection.driver"));

        // write pool into hashtable - FALSE for not used cons
        for (int i = 0; i < initialConnections; i++) {
            Connection con = getNewConnection();
            connections.put(con, Boolean.FALSE);
        }
        log.debug("ConnectionPoolClass pool online");
    }

    /* return the connection to the pool */
    /**
     * Return connection.
     *
     * @param returned
     *            the returned
     */
    public void returnConnection(Connection returned) {
        if (connections.containsKey(returned)) {
            connections.put(returned, Boolean.FALSE);
            log.debug("ConnectionPoolClass connection returned");
        }
    }

}