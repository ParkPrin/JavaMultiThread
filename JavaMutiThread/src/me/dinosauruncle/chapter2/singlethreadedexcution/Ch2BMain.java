package me.dinosauruncle.chapter2.singlethreadedexcution;

public class Ch2BMain {
	
	public static void main(String[] args) {
		// 3���� ���ҽ��� �غ��Ѵ�
		BoundedResource resource = new BoundedResource(3);
		
		//  10���� �����尡 ����Ѵ�.
		for (int i =0; i < 10; i++) {
			new Ch2UserThread(resource).start();
		}	
	}
}
