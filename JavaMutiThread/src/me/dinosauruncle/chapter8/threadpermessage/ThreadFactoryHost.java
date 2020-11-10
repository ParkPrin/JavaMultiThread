package me.dinosauruncle.chapter8.threadpermessage;

import java.util.concurrent.ThreadFactory;

public class ThreadFactoryHost {
	private final Helper helper = new Helper();
	private final ThreadFactory threadFactory;
	
	public ThreadFactoryHost(ThreadFactory threadFactory) {
		this.threadFactory = threadFactory;
	}
	
	public void request(final int count, final char c) {
		System.out.println("      request(" + count + ", " + c + ") BEGIN");
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				helper.handle(count, c);
				
			}
		};
		threadFactory.newThread(runnable).start();
		System.out.println("      request(" + count + ", " + c + ") END");
	}
}
