# Thread-Per-Message Pattern

## Thread-Per-Message Pattern이란? 
thread per message를 직역하면 메시지마다 쓰레드 라는 뜻이다.
어떠한 명령이나 요구마다 새로 한 개의 쓰레드가 할당되고 그 쓰레드가 처리를 실행한다.

## 예제

<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Ch8AMain</td>
		<td>Host에 문자표시를 요구하는 클래스</td>
	</tr>
	<tr>
		<td>Host</td>
		<td>요구에 대하여 쓰레드를 생성하는 클래스</td>
	</tr>
	<tr>
		<td>Helper</td>
		<td>문자표시라고 하는 기능을 제공하는 수동적인 클래스</td>
	</tr>
</table>


Ch8AMain.class

```
public class Ch8AMain {
	public static void main(String[] args) {
		System.out.println("main BEGIN");
		Host host = new Host();
		host.request(10, 'A');
		host.request(20, 'B');
		host.request(30, 'C');
		System.out.println("main END");
	}
}
```

Host.class

```
public class Host {
	private final Helper helper = new Helper();
	public void request(final int count, final char c) {
		System.out.println("      request(" + count + ", " + c + ") BEGIN");
		new Thread() {
			public void run() {
				helper.handle(count, c);
			}
		}.start();
		System.out.println("      request(" + count + ", " + c + ") END");
	}
}
```

Helper.class

```
public class Helper {
	public void handle(int count, char c) {
		System.out.println("      handle(" + count + ", " + c + ") BEGIN ");
		for (int i=0; i < count; i++) {
			slowly();
			System.out.println(c);
		}
		System.out.println("");
		System.out.println("      handle(" + count + ", " + c + ") END");
	}
	private void slowly() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			
		}
	}
}
```

main 함수에서 host 객체에서 request를 실행할 때마다 thread 객체가 생성이 되고
주입된 helper 객체의 handle 메소드가 실행이 된다.
특징으로는 request 객체가 실행 후 Helper 객체의 handle 메소드를 호출하고 종료해버린다.
handle 메소드의 호출 종료여부와 관계없이 호출후 종료해버리는 비동기적 방식으로 진행이 된다.

## Thread-Per-Message Pattern의 등장인물

1. Client(의뢰자) 역할: Host객체의 request를 호출하는 역할을 담당한 main함수
2. Host 역할: Client 역할로부터 요구(request)를 받으면 새로 쓰레드를 만들어서 기동한다. 
3. Helper (원조자) 역할: Helper 역할은 요구를 처리(handle)하는 기능을 Host에게 제공한다.


## Thread-Per-Message Pattern의 특징과 사용하는 경우 
1. 응답성을 높이고 지연 시간을 줄인다 
2. 처리 순서를 상관하지 않을 때 사용한다
3. 반환값이 불필요한 경우에 사용한다
4. 서버에 응용
5. 메소드 호출 + 쓰레드 기동 -> 메시지 송신 

## 그 외에 구현하는 방식에 대해서

```
1. 
		new Thread() {
			public void run() {
				helper.handle(count, c);
			}
		}.start();
		
2,
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				helper.handle(count, c);
				
			}
		};
		new Thread(runnable).start();		
```


1. 익명 쓰레드 생성과 실행을 같이 구현하는 경우와
2. 쓰레드 생성과 실행을 나누어서 구현하는 경우를 볼 수 있다. 

## java.util.concurrent.ThreadFactory 인터페이스를 사용한 Thread-Per-Message Pattern

ThreadFactory는 쓰레드 생성을 추상화한 인터페이스이며, 인수로 부여하는
Runnable 객체는 쓰레드가 실행하는 처리의 내용을 나타낸다.

new를 사용하여 Thread의 인스턴스를 만들면 그 소스 코드는 java.lang.Thread 클래스에
의존하게 된다. 그렇게 되면 쓰레드 생성 부분을 제어할 수 없기 때문에 재사용성이 낮아진다.
필드 threadFactory에 ThreadFactory 객체를 보관해 두고 new Thread(...)라고
적는 대신 thread.newThread(...)라고 적었다고 하면 threadFactory에 대입하는
ThreadFactory 객체를 바꾸는 것만으로 쓰레드의 생성을 제어할 수 있다.


ThreadFactoryHost.class


```
public class ThreadFactoryHost {
	private final Helper helper = new Helper();
	private final ThreadFactory threadFactory;
	
	public ThreadFactoryHost(ThreadFactory threadFactory) {
		this.threadFactory = threadFactory;
	}
	
	public void request(final int count, final char c) {
		System.out.println("      request(" + count + ", " + c + ") BEGIN");
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				helper.handle(count, c);
				
			}
		};
		threadFactory.newThread(runnable).start();
		System.out.println("      request(" + count + ", " + c + ") END");
	}
}
```

Ch8BMain.class

```
public class Ch8BMain {
	public static void main(String[] args) {
		System.out.println("main BEGIN");
		ThreadFactoryHost host = new ThreadFactoryHost(
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						// TODO Auto-generated method stub
						return new Thread(r);
					}
				}
		);
		host.request(10, 'A');
		host.request(20, 'B');
		host.request(30, 'C');
		System.out.println("main END");
	}
}
```

기존 구현과 다른 점
1. Host 객체 생성시 ThreadFactory 객체를 생성 후 주입함
2. Host 내부의 request 구현시 runnable의 run 구현체를 ThreadFactory.newThread 메소드 내부에 주입함


## java.util.concurrent.Executors 클래스로 구하는 ThreadFactory

java.util.concurrent.ThreadFactory 구현하는 방식과 비슷함

차이점: main 클래스에서 처리의 실행을 구현할 수 있다.

