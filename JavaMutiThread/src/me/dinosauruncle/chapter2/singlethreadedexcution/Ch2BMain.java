package me.dinosauruncle.chapter2.singlethreadedexcution;

public class Ch2BMain {
	
	public static void main(String[] args) {
		// 3개의 리소스를 준비한다
		BoundedResource resource = new BoundedResource(3);
		
		//  10개의 쓰레드가 사용한다.
		for (int i =0; i < 10; i++) {
			new Ch2UserThread(resource).start();
		}	
	}
}