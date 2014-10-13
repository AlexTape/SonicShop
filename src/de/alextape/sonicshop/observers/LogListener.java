package de.alextape.sonicshop.observers;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

/*
 * this class is listening to all servlets and is able to redirect any loggings to several targets using log4j
 */
/**
 * The listener interface for receiving log events. The class that is interested
 * in processing a log event implements this interface, and the object created
 * with that class is registered with a component using the component's
 * <code>addLogListener<code> method. When
 * the log event occurs, that object's appropriate
 * method is invoked.
 *
 * @see LogEvent
 */
public class LogListener implements ServletContextListener {

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
     * ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
     * .ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        /* Use log4j instead of java.utility.logger */
        System.setProperty("rootPath", context.getRealPath("/"));
        String prefix = context.getRealPath("/");
        String file = "WEB-INF" + System.getProperty("file.separator")
                + "classes" + System.getProperty("file.separator")
                + "log4j.properties";

        if (file != null) {
            PropertyConfigurator.configure(prefix + file);
            System.out.println("Logging started for application: " + prefix
                    + file);
        }

    }

}