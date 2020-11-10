package me.dinosauruncle.chapter8.threadpermessage;

import java.util.concurrent.Executor;

public class Ch8CMain {
	public static void main(String[] args) {
		System.out.println("main BEGIN");
		DefaultThreadFactoryHost host = new DefaultThreadFactoryHost(
				new Executor() {
					@Override
					public void execute(Runnable r) {
						new Thread(r).start();
						
					}
				}
		);
		host.request(10, 'A');
		host.request(20, 'B');
		host.request(30, 'C');
		System.out.println("main END");
	}
}
