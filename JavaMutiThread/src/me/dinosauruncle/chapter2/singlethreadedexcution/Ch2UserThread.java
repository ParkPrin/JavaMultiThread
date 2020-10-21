package me.dinosauruncle.chapter2.singlethreadedexcution;

import java.util.Random;

public class Ch2UserThread extends Thread {
	private final static Random random = new Random();
	private final BoundedResource resource;
	
	public Ch2UserThread(BoundedResource resource) {
		this.resource =  resource;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				resource.use();
				Thread.sleep(random.nextInt(3000));
			}
		} catch (InterruptedException e) {
			
		}
	}

}
