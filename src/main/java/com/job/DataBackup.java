package com.job;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataBackup {
//	@Scheduled(fixedDelayString ="${db.backup.delay}")
//	@Scheduled(fixedDelay = 1000)
	public void demoServiceMethod()
	{
		System.out.println("Method executed at every 1 min. Current time is :: "+ new Date());
	}
}
