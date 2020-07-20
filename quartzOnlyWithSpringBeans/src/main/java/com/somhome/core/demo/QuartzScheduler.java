package com.somhome.core.demo;

import java.io.IOException;
import java.util.Properties;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

import com.somhome.core.service.DemoJob;

@Component
@Configuration
public class QuartzScheduler {
	
	private static Logger log = LoggerFactory.getLogger(QuartzScheduler.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@PostConstruct
    public void init() {
		log.info("Hello world from QuartzScheduler...");
    }
	
	public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
	
	@Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springBeanJobFactory());
        //factory.setQuartzProperties(quartzProperties());
        return factory;
    }
	
	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {
	    AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
	    jobFactory.setApplicationContext(applicationContext);
	    return jobFactory;
	}
	
	@Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory) throws SchedulerException {
		log.debug("Getting a handle to the Scheduler");
        Scheduler scheduler = factory.getScheduler();
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
