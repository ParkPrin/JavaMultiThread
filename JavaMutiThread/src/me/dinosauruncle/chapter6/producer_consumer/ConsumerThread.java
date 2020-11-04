package me.dinosauruncle.chapter6.producer_consumer;

import java.util.Random;
import java.util.concurrent.Exchanger;

public class ConsumerThread extends Thread {
	private final Exchanger<char[]> exchager;
	private char[] buffer = null;
	private final Random random;
	
	public ConsumerThread(Exchanger<char[]> exchanger, char[] buffer, long seed) {
		super("ConsumerThread");
		this.exchager = exchanger;
		this.buffer = buffer;
		this.random = new Random(seed);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				// 버퍼를 교환한다
				System.out.println(Thread.currentThread().getName() + ": BEFORE exchange");
				buffer = exchager.exchange(buffer);
				System.out.println(Thread.currentThread().getName() + ": AFTER exchage");
				
				// 버퍼로부터 문자를 꺼낸다
				for (int i =0; i < buffer.length; i++) {
					System.out.println(Thread.currentThread().getName() + ": -> " + buffer[i]);
					Thread.sleep(random.nextInt(1000));
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
