package me.dinosauruncle.chapter7.read_write_lock;

public class Ch7BMain {
	public static void main(String[] args) {
		ReentrantReadWriteLockData data = new ReentrantReadWriteLockData(10);
		new ReentrantReadWriteLockReaderThread(data).start();
		new ReentrantReadWriteLockReaderThread(data).start();
		new ReentrantReadWriteLockReaderThread(data).start();
		new ReentrantReadWriteLockReaderThread(data).start();
		new ReentrantReadWriteLockReaderThread(data).start();
		new ReentrantReadWriteLockReaderThread(data).start();
		new ReentrantReadWriteLockWriterThread(data, "ABCDEFGHIJKLMNOPQRSTUVWXYZ").start();
		new ReentrantReadWriteLockWriterThread(data, "abcdefghijklmnopqrstuvwxyz").start();
	}
}
