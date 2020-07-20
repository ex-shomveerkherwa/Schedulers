package com.somhome.quartzOnly.demo;

import javax.annotation.PostConstruct;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.somhome.quartzOnly.service.DemoJob;

@Component
@Configuration
public class QuartzScheduler {
	private static Logger log = LoggerFactory.getLogger(QuartzScheduler.class);
	
	// NOTE : Problem with Quartz Only implementation is , it creates a new instance of service method in job class,
	// and does not make use of spring beans 
	@PostConstruct
    public void init() {
		log.info("Hello world from QuartzScheduler...");
    }
	
	@Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job ) throws SchedulerException {
		log.debug("Getting a handle to the Scheduler");
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job, trigger);

        log.debug("Starting Scheduler threads");
        scheduler.start();
        return scheduler;
    }
	
	@Bean
	public JobDetail jobDetail() {
		JobDetail jobDetail = JobBuilder.newJob().ofType(DemoJob.class).build();
		return jobDetail;
	}
	
	@Bean
	public Trigger trigger(JobDetail jobDetail) {
		int frequency = 2;
		Trigger trigger = TriggerBuilder.newTrigger()
				.forJob(jobDetail)
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(frequency).withRepeatCount(5)
						).build();
		
		 return trigger;
	}
	
}
