package com.jobs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.jdbc.DataAccessObject;

public class DatabaseKeepAlive implements Job {
	
	private Connection dbConnection = DataAccessObject.getConnection();
	private Statement statement; 
	
	public void execute(JobExecutionContext context)
		throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobKey key = jobDetail.getKey();
		String query = "SELECT COUNT(COD_FISCALE) FROM ASSISTITI";
		try {
			statement = dbConnection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			int totaleAssistiti = 0;
            while (rs.next()) {
            	totaleAssistiti = rs.getInt(1);
            }
			System.out.println("KeepAlive Job @ Date:"+ new Date() + "\n Totale Assistiti: " + totaleAssistiti);
		} catch (SQLException sql) {
			System.err.println("Exception occured in job DatabaseKeepAlive - " + sql.getMessage());
		}
	}
}