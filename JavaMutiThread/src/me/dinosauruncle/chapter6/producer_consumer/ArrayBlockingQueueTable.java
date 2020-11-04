package me.dinosauruncle.chapter6.producer_consumer;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueTable extends ArrayBlockingQueue<String> {
	public ArrayBlockingQueueTable(int count) {
		super(count);
	}
	
	public void put(String cake) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " puts " + cake);
		super.put(cake);
	}
	
	public String take() throws InterruptedException {
		String cake = super.take();
		System.out.println(Thread.currentThread().getName() + " takes " + cake);
		return cake;
	}
	
}