package com.jobs;

import java.util.Date;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class SchedulerJob implements Job {
	public void execute(JobExecutionContext context)
		throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobKey key = jobDetail.getKey();
		System.out.println("Job key:"+ key +", Date:"+ new Date());
	}
}