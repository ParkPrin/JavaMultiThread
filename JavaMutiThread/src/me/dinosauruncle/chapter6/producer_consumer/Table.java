package me.dinosauruncle.chapter6.producer_consumer;

public class Table {
	private final String[] buffer;
	private int tail;	// 다음에 put할 장소
	private int head;	// 다음에 take할 장소
	private int count;	// buffer 안에 케이크 수
	public Table(int count) {
		this.buffer = new String[count];
		this.head = 0;
		this.tail = 0;
		this.count = 0;
	}
	
	// 케이크를 놓는다
	public synchronized void put (String cake) 
			throws InterruptedException {
		System.out.println(Thread.currentThread().getName()+
				" puts " + cake);
		while (count >= buffer.length) {
			wait();
		}
		buffer[tail] = cake;
		tail = (tail + 1) % buffer.length;
		count++;
		notifyAll();
	}
	
	// 케이크를 먹는다
	public synchronized String take() 
			throws InterruptedException {
		while (count <= 0) {
			wait();
		}
		String cake = buffer[head];
		head = (head + 1) % buffer.length;
		count--;
		notifyAll();
		System.out.println(Thread.currentThread().getName() + " takes " + cake);
		return cake;
	}

}