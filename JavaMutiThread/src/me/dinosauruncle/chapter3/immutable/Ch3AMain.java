package me.dinosauruncle.chapter3.immutable;

public class Ch3AMain {

	public static void main(String[] args) {
		// �Һ��� ��ü�� �����ڿ����� �����
		Person alice = new Person("Alice", "Alaska");
		
		new PrintPersonThread(alice).start();
		new PrintPersonThread(alice).start();
		new PrintPersonThread(alice).start();

	}

}
