package com.somhome.quartzSpring.demo;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.somhome.quartzSpring.service.DemoJob;

@Configuration
public class QuartzSubmitJobs {

	@Bean(name = "demoJobDetail")
	public JobDetailFactoryBean jobDemoBean() {
		return QuartzConfig.createJobDetail(DemoJob.class, "Demo Job");
	}

	@Bean(name = "demoJobTrigger")
	public SimpleTriggerFactoryBean triggerMemberStats(@Qualifier("demoJobDetail") JobDetail jobDetail) {
		return QuartzConfig.createTrigger(jobDetail, 20000, "Demo Job Trigger");
	}
}
