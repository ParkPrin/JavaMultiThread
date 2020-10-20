# Java Thread의 기본사용

## 스레드란?
프로그램을 실행하고 잇는 주체

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
Thread 상태변환 명령어: wait, notify, notifyAll
wait는 대기상태로 만듬,
notify는 자원 사용자가 없음을 알리고 대기상태에서 실행상태로 변환
notifyAll은 모든 대기상태에 있는 Thread에게 자원을 사용이 가능하다는 것을 알림 

대기상태에 있는 모든 Thread의 집합을 wait set 이라고함

### Wait 메소드
wait 메소드는 동작가능한 Thread를 대기상태로 만든다 => wait set에 Thread를 넣음

### notify 메소드
wait set에 있는 대기상태에 있는 Thread를 꺼내서 동작 가능하도록 만든다.


# 멀티 Thread 프로그램의 평가기준
1) 안정성: 객체를 망가뜨리지 않을 것
2) 생존성: 필요한 처리가 이뤄질 것 <-> 데드락이 일어나는 것
3) 재사용성: 클래스를 다시 사용할 수 있을 것
4) 수행능력: 고속·대량으로 처리할 수 있을 것, 시간이 짧을수록(응답성이 좋은)

