package me.dinosauruncle.chapter4.guardedsuspension;

import java.util.Random;

public class ServerThread2 extends Thread {
	private final Random random;
	private final LinkedBlockingQueueUsedRequestQueue requestQueue;
	public ServerThread2(LinkedBlockingQueueUsedRequestQueue requestQueue, String name, long seed) {
		super(name);
		this.requestQueue = requestQueue;
		this.random = new Random(seed);
	}
	@Override
	public void run() {
		for (int i =0; i < 10000; i++) {
			Request request = requestQueue.getRequest();
			System.out.println(Thread.currentThread().getName() + " handles " + request);
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {}
		}
	}

}