package me.dinosauruncle.chapter5.balking;

import java.util.concurrent.TimeoutException;

public class Host {
	private final long timeout; // Ÿ�� �ƿ��� ��
	private boolean ready = false; // �޼ҵ带 �����ص� �Ǹ� true
	
	public Host(long timeout) {
		this.timeout = timeout;
	}
	
	// ���¸� �����Ѵ�
	
	public synchronized void setExecutable(boolean on) {
		ready = on;
		notifyAll();
	}
	
	// ���¸� ����Ͽ� �����Ѵ�
	public synchronized void execute() 
			throws InterruptedException,TimeoutException{
		long start = System.currentTimeMillis(); // ���� �ð�
		while (!ready) {
			long now = System.currentTimeMillis(); // ���� �ð�
			long rest = timeout - (now - start);  //������ ��� �ð�
			
			if (rest <= 0) {
				throw new TimeoutException("now - start = "
						+ (now - start) + ", timeout = " + timeout);
			}
			wait(rest);
		}
		doExecute();
		
	}
	
	// ���� ó��
	private void doExecute() {
		System.out.println(Thread.currentThread().getName() + " calls doExecute");
	}
}
