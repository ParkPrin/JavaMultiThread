package me.dinosauruncle.chapter8.threadpermessage;

import java.util.concurrent.Executor;

public class DefaultThreadFactoryHost {
	private final Helper helper = new Helper();
	private final Executor executor;
	
	public DefaultThreadFactoryHost(Executor executor) {
		this.executor = executor;
	}
	
	public void request(final int count, final char c) {
		System.out.println("      request(" + count + ", " + c + ") BEGIN");
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				helper.handle(count, c);
				
			}
		};
		executor.execute(runnable);
		System.out.println("      request(" + count + ", " + c + ") END");
	}
}
