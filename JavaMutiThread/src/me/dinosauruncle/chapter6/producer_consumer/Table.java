package me.dinosauruncle.chapter6.producer_consumer;

public class Table {
	private final String[] buffer;
	private int tail;	// ������ put�� ���
	private int head;	// ������ take�� ���
	private int count;	// buffer �ȿ� ����ũ ��
	public Table(int count) {
		this.buffer = new String[count];
		this.head = 0;
		this.tail = 0;
		this.count = 0;
	}
	
	// ����ũ�� ���´�
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
	
	// ����ũ�� �Դ´�
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
