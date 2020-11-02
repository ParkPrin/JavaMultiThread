package me.dinosauruncle.chapter6.producer_consumer;

import java.util.Random;

public class MakerThread extends Thread {
	private final Random random;
	private final Table table;
	private static int id =0;	// ����ũ �ȳ� ��ȣ(�丮�� ���� ����)
	public MakerThread(String name, Table table, long seed) {
		super(name);
		this.table = table;
		this.random = new Random(seed);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(random.nextInt(10000));
				String cake = "[ Cake No. " + nextId() + " by " + getName() + "]";
			}
		} catch (InterruptedException e) {

		}
		
	}
	private static synchronized int nextId() {
		return id++;
	}

}
