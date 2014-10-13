package de.alextape.sonicshop.connectivity;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/*
 * Further steps with ConnectionPool better in my Oppinion
 */
/**
 * The Class ConnectionHolder.
 */
@Deprecated
public class ConnectionHolder implements HttpSessionBindingListener {

    /** The con. */
    private Connection con = null;

    /**
     * Instantiates a new connection holder.
     *
     * @param con
     *            the con
     */
    public ConnectionHolder(Connection con) {

        // save the connection
        this.con = con;

        try {
            // long life transaktion
            con.setAutoCommit(false);
        } catch (SQLException e) {
            // TODO error handling
        }
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return con;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpSessionBindingListener#valueBound(javax.servlet
     * .http.HttpSessionBindingEvent)
     */
    public void valueBound(HttpSessionBindingEvent event) {
        // just attached
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpSessionBindingListener#valueUnbound(javax.servlet
     * .http.HttpSessionBindingEvent)
     */
    public void valueUnbound(HttpSessionBindingEvent event) {
        // clear stage if session timeout or logout
        try {
            if (con != null) {
                con.rollback();
                con.close();
            }
        } catch (SQLException e) {
            // TODO log error
        }
    }
}
