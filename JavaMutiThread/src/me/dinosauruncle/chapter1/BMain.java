package me.dinosauruncle.chapter1;

public class BMain {

	public static void main(String[] args) {
		for(int i=0; i < 10000; i++) {
			System.out.println("Good!");
			try {				
				Thread.sleep(1000); // �ش� �ڵ�� ���� 1�ʿ� �ѹ��� �ֿܼ� �޼��� ��µ� 
			} catch (InterruptedException e) {
			}
		}

	}

}
