package me.dinosauruncle.chapter1;

public class BMain {

	public static void main(String[] args) {
		for(int i=0; i < 10000; i++) {
			System.out.println("Good!");
			try {				
				Thread.sleep(1000); // 해당 코드로 인해 1초에 한번씩 콘솔에 메세지 출력됨 
			} catch (InterruptedException e) {
			}
		}

	}

}
