package me.dinosauruncle.chapter7.read_write_lock;

public class ReentrantReadWriteLockReaderThread extends Thread {
	private final ReentrantReadWriteLockData data;
	public ReentrantReadWriteLockReaderThread(ReentrantReadWriteLockData data) {
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				char[] readbuf = data.read();
				System.out.println(Thread.currentThread().getName() + " reads " + String.valueOf(readbuf));
			}
		} catch (InterruptedException e) {
			
		}
	}

}
