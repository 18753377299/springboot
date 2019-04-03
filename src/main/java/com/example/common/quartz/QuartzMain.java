package com.example.common.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzMain {
	public static void main(String[]args) throws SchedulerException {
		// 1.创建 Job 对象：你要做什么事？
		JobDetail jobDetail =JobBuilder.newJob(QuartzDemo.class).build();
		/**
		* 简单的 trigger 触发时间：通过 Quartz 提供一个方法来完成简单的重复
		* 调用 cron
		* Trigger：按照 Cron 的表达式来给定触发的时间
		* */
		
		// 2.创建 Trigger 对象：在什么时间做？
		/**提供一个方法来完成简单的重复*/
//		Trigger trigger = TriggerBuilder.newTrigger().
//				withSchedule(SimpleScheduleBuilder.repeatSecondlyForever()).build();
		/*按照 Cron 的表达式来给定触发的时间*/
		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(
				CronScheduleBuilder.cronSchedule("0/3 * * * * ?")).build();
		
		// 3.创建 Scheduler 对象：在什么时间做什么事？
		
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.scheduleJob(jobDetail,trigger);
		
		// 启动
		scheduler.start();
	}
}
