package com.example.common.schedule;



import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledDemo {
	
	/**
	* 定时任务方法
	* @Scheduled:设置定时任务
	* cron 属性：cron 表达式。定时任务触发是时间的一个字符串表达形式
	*/
	@Scheduled(cron="0/20 * * * * ?")
	public void scheduledMethod(){
//		System.out.println("定时器被触发"+new Date());
	}
}
