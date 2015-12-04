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
								.withIdentity("DBkeepAlive", "Service").build();
		
		JobDetail ChkScadenzaEmporio = JobBuilder.newJob(CheckScadenzaEmporio.class)
				.withIdentity("ChkScadenzaEmporio", "Database").build();
		try {	

			Trigger t1 = TriggerBuilder
			  .newTrigger()
			  .withIdentity("everyTenSeconds", "Hourly")
			  .withSchedule(
			     CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
			  .build();

			Trigger t2 = TriggerBuilder
					  .newTrigger()
					  .withIdentity("InTheEvening1", "Daily")
					  .withSchedule(
					     CronScheduleBuilder.cronSchedule("0 30 20 * * ?"))
					  .build();

			Trigger t3 = TriggerBuilder
					  .newTrigger()
					  .withIdentity("InTheEvening2", "Daily")
					  .withSchedule(
					     CronScheduleBuilder.cronSchedule("0 30 20 * * ?"))
					  .build();

			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(DBkeepAlive, t2);
			scheduler.scheduleJob(ChkScadenzaEmporio, t3);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}