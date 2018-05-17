package com.jfdh.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jfdh.service.ScoreShopService;

@Component
public class MyTask {
	@Autowired
	private ScoreShopService scoreShopService;
	
	
	//"0 15 10 * * ?"    每天早上10：15触发 
//	 @Scheduled(cron="0/15 * *  * * ? ")   //每5秒执行一次 
	@Scheduled(cron = "0 1 0 * * ?")
	public void doPushMessage() {
		System.out.println("start....");
		scoreShopService.doBusiness();
		System.out.println("end....");
		
	}
}
