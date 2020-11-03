package me.dinosauruncle.chapter5.balking;

import java.io.IOException;
import java.util.Random;

public class ChangerThread extends Thread {
	private final Data data;
	private final Random random = new Random();
	public ChangerThread(String name, Data data) {
		super(name);
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			for (int i = 0; true; i++) {
				data.change("No. " + i);				// 데이터를 변경한다
				Thread.sleep(random.nextInt(1000));		// 작업
				data.save();							// 명시적으로 저장한다.
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}