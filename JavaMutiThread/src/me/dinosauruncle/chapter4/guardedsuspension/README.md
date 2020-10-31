# Guarded Suspension Pattern

## Guarded Suspension 이란?
Guarded [보호받고 있다]와 Suspension [일시 정지함]을 친 말로써
복수의 쓰레드가 동시에 작업을 사용할 시 쓰레드를 기다리게 하여 인스턴스의 안전성을 보호한다.

## 예시

<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Request</td>
		<td>한 개의 리퀘스트를 표현한 클래스</td>
	</tr>
	<tr>
		<td>RequestQueue</td>
		<td>리퀘스트를 순서대로 비축해 두는 클래스</td>
	</tr>
	<tr>
		<td>ClientThread</td>
		<td>리퀘스트를 제시하는 클래스</td>
	</tr>
	<tr>
		<td>ServerThread</td>
		<td>리퀘스트를 접수하는 클래스</td>
	</tr>
	<tr>
		<td>Ch4AMain</td>
		<td>동작 테스트용 클래스</td>
	</tr>
</table>

Request.class

```
public class Request {
	private final String name;
	public Request(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public String toString() {
		return "[ Request " + name + " ]";
	}
}
```

RequestQueue.class

```
public class RequestQueue {
	private final Queue<Request> queue = new LinkedList<Request>();
	
	// ServerThread객체가 사용하는 method이다
	public synchronized Request getRequest() {
	// queuje.peek()는 Queue객체 안에 request가 존재하는지 여부를 알려준다. 없으면 null를 반환한다
		while (queue.peek() == null ) {
			try {
				// wait에 걸리는 순간 ServerThread는 기다림의 상태에 놓여진다.
				// notify() 또는 notifyAll()가 진행되어야 동작할 수 있다.
				wait();
			} catch (InterruptedException e) {

			}
		}
		return queue.remove();
	}
		// ClientThread객체가 사용하는 method이다
	public synchronized void putRequest (Request request) {
		// queue.offer은 queue 안에 request 객체를 넣는다. 
		queue.offer(request);
		// notifyAll()이 실행되는 순간 기다림의 상태에 놓여진 ServerThread가 동작을 시작한다.
		notifyAll();
	}
}
```

ClientThread.class

```
public class ClientThread extends Thread{
	private final Random random;
	private final RequestQueue requestQueue;
	public ClientThread(RequestQueue requestQueue, String name, long seed) {
		super(name);
		this.requestQueue = requestQueue;
		this.random = new Random(seed);
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 10000; i++ ) {
			Request request = new Request("No." + i);
			System.out.println(Thread.currentThread().getName() + "requests " + request);
			requestQueue.putRequest(request);
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {}
		}
	}

}
```

ServerThread.class

```
public class ServerThread extends Thread {
	private final Random random;
	private final RequestQueue requestQueue;
	public ServerThread(RequestQueue requestQueue, String name, long seed) {
		super(name);
		this.requestQueue = requestQueue;
		this.random = new Random(seed);
	}
	@Override
	public void run() {
		for (int i =0; i < 10000; i++) {
			Request request = requestQueue.getRequest();
			System.out.println(Thread.currentThread().getName() + " handles " + request);
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {}
		}
	}

}
```

Ch4AMain.class

```
public class Ch4AMain {
	public static void main(String[] args) {
		RequestQueue requestQueue = new RequestQueue();
		new ClientThread(requestQueue, "Alice", 3141582L).start();
		new ServerThread(requestQueue, "Bobby", 6535897L).start();
	}

}

```

## Guarded Suspension 패턴의 목적
공유자원이 존재하는 상태에서 공유자원에 자원 입력하는 객체와 출력하는 객체가 존재하면
출력하는 객체가 자원을 출력을 하려는 하나, 자원이 존재하지 않을 경우, 자원이 공유자원에
들어올 때 까지 기다림의 상태(정지상태)에 놓이게 하고, 입력하는 객체가 공유자원에 자원을
등록했을 때, 정지되어 있는 객체의 상태를 동작하는 상태로 바꾸어주는 패턴이다.

## Guarded Suspention 패턴에 필요요소
1) 공유 자원
2) 공유 자원에서 특정 메소드를 사용할 시 정지에 대한 조건

## 조건부 synchronized
Single Threaded Execution 패턴에서는  쓰레드가 한 개라도 크리티컬 섹션 안에 있으면
다른 쓰레드는 크리티컬 섹션에 들어가지 않고 대기했다.
한편, Guared Suspension 패턴에서는 쓰레드의 대기 여부가 가드 조건에 의해 결정 된다.
Guarded Suspension 패턴은 Single Threaded Execution 패턴에 조건을 부가한 것이다.

## java.util.concurrent.LinkedBlockingQueue를 사용한 예제 프로그램
Queue<Request> queue = new LinkedList<Request>(); 를 공유자원에서 사용할 경우
wait, notify, nofityAll 메소드에 대한 처리가 필요없다.

### 예시

LinkedBlockingQueueUsedRequestQueue.class

```
public class LinkedBlockingQueueUsedRequestQueue {
	private final BlockingQueue<Request> queue
		= new LinkedBlockingQueue<Request>();
	
	public Request getRequest() {
		Request req = null;
		try {
			req = queue.take();
		} catch(InterruptedException e) {
			
		}
		return req;
	}
	
	public void putRequest(Request request) {
		try {
			queue.put(request);
		} catch (InterruptedException e) {
		}
	}
}
```

데이터를 출력하는 경우
기존 코드에서는 syncronized를 사용함과 동시에 queue 내부에 조건을 걸었지만
LinkedBlockingQueue 사용하는 경우 take() 하나로 이 모든 과정이 생략이 된다


데이터를 입력하는 경우도 알리는 과정을 생략하고 put() 하나로 이전과 같은 처리가 된다

