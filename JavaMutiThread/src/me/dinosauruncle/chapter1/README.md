# Java Thread의 기본사용

## Thread 생성

스레드 객체를 생성하는 방법은 두 가지가 있음
###  Thread 클래스를 상속받아서 스레드 클래스 생성
```
package me.dinosauruncle.chapter1;

public class AMain {
	public static void main(String[] args) {	
		new MyThread("Good!").start();
		new MyThread("Nice!").start();
	}

}


```

```
package me.dinosauruncle.chapter1;

public class MyThread extends Thread {
	private String message;
	
	public MyThread(String message) {
		this.message = message;
	}
	
	public void run() {
		for (int i =0; i<10000; i++) {
			System.out.println(message);
		}
	}
}
```

### Runnable 인터페이스를 받아서 구현체로 구현 후 Thread 객체를 생성시 인자값으로 인터페이스 구현체를 주입함
```
package me.dinosauruncle.chapter1;

public class AMain {
	public static void main(String[] args) {
		new Thread(new Printer("Good!")).start();
		new Thread(new Printer("Nice!")).start();
	}

}
```

```
package me.dinosauruncle.chapter1;
public class Printer implements Runnable{
	private String message;
	
	public Printer(String message) {
		this.message = message;
	}

	@Override
	public void run() {
		for(int i= 0; i < 10000; i++) {
			System.out.println(message);
		}
	}

}
```

## Thread의 일시 정지

기본개념
```
Thread.sleep()
sleep의 첫번째 인자 밀리초, 두번째 인자 나노초
```

코드예제

```
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
```

## Thread의 배타제어

### data race(데이터 레이스)
복수의 thread가 동작 실행시 한 thread 작업중 다른 thread가 작업 간섭으로 인한 불필요한 데이터변경에 의한 현상
=> 이를 방지하기 위한 교통정리를 베타제어 혹은 상호베타(mutual exclusion)라고 함
==> Java에서는 Thread 베타제어를 실행 할 때 synchronized 라는 키워드를 사용함


### synchronized 메소드(동기 메소드)
동작원리: 해당키워드를 붙여서 선언하면 그 메소드는 하나의 thread로 동작함
=> 하나의 thread로 동작한다고 해서 어떤 특정한 thread 외에는 실행할 수 없다는 의미가 아니라 한번에 한개만 실행함

### synchronized 블록

락을 취하고 인스터스에 사용함


```
synchronized(식){
	...
}
```

## Thread의 상태변환

wait, notify, notifyAll
