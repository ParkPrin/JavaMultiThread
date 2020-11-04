package me.dinosauruncle.chapter6.producer_consumer;

import java.util.Random;
import java.util.concurrent.Exchanger;

public class ProducerThread extends Thread {
	private final Exchanger<char[]> exchanger;
	private char[] buffer = null;
	private char index = 0;
	private final Random random;
	
	public ProducerThread(Exchanger<char[]> exchanger, char[] buffer, long seed) {
		super("ProducerThread");
		this.exchanger = exchanger;
		this.buffer = buffer;
		this.random = new Random();
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				// 버퍼에 문자를 채워 넣는다
				for (int i = 0; i < buffer.length; i++) {
					buffer[i] = nextChar();
					System.out.println(Thread.currentThread().getName() + ": " + buffer[i] + " -> ");
					
					// 버퍼를 교환한다
					System.out.println(Thread.currentThread().getName() + ": BERORE exchange");
					buffer = exchanger.exchange(buffer);
					System.out.println(Thread.currentThread().getName() + ": AFTER exchange");
				}
			}	
		} catch (InterruptedException e) {
			
		}
		
		
	}
	
	// 문자를 생산한다
	private char nextChar() throws InterruptedException {
		char c = (char)('A' + index % 26);
		index++;
		Thread.sleep(random.nextInt(1000));
		return c;
	}
}
