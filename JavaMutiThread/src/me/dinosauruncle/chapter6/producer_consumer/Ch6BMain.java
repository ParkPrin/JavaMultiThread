  
package me.dinosauruncle.chapter6.producer_consumer;

public class Ch6BMain {
	public static void main(String[] args) {
		ArrayBlockingQueueTable table = new ArrayBlockingQueueTable(3);		// 케이크를 3개까지 놓을 수 있는 테이블이 있다
		new ArrayBlockingQueueMakerThread("MakerThread-1", table, 31415).start();
		new ArrayBlockingQueueMakerThread("MakerThread-2", table, 92653).start();
		new ArrayBlockingQueueMakerThread("MakerThread-3", table, 58979).start();
		new ArrayBlockingQueueEaterThread("EaterThread-1", table, 32384).start();
		new ArrayBlockingQueueEaterThread("EaterThread-2", table, 62643).start();
		new ArrayBlockingQueueEaterThread("EaterThread-3", table, 38327).start();
	}
}