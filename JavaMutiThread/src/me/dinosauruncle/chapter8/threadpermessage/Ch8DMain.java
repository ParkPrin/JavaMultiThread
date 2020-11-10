package me.dinosauruncle.chapter8.threadpermessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ch8DMain {
	public static void main(String[] args) {
		System.out.println("main BEGIN");
		ExecutorService executorService = Executors.newCachedThreadPool();
		DefaultThreadFactoryHost host = new DefaultThreadFactoryHost(
				executorService
		);
		host.request(10, 'A');
		host.request(20, 'B');
		host.request(30, 'C');
		System.out.println("main END");
	}
}
