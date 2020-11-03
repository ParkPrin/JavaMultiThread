  
package me.dinosauruncle.chapter3.immutable;

public class Ch3AMain {

	public static void main(String[] args) {
		// 불변의 객체를 공유자원으로 사용함
		Person alice = new Person("Alice", "Alaska");
		
		new PrintPersonThread(alice).start();
		new PrintPersonThread(alice).start();
		new PrintPersonThread(alice).start();

	}

}