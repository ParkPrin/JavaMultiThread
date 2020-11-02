package me.dinosauruncle.chapter5.balking;

import java.util.concurrent.TimeoutException;

public class Host {
	private final long timeout; // 타임 아웃의 값
	private boolean ready = false; // 메소드를 실행해도 되면 true
	
	public Host(long timeout) {
		this.timeout = timeout;
	}
	
	// 상태를 변경한다
	
	public synchronized void setExecutable(boolean on) {
		ready = on;
		notifyAll();
	}
	
	// 상태를 고려하여 실행한다
	public synchronized void execute() 
			throws InterruptedException,TimeoutException{
		long start = System.currentTimeMillis(); // 개시 시각
		while (!ready) {
			long now = System.currentTimeMillis(); // 현재 시각
			long rest = timeout - (now - start);  //나머지 대기 시간
			
			if (rest <= 0) {
				throw new TimeoutException("now - start = "
						+ (now - start) + ", timeout = " + timeout);
			}
			wait(rest);
		}
		doExecute();
		
	}
	
	// 실제 처리
	private void doExecute() {
		System.out.println(Thread.currentThread().getName() + " calls doExecute");
	}
}
