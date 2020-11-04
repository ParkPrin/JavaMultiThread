  
package me.dinosauruncle.chapter6.producer_consumer;

import java.util.Random;

public class ArrayBlockingQueueEaterThread extends Thread {
	private final Random random;
	private final ArrayBlockingQueueTable table;
	public ArrayBlockingQueueEaterThread(String name, ArrayBlockingQueueTable table, long seed) {
		super(name);
		this.table = table;
		this.random = new Random(seed);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				String cake = table.take();
				Thread.sleep(random.nextInt(1000));
			}
		} catch (InterruptedException e) {}
	}
}