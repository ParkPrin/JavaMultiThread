package me.dinosauruncle.chapter7.read_write_lock;

import java.util.Random;

public class ReentrantReadWriteLockWriterThread extends Thread {
	private static final Random random = new Random();
	private final ReentrantReadWriteLockData data;
	private final String filler;
	private int index =0;
	public ReentrantReadWriteLockWriterThread(ReentrantReadWriteLockData data, String filler) {
		this.data = data;
		this.filler = filler;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				char c = nextchar();
				data.write(c);
				Thread.sleep(random.nextInt(3000));
			}
		} catch (InterruptedException e) {
			
		}
	}
	
	private char nextchar () {
		char c = filler.charAt(index);
		index++;
		if (index >= filler.length()) {
			index =0;
		}
		return c;
	}

}
