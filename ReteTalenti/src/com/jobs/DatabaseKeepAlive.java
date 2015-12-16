package com.jobs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.jdbc.DataAccessObject;

public class DatabaseKeepAlive implements Job {
	static final Logger LOGGER = Logger.getLogger(DatabaseKeepAlive.class);

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
			LOGGER.info("DB KeepAlive Job: Totale Assistiti: " + totaleAssistiti);
		} catch (SQLException sql) {
			LOGGER.error("Exception occured in job DatabaseKeepAlive - " + sql.getMessage());
		}
	}
}