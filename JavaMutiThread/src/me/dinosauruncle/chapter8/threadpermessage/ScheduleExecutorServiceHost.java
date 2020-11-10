package me.dinosauruncle.chapter8.threadpermessage;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleExecutorServiceHost {
	private final Helper helper = new Helper();
	private final ScheduledExecutorService scheduledExecutorService;
	
	public ScheduleExecutorServiceHost(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
	}
	
	public void request(final int count, final char c) {
		System.out.println("      request(" + count + ", " + c + ") BEGIN");
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				helper.handle(count, c);
				
			}
		};
		scheduledExecutorService.schedule(
				runnable,
				3L, // 3초
				TimeUnit.SECONDS // 시간단위 '초' 로 설정
				);
		System.out.println("      request(" + count + ", " + c + ") END");
	}
}
