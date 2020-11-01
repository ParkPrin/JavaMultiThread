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
				data.save();			// �����͸� �����Ϸ� �Ѵ�
				Thread.sleep(1000);		// �� 1�� �޽�
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
