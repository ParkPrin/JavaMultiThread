package me.dinosauruncle.chapter8.threadpermessage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Ch8EMain {
	public static void main(String[] args) {
		System.out.println("main BEGIN");
		
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(
				5 // 쓰레드 생성 개수 설정
				);
		DefaultThreadFactoryHost host = new DefaultThreadFactoryHost(
				scheduledExecutorService
		);
		try {
			host.request(10, 'A');
			host.request(20, 'B');
			host.request(30, 'C');	
		} finally {
			scheduledExecutorService.shutdown();
			System.out.println("main END");	
		}
		
		
	}
}
