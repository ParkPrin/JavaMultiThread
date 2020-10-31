# Guarded Suspension Pattern

## Guarded Suspension �̶�?
Guarded [��ȣ�ް� �ִ�]�� Suspension [�Ͻ� ������]�� ģ ���ν�
������ �����尡 ���ÿ� �۾��� ����� �� �����带 ��ٸ��� �Ͽ� �ν��Ͻ��� �������� ��ȣ�Ѵ�.

## ����

<table>
	<tr>
		<td>�̸�</td>
		<td>�ؼ�</td>
	</tr>
	<tr>
		<td>Request</td>
		<td>�� ���� ������Ʈ�� ǥ���� Ŭ����</td>
	</tr>
	<tr>
		<td>RequestQueue</td>
		<td>������Ʈ�� ������� ������ �δ� Ŭ����</td>
	</tr>
	<tr>
		<td>ClientThread</td>
		<td>������Ʈ�� �����ϴ� Ŭ����</td>
	</tr>
	<tr>
		<td>ServerThread</td>
		<td>������Ʈ�� �����ϴ� Ŭ����</td>
	</tr>
	<tr>
		<td>Ch4AMain</td>
		<td>���� �׽�Ʈ�� Ŭ����</td>
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
	
	// ServerThread��ü�� ����ϴ� method�̴�
	public synchronized Request getRequest() {
	// queuje.peek()�� Queue��ü �ȿ� request�� �����ϴ��� ���θ� �˷��ش�. ������ null�� ��ȯ�Ѵ�
		while (queue.peek() == null ) {
			try {
				// wait�� �ɸ��� ���� ServerThread�� ��ٸ��� ���¿� ��������.
				// notify() �Ǵ� notifyAll()�� ����Ǿ�� ������ �� �ִ�.
				wait();
			} catch (InterruptedException e) {

			}
		}
		return queue.remove();
	}
		// ClientThread��ü�� ����ϴ� method�̴�
	public synchronized void putRequest (Request request) {
		// queue.offer�� queue �ȿ� request ��ü�� �ִ´�. 
		queue.offer(request);
		// notifyAll()�� ����Ǵ� ���� ��ٸ��� ���¿� ������ ServerThread�� ������ �����Ѵ�.
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

## Guarded Suspension ������ ����
�����ڿ��� �����ϴ� ���¿��� �����ڿ��� �ڿ� �Է��ϴ� ��ü�� ����ϴ� ��ü�� �����ϸ�
����ϴ� ��ü�� �ڿ��� ����� �Ϸ��� �ϳ�, �ڿ��� �������� ���� ���, �ڿ��� �����ڿ���
���� �� ���� ��ٸ��� ����(��������)�� ���̰� �ϰ�, �Է��ϴ� ��ü�� �����ڿ��� �ڿ���
������� ��, �����Ǿ� �ִ� ��ü�� ���¸� �����ϴ� ���·� �ٲپ��ִ� �����̴�.

## Guarded Suspention ���Ͽ� �ʿ���
1) ���� �ڿ�
2) ���� �ڿ����� Ư�� �޼ҵ带 ����� �� ������ ���� ����

## ���Ǻ� synchronized
Single Threaded Execution ���Ͽ�����  �����尡 �� ���� ũ��Ƽ�� ���� �ȿ� ������
�ٸ� ������� ũ��Ƽ�� ���ǿ� ���� �ʰ� ����ߴ�.
����, Guared Suspension ���Ͽ����� �������� ��� ���ΰ� ���� ���ǿ� ���� ���� �ȴ�.
Guarded Suspension ������ Single Threaded Execution ���Ͽ� ������ �ΰ��� ���̴�.

## java.util.concurrent.LinkedBlockingQueue�� ����� ���� ���α׷�
Queue<Request> queue = new LinkedList<Request>(); �� �����ڿ����� ����� ���
wait, notify, nofityAll �޼ҵ忡 ���� ó���� �ʿ����.

### ����

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

�����͸� ����ϴ� ���
���� �ڵ忡���� syncronized�� ����԰� ���ÿ� queue ���ο� ������ �ɾ�����
LinkedBlockingQueue ����ϴ� ��� take() �ϳ��� �� ��� ������ ������ �ȴ�


�����͸� �Է��ϴ� ��쵵 �˸��� ������ �����ϰ� put() �ϳ��� ������ ���� ó���� �ȴ�

