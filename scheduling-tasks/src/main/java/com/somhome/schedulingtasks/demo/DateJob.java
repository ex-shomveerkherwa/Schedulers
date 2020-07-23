package com.somhome.schedulingtasks.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.somhome.schedulingtasks.service.DateService;

@Component
public class DateJob {

	@Autowired
	private DateService dateService;
	
	@Scheduled(fixedRate = 5000)
	public void datePrintcronJob() {
		dateService.printCurrentDate();
	}
}
