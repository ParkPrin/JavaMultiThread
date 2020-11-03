package me.dinosauruncle.chapter5.balking;

import java.io.IOException;

public class SaverThread extends Thread {
	private final Data data;
	public SaverThread(String name, Data data) {
		super(name);
		this.data = data;
	}
	
	@Override
	public void run() {
	
		try {
			while (true) {
				data.save();			// 데이터를 저장하려 한다
				Thread.sleep(1000);		// 약 1초 휴식
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}