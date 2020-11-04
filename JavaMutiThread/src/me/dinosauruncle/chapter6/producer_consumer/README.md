# Producer-Consumer Pattern

## Producer-Consumer Pattern 이란?

producer은 생산자, 데이터 작성하는 thread를
consumer는 소비자, 데이터를 이용하는 thread를 의미한다.

생산자가 소비자에게 안전하게 데이터를 전달하는 패턴이다.

다음과 같은 문제의 상태이 있다고 하자
다만 생산자와 소비자가 각각 별도의 thread로서 동작할 때
양쪽 처리속도에 차이가 있으면 문제가 발생한다.
소비자가 데이터를 받으려 하는데 데이터가 미완성 상태거나,
생산자가 데이터를 건네려 하는데 소비자가 데이터를 받을 수 없는
상태의 문제를 말한다.

Producer-Consumer Pattern은 생산자와 소비자 사이에
중개 역할로 존재하며, 이 중개 역할이 양쪽 thread 간 처리 속도의
차이를 메우게 된다


## 예제

<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Ch6AMain</td>
		<td>동작 테스트용 클래스</td>
	</tr>
	<tr>
		<td>MakerThread</td>
		<td>요리사를 나타내는 클래스</td>
	</tr>
	<tr>
		<td>EaterTrhead</td>
		<td>손님을 나타내는 클래스</td>
	</tr>
	<tr>
		<td>Table</td>
		<td>테이블을 나타내는 클래스</td>
	</tr>
</table>

Ch6AMain.class

```
public static void main(String[] args) {
		Table table = new Table(3);		// 케이크를 3개까지 놓을 수 있는 테이블이 있다
		new MakerThread("MakerThread-1", table, 31415).start();
		new MakerThread("MakerThread-2", table, 92653).start();
		new MakerThread("MakerThread-3", table, 58979).start();
		new EaterThread("EaterThread-1", table, 32384).start();
		new EaterThread("EaterThread-2", table, 62643).start();
		new EaterThread("EaterThread-3", table, 38327).start();

	}
```

MakerThread.class

```
public class MakerThread extends Thread {
	private final Random random;
	private final Table table;
	private static int id =0;	// 케이크 안내 번호(요리사 전원 공통)
	public MakerThread(String name, Table table, long seed) {
		super(name);
		this.table = table;
		this.random = new Random(seed);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(random.nextInt(10000));
				String cake = "[ Cake No. " + nextId() + " by " + getName() + "]";
				table.put(cake);
			}
		} catch (InterruptedException e) {

		}
		
	}
	private static synchronized int nextId() {
		return id++;
	}

}
```

EaterThread.class

```
public class EaterThread extends Thread {
	private final Random random;
	private final Table table;
	public EaterThread(String name, Table table, long seed) {
		super(name);
		this.table = table;
		this.random = new Random(seed);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				String cake = table.take();
				Thread.sleep(random.nextInt(1000));
			}
		} catch (InterruptedException e) {}
	}
}
```

Table.class

```
package me.dinosauruncle.chapter6.producer_consumer;

public class Table {
	private final String[] buffer;
	private int tail;	// 다음에 put할 장소
	private int head;	// 다음에 take할 장소
	private int count;	// buffer 안에 케이크 수
	public Table(int count) {
		this.buffer = new String[count];
		this.head = 0;
		this.tail = 0;
		this.count = 0;
	}
	
	// 케이크를 놓는다
	public synchronized void put (String cake) 
			throws InterruptedException {
		System.out.println(Thread.currentThread().getName()+
				" puts " + cake);
		while (count >= buffer.length) {
			wait();
		}
		buffer[tail] = cake;
		tail = (tail + 1) % buffer.length;
		count++;
		notifyAll();
	}
	
	// 케이크를 먹는다
	public synchronized String take() 
			throws InterruptedException {
		while (count <= 0) {
			wait();
		}
		String cake = buffer[head];
		head = (head + 1) % buffer.length;
		count--;
		notifyAll();
		System.out.println(Thread.currentThread().getName() + " takes " + cake);
		return cake;
	}

}
```

## Producer_Consumer Pattern 만의 특징
Producer_Consumer Pattern은 Producer Thread 객체와 Consumer Thread 객체가 
각각 Guard 조건을 가지고 있으면, 이중 Guarded Suspension Pattern으로 볼 수 있다
위 예제와 같이 Producer Thread에 해당하는 MakerThread.class는 공유자원(Table.class)에서
put method를 사용하며, 저장할 수 있는 케이크의 수와 만들어 놓은 케이크 수를 비교하므로
Guard 조건을 결정한다. 이와 반대로 Consumer Thread 객체는 EaterThread.class는
공유자원에서 take method를 사용하는데, Guard조건은 만들어 놓은 케이크의 수가 0보다 작으면 
Guard 조건을 사용한다. Table.class은 Producer와 Consumer 사이에서 Channel의 역할도
수행한다. Channel 역할은 Thread 사이에 배타제어를 실행하여 Producer 역할로부터 Consumer
역할에게 정확하게 Data를 전달한다. 

