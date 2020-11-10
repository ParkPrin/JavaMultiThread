package me.dinosauruncle.chapter8.threadpermessage;

import java.util.concurrent.ThreadFactory;

public class Ch8BMain {
	public static void main(String[] args) {
		System.out.println("main BEGIN");
		ThreadFactoryHost host = new ThreadFactoryHost(
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						// TODO Auto-generated method stub
						return new Thread(r);
					}
				}
		);
		host.request(10, 'A');
		host.request(20, 'B');
		host.request(30, 'C');
		System.out.println("main END");
	}
}