DefaultThreadFactoryHost.class

```
public class DefaultThreadFactoryHost {
	private final Helper helper = new Helper();
	private final Executor executor;
	
	public DefaultThreadFactoryHost(Executor executor) {
		this.executor = executor;
	}
	
	public void request(final int count, final char c) {
		System.out.println("      request(" + count + ", " + c + ") BEGIN");
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				helper.handle(count, c);
				
			}
		};
		executor.execute(runnable);
		System.out.println("      request(" + count + ", " + c + ") END");
	}
}
```

Ch8CMain.class

```
public class Ch8CMain {
	public static void main(String[] args) {
		System.out.println("main BEGIN");
		DefaultThreadFactoryHost host = new DefaultThreadFactoryHost(
				new Executor() {
					@Override
					public void execute(Runnable r) {
						new Thread(r).start();		<- 객체 실행 부분을 Host에서 가지고옴
						
					}
				}
		);
		host.request(10, 'A');
		host.request(20, 'B');
		host.request(30, 'C');
		System.out.println("main END");
	}
}
```

## java.util.concurrent.ExecutorService 인터페이스

추상화한 Executor 클래스는 자신의 손으로 new Thread(...를 실행하였다.
그러나 잘 생각해보면 쓰레드를 매번 생성해야만 하는 것은 아니다. Executor 인터페이스만
보호하고 있으면 처리의 실행을 마치고 쉬고 있는 쓰레드를 재사용해도 상관이 없다.

그래서 등장한 것이 java.util.concurrent.ExecutorService 인터페이스이다.
ExecutorService 인터페이스는 반복하여 execute할 수 있는 서비스를 추상화 한 것이다.

ExecutorService 인터페이스의 배후에는 대체로 쓰레드가 항상 동작하고 있기 때문에
서비스 종료용으로 shutdown 메소드가 준비되어 있다.

```
public class Ch8DMain {
	public static void main(String[] args) {
		System.out.println("main BEGIN");
		ExecutorService executorService = Executors.newCachedThreadPool();
		DefaultThreadFactoryHost host = new DefaultThreadFactoryHost(
				executorService
		);
		host.request(10, 'A');
		host.request(20, 'B');
		host.request(30, 'C');
		System.out.println("main END");
	}
}
```

## java.util.concurrent.ScheduledExecutorService 인터페이스

java.util.concurrent.ScheduledExecutorService 인터페이스는 실행의 스케줄링이
가능하며, executorService의 서브 인터페이스이며 처리의 실행을 지연시키는 기능을 갖는다.
Host부분에서는 지연시간을 설정할 수 있고 Main에서는 Executors.newScheduledThreadPool
메소드에 의해 쓰레드가 재사용되는 타입의 ScheduledExecutorService 객체를 만들고 그것을
사용하여 Host를 생성한다.


ScheduleExecutorServiceHost.class

```
public class ScheduleExecutorServiceHost {
	private final Helper helper = new Helper();
	private final ScheduledExecutorService scheduledExecutorService;
	
	public ScheduleExecutorServiceHost(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
	}
	
	public void request(final int count, final char c) {
		System.out.println("      request(" + count + ", " + c + ") BEGIN");
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				helper.handle(count, c);
				
			}
		};
		scheduledExecutorService.schedule(
				runnable,
				3L, // 3초
				TimeUnit.SECONDS // 시간단위 '초' 로 설정
				);
		System.out.println("      request(" + count + ", " + c + ") END");
	}
}
```

Ch8EMain.class

```
public class Ch8EMain {
	public static void main(String[] args) {
		System.out.println("main BEGIN");
		
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(
				5 // 쓰레드 생성 개수 설정
				);
		DefaultThreadFactoryHost host = new DefaultThreadFactoryHost(
				scheduledExecutorService
		);
		try {
			host.request(10, 'A');
			host.request(20, 'B');
			host.request(30, 'C');	
		} finally {
			scheduledExecutorService.shutdown();
			System.out.println("main END");	
		}
		
		
	}
}
```

## 정리

java.lang.Thread 클래스 -  가장 기본적인 쓰레드 생성, 기동 클래스
java.lang.Runnable 인터페이스 - 쓰레드가 실행하는 [일]을 나타내는 클래스
java.util.concurrent.ThreadFactory 인터페이스 - 쓰레드 생성을 추상화한 인터페이스
java.util.concurrent.Executor 인터페이스 - 쓰레드의 실행을 추상화한 인터페이스
java.util.concurrent.ExecutorService 인터페이스 - 재사용되는 쓰레드의 실행을 추상화한 인터페이스
java.util.concurrent.ScheduledExecutorService 인터페이스 
- 스케줄링 된 쓰레드의 실행을 추상화한 인터페이스
java.util.concurrent.Executors 인터페이스 - 인터페이스 생성의 유틸리티 클래스

### 동작방식
Client 역할은 Host 역할의 request 메소드를 호출하여 요구를 제시한다. 그 요구를 실제로 처리하는 것은
Helper 역할의 handle 메소드이다. 그러나 client 역할의 쓰레드를 사용하여 request 중에서 handle를
호출해 버리면 실제 처리가 완료될 때까지 handle로부터 돌아오지 못해 결과적으로 request에서 돌아올 수
없게 된다. 결국 request의 응답성이 나빠진다. 그래서 이 요구를 처리하기 위해 새로운 쓰레드를 Host 역할
에서 기동한다. 이 새로운 쓰레드가 handle을 호출하며, 요구를 제시했던 쓰레드는 request로부터 바로
돌아올 수 있다. 이러한 비동기 메세지의 송신을 구현한 것이 Thread-Per-Message Pattern 이다.


