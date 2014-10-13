package de.alextape.sonicshop.connectivity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * Further steps with ConnectionPool better in my Oppinion
 */
/**
 * The Class ConnectionPerClient.
 */
@Deprecated
public class ConnectionPerClient extends HttpServlet {

    /** The db name. */
    private static String DB_NAME = "Webshop";

    /** The db server. */
    private static String DB_SERVER = "localhost:5432";

    /** The driver. */
    private static String driver = "org.postgresql.Driver";

    /** The password. */
    private static String password = "xxx";

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1332099890476163404L;

    /** The url. */
    private static String url = "jdbc:postgresql://" + DB_SERVER + "/"
            + DB_NAME;

    /** The user. */
    private static String user = "postgres";

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        Connection con;

        synchronized (session) {
            // try to get connection holder
            ConnectionHolder holder = (ConnectionHolder) session
                    .getAttribute("database.connection");

            // if necessary create new holder
            if (holder == null) {

                try {
                    holder = new ConnectionHolder(DriverManager.getConnection(
                            driver, user, password));
                    session.setAttribute("database.connection", holder);
                } catch (SQLException e) {
                    // TODO dispatch error
                    System.out.println("Could not connect db");
                }
            }

            // get connection from holder
            con = holder.getConnection();

            // TODO is working with url??
            System.out.println(url);
        }

        // use connection
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE INV");

            // ...

            // TODO Dispatch to creditcard handler e.g.

        } catch (Exception e) {
            // if error rollback()
            try {
                con.rollback();
                session.removeAttribute("database.connection");
            } catch (Exception ignored) {
            }
            // TODO dispatch error
            System.out.println("db error");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.GenericServlet#init()
     */
    public void init() throws ServletException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new UnavailableException("Could not load driver");
        }
    }
}
