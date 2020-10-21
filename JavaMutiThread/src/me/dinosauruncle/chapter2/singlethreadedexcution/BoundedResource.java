package me.dinosauruncle.chapter2.singlethreadedexcution;

import java.util.Random;
import java.util.concurrent.Semaphore;

// 수 제한이 있는 리소스
public class BoundedResource {
	private final Semaphore semaphore;
	private final int permits;
	private final static Random random = new Random(314159);
	
	//클래스 생성자(permits는 리소스의 개수)
	public BoundedResource(int permits) {
		this.semaphore = new Semaphore(permits);
		this.permits = permits;
	
	}
	
	// 리소스를 사용한다
	public void use() throws InterruptedException {
		semaphore.acquire(); // 사용할 수 있는 리소스가 있는지 조사, 모든 리소스가 사용 주이면 블록함
		try {
			doUse();
		} finally {
			semaphore.release(); // 사용한 리소스를 해제한다
		}
	}
	
	// 리소스를 실제로 사용한다.(여기에서는 Thread.sleep하고 있을 뿐)
	protected void doUse() throws InterruptedException{
		Log.println("BEGIN: used = " + (permits - semaphore.availablePermits()));
		Thread.sleep(random.nextInt(500));
		Log.println("END:   used= " +  (permits - semaphore.availablePermits()));
	}
}
