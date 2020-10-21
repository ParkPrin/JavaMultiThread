package me.dinosauruncle.chapter2.singlethreadedexcution;

import java.util.Random;
import java.util.concurrent.Semaphore;

// �� ������ �ִ� ���ҽ�
public class BoundedResource {
	private final Semaphore semaphore;
	private final int permits;
	private final static Random random = new Random(314159);
	
	//Ŭ���� ������(permits�� ���ҽ��� ����)
	public BoundedResource(int permits) {
		this.semaphore = new Semaphore(permits);
		this.permits = permits;
	
	}
	
	// ���ҽ��� ����Ѵ�
	public void use() throws InterruptedException {
		semaphore.acquire(); // ����� �� �ִ� ���ҽ��� �ִ��� ����, ��� ���ҽ��� ��� ���̸� �����
		try {
			doUse();
		} finally {
			semaphore.release(); // ����� ���ҽ��� �����Ѵ�
		}
	}
	
	// ���ҽ��� ������ ����Ѵ�.(���⿡���� Thread.sleep�ϰ� ���� ��)
	protected void doUse() throws InterruptedException{
		Log.println("BEGIN: used = " + (permits - semaphore.availablePermits()));
		Thread.sleep(random.nextInt(500));
		Log.println("END:   used= " +  (permits - semaphore.availablePermits()));
	}
}
