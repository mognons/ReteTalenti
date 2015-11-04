package com.listener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
public class classActionServletContextListener 
               implements ServletContextListener{
 
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}
 
        //Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("ServletContextListener started");
		Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("deregistering jdbc driver: " + driver);
            } catch (SQLException e) {
            	System.out.println("Error deregistering driver " + driver);
            }

        }
	}
}