package me.dinosauruncle.chapter6.producer_consumer;

import java.util.Random;

public class ArrayBlockingQueueMakerThread extends Thread {
	private final Random random;
	private final ArrayBlockingQueueTable table;
	private static int id =0;	// 케이크 안내 번호(요리사 전원 공통)
	public ArrayBlockingQueueMakerThread(String name, ArrayBlockingQueueTable table, long seed) {
		super(name);
		this.table = table;
		this.random = new Random(seed);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(random.nextInt(10000));
				String cake = "[ Cake No. " + nextId() + " by " + getName() + "]";
				table.put(cake);
			}
		} catch (InterruptedException e) {

		}
		
	}
	private static synchronized int nextId() {
		return id++;
	}

}