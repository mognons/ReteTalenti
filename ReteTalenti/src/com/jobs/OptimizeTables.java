package com.jobs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibatis.common.jdbc.ScriptRunner;
import com.jdbc.DataAccessObject;


public class OptimizeTables implements Job, ServletRequestAware {
   	HttpServletRequest servletRequest;

    public void execute(JobExecutionContext context) throws JobExecutionException {
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
			System.err.println("Failed to Execute" + SQLScriptFilePath
					+ " The error is " + e.getMessage());
		}
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
 
    }

}