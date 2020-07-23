package com.somhome.schedulingtasks.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DateService {

	private final Logger LOG = LoggerFactory.getLogger(DateService.class);
	
	private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	
	public void printCurrentDate() {
		Date date = new Date();
		LOG.info("Date: {}", date);
		LOG.info("Formatted Date: {}", format.format(date));
	}
}