## 어떤 순서로 Data 역할을 건네야 할까
1) Queue - 먼저 받은 것부터 건넨다, FIFO(First In Firtst Out)
2) Stack - 나중에 받은 것을 먼저 건넨다 LIFO(Last In First Out)
해당 패턴에서는 추천 하지는 않는다.
3) 우선 순위를 매기는 Queue - 우선할 것을 먼저 건넨다

## 중개 역할의 존재가 가지는 의미
Producer-Consumer Pattern에서는 Producer 역할 사이에 들어가는 중개자 Channel 역할이
중요한 의미를 가진다. Channel 역할 덕분에 Producer 역할이 Consumer 역할이라고 하는 복수의
Thread가 협력과 조화를 이룰 수 있기 때문이다. 바꾸어 말하면 Channel 역할이라고 하는 중개자에
의해 Thread 협조동작이 실현되고 있는 것이다 

- Thread의 협조동작에서는 [중간에 들어가는 것]을 생각하자
- Thread의 배타제어에서는 [지켜야 할 것]을 생각하자 

협조동작과 배타제어는 하나이며 Thread가 협조동작을 하려면 공유하고 있는 것이 망가지지 않도록 
배타제어를 해야한다.


## Consumer 역할이 단수라면 어떨까
Consumer 객체가 하나라면 Consumer가 사용하는 method에 배타제어 처리를 할 필요가 없어진다.
synchronized 표시를 삭제하고 사용해도 무방하다

## InterruptedExceptiond와의 연동
multi Thread를 사용한 Java 프로그래밍에 익숙해지면 메소드에 throws Interrupted Exception이
따라오는지 아닌지를 의식하게 된다. 메소드에 throws Interrupted Exception가 붙는다는 것은 
이 method에 throws Interrupted Exception가 붙는다는 것은 이 method 안에 (혹은 이 method가 
호출하고 있는 method 안에서) InterruptedException이라고 하는 예외가 throws 될 가능성이 있다는 
뜻이다. 다음과 같은 두가지의 의미를 갖는다.

- 시간이 걸리는 method
- 취소 가능한 method

즉 throws InterruptedException가 붙는 method는 시간이 걸릴 지는 모르나 취소할 수 있는 method이다

## ArrayBlockingQueue를 이용한 Producer-Consumer

### 예제

Ch6BMain.class


```
public class Ch6BMain {
	public static void main(String[] args) {
		ArrayBlockingQueueTable table = new ArrayBlockingQueueTable(3);		// 케이크를 3개까지 놓을 수 있는 테이블이 있다
		new ArrayBlockingQueueMakerThread("MakerThread-1", table, 31415).start();
		new ArrayBlockingQueueMakerThread("MakerThread-2", table, 92653).start();
		new ArrayBlockingQueueMakerThread("MakerThread-3", table, 58979).start();
		new ArrayBlockingQueueEaterThread("EaterThread-1", table, 32384).start();
		new ArrayBlockingQueueEaterThread("EaterThread-2", table, 62643).start();
		new ArrayBlockingQueueEaterThread("EaterThread-3", table, 38327).start();
	}
}
```

ArrayBlockingQueueTable.class

```
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueTable extends ArrayBlockingQueue<String> {
	public ArrayBlockingQueueTable(int count) {
		super(count);
	}
	
	public void put(String cake) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " puts " + cake);
		super.put(cake);
	}
	
	public String take() throws InterruptedException {
		String cake = super.take();
		System.out.println(Thread.currentThread().getName() + " takes " + cake);
		return cake;
	}
	
}
```

ArrayBlockingQueueMakerThread.class

```
public class ArrayBlockingQueueMakerThread extends Thread {
	private final Random random;
	private final ArrayBlockingQueueTable table;
	private static int id =0;	// 케이크 안내 번호(요리사 전원 공통)
	public ArrayBlockingQueueMakerThread(String name, ArrayBlockingQueueTable table, long seed) {
		super(name);
		this.table = table;
		this.random = new Random(seed);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(random.nextInt(10000));
				String cake = "[ Cake No. " + nextId() + " by " + getName() + "]";
				table.put(cake);
			}
		} catch (InterruptedException e) {

		}
		
	}
	private static synchronized int nextId() {
		return id++;
	}

}
```

ArrayBlockingQueueEaterThread.class

```
public class ArrayBlockingQueueEaterThread extends Thread {
	private final Random random;
	private final ArrayBlockingQueueTable table;
	public ArrayBlockingQueueEaterThread(String name, ArrayBlockingQueueTable table, long seed) {
		super(name);
		this.table = table;
		this.random = new Random(seed);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				String cake = table.take();
				Thread.sleep(random.nextInt(1000));
			}
		} catch (InterruptedException e) {}
	}
}
```


---
기존에 작성한 Producer-Consumer Patter 코드와의 차이점은 Channel에 들어가던 guard 조건을 넣지않고
ArrayBlockingQueue 클래스를 상속하므로 ArrayBlockingQueue 구현된 guard 조건을 사용할 수 있다.
