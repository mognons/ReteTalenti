package com.jobs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.ibatis.common.jdbc.ScriptRunner;
import com.jdbc.DataAccessObject;


public class OptimizeTables implements Job, ServletRequestAware {
   	static final Logger LOGGER = Logger.getLogger(OptimizeTables.class);
	HttpServletRequest servletRequest;

    public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobKey key = jobDetail.getKey();
		LOGGER.info("Job key:"+ key);
 		String filePath = servletRequest.getSession().getServletContext().getRealPath("/");

    	String SQLScriptFilePath = filePath + "SQL_Scripts/rtdb_optimize.sql";
        Connection dbConnection = DataAccessObject.getConnection();
        try {
			// Initialize object for ScripRunner
			ScriptRunner sr = new ScriptRunner(dbConnection, false, false);

			// Give the input file to Reader
			Reader reader = new BufferedReader(
                               new FileReader(SQLScriptFilePath));

			// Execute script
			sr.runScript(reader);

		} catch (Exception e) {
			LOGGER.error("Failed to Execute" + SQLScriptFilePath
					+ " The error is " + e.getMessage());
		}
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest; 
    }
}