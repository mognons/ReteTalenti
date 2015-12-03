package com.listener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jdbc.DataAccessObject;
 
public class ReteTalentiContextListener 
               implements ServletContextListener{
 
	@Override
	public final void contextDestroyed(ServletContextEvent sce) {
		// Close Database Connection
		System.out.println("Closing DB Connection");
	    DataAccessObject.closeConnection();
	    
		// Now deregister JDBC drivers in this context's ClassLoader:
	    // Get the webapp's ClassLoader
	    ClassLoader cl = Thread.currentThread().getContextClassLoader();
	    // Loop through all drivers
	    Enumeration<Driver> drivers = DriverManager.getDrivers();
	    while (drivers.hasMoreElements()) {
	        Driver driver = drivers.nextElement();
	        if (driver.getClass().getClassLoader() == cl) {
	            // This driver was registered by the webapp's ClassLoader, so deregister it:
	            try {
	                System.out.println("Deregistering JDBC driver " + driver);
	                DriverManager.deregisterDriver(driver);
	            } catch (SQLException ex) {
	                System.out.println("Error deregistering JDBC driver " + driver);
	            }
	        } else {
	            // driver was not registered by the webapp's ClassLoader and may be in use elsewhere
                System.out.println("JDBC driver not registered with this app " + driver);
	        }
	    }
	} 
        //Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("ReteTalenti starting...");
	}
}