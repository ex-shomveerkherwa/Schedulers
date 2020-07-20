package com.somhome.quartzOnly.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoJob implements Job {

	private Logger log = LoggerFactory.getLogger(DemoJob.class);

	@Autowired
	private DemoService demoService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
		if(demoService == null) {
			log.info("Create new Service Instance");
			demoService = new DemoService();
		}
		demoService.perfromServiceLogic();
		log.info("Job ** {} ** completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(),
				context.getNextFireTime());

	}

}
