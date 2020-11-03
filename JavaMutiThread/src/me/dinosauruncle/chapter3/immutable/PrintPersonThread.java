package me.dinosauruncle.chapter3.immutable;

public class PrintPersonThread extends Thread {
	private Person person;
	// 객체 자체가 final로 정의된 불편 객체이기 때문에 synchronized를 하지 않아도 데이터 간섭이 발생하지 않는다
	// synchronized가 불필요함
	public PrintPersonThread(Person person) {
		this.person = person;
	}
	
	public void run() {
		while (true) {
			// Thread.currentThread().getName은 자기 thread의 이름을 구할 때 사용한다
			// Thread.currentThread()는 현재의 thread 구하는 메소드이다.
			System.out.println(Thread.currentThread().getName() + "prints" + person);
		}
	}
}