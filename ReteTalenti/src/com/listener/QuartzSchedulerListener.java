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
import com.jobs.*;

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

		JobDetail DBkeepAlive = JobBuilder.newJob(DatabaseKeepAlive.class)
								.withIdentity("DBkeepAlive", "myGroup").build();
		
		JobDetail ChkScadenzaEmporio = JobBuilder.newJob(CheckScadenzaEmporio.class)
				.withIdentity("ChkScadenzaEmporio", "myGroup").build();
		try {	

			Trigger everyTenSeconds = TriggerBuilder
			  .newTrigger()
			  .withIdentity("everyTenSeconds", "myGroup")
			  .withSchedule(
			     CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
			  .build();

			Trigger dailyInTheEvening = TriggerBuilder
					  .newTrigger()
					  .withIdentity("dailyInTheEvening", "myGroup")
					  .withSchedule(
					     CronScheduleBuilder.cronSchedule("0 30 20 * * ?"))
					  .build();

			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(DBkeepAlive, dailyInTheEvening);
			scheduler.scheduleJob(ChkScadenzaEmporio, dailyInTheEvening);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}