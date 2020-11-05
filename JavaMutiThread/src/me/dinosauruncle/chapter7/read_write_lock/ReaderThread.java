package me.dinosauruncle.chapter7.read_write_lock;

public class ReaderThread extends Thread {
	private final Data data;
	public ReaderThread(Data data) {
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
