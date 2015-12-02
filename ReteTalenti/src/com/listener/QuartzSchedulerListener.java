package com.listener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import com.jobs.SchedulerJob;

public class QuartzSchedulerListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.shutdown(true);
		} catch (Exception e) {
			System.err.println("Error(s) in destroying scheduler");
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {

		JobDetail job = JobBuilder.newJob(SchedulerJob.class)
							.withIdentity("myJob", "myGroup").build();
		try {

			Trigger trigger = TriggerBuilder
			  .newTrigger()
			  .withIdentity("myTrigger", "myGroup")
			  .withSchedule(
			     CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
			  .build();

			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			// scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}