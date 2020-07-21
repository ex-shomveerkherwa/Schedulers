package com.somhome.quartzSpring.demo;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.somhome.quartzSpring.service.DemoCronJob;
import com.somhome.quartzSpring.service.DemoSimpleJob;

@Configuration
public class QuartzSubmitJobs {

	private static final String EVERY_MINUTE_TODAY = "0 * * 20 * ?"; // 20th July
	
	@Bean(name = "demoJobDetail")
	public JobDetailFactoryBean jobDemoBean() {
		return QuartzConfig.createJobDetail(DemoSimpleJob.class, "Demo Job");
	}

	@Bean(name = "demoJobSimpleTrigger")
	public SimpleTriggerFactoryBean triggerSimpleJob(@Qualifier("demoJobDetail") JobDetail jobDetail) {
		return QuartzConfig.createTrigger(jobDetail, 9000, "Demo Simple Job Trigger");
	}
	
	@Bean(name = "demoCronJobDetail")
	public JobDetailFactoryBean jobCronDemoBean() {
		return QuartzConfig.createJobDetail(DemoCronJob.class, "Demo Cron Job");
	}

	@Bean(name = "demoJobCronTrigger")
	public CronTriggerFactoryBean triggerCronJob(@Qualifier("demoCronJobDetail") JobDetail jobDetail) {
		return QuartzConfig.createCronTrigger(jobDetail, EVERY_MINUTE_TODAY, "Demo Cron Job Trigger");
		
	}
}
