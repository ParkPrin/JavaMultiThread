# Single Threaded Execution 패턴

## Single Threaded Execution 패턴이란?

한 개의 쓰레드에 의한 실행 => 멀티 쓰레드 프로그래밍의 기초
다른 명칭으로는 Critical Section(아슬아슬한 영역, 위험 구역) 혹은 Critical Region

절벽 위 일자 통나무 위에서 건너는 사람을 빗대서 만든 개념

## Single Threaded Execution 을 사용하지 않는 예시

```
프로그램에서는 한 번에 한 사람 밖에 건널 수 없는 문(gate)을 3명이 통과하는 모습을 실험한다.
즉 사람이 문을 통과할 때마다 그 수를 세고, 또 통과하는 사람의 [이름과 출신지]를 기록하게 된다
```


<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Main</td>
		<td>문을 만들고, 3명을 문으로 통과시키는 클래스</td>
	</tr>
	<tr>
		<td>Gate</td>
		<td>문을 표현하는 클래스, 사람이 지나갈 때 이름과 출신지를 기록한다.</td>
	</tr>
	<tr>
		<td>User Thread</td>
		<td>사람을 나타내는 클래스, 문을 통과한다.</td>
	</tr>
</table>

Ch2AMain.java, Gate.java, UserThread.java 코드를 이용해서 만듬


<h5>복수 쓰레드를 실행시 이름과 출신자의 첫글자가 같음에도 불구하고 불일치에 대한 내용이 콘솔에서 발생한다</h5>
=> 복수 쓰레드 실행시 데이터 레이스를 하면서 데이터 간섭이 발생한다 

문제가 되는 부분은 다음과 같다

```
package me.dinosauruncle.chapter2.singlethreadedexcution;

public class Gate {
	private int counter =0;
	private String name = "Nobody";
	private String address = "Nowhere";
	public void pass(String name, String address) {
		this.counter++;
		this.name = name;
		this.address = address;
		check();
	}
	// 데이터 간섭이 발생하는 메소드 1
	public String toString() {
		return "No. " + counter + " : " +  name + ", " + address;
	}

	// 데이터 간섭이 발생하는 메소드 2	
	private void check() {
		if (name.charAt(0) != address.charAt(0)) {
			System.out.println("*****BROKEN*****" + toString());
		}
	}

}

```

## Single Threaded Execution 을 사용하는 예시

toString과 check 메소드를 복수의 쓰레드가 동시에 사용할 경우 데이터가 꼬이게 되는 현상이 발생하게된다

이를 방지하기 위해서(배타제어를 하기 위해서) 동기화처리를 하는 synchronized 표시를 메소드에 해야한다

```
	public synchronized void pass(String name, String address) {
		this.counter++;
		this.name = name;
		this.address = address;
		check();
	}

	public synchronized String toString() {
		return "No. " + counter + " : " +  name + ", " + address;
	}

	// 데이터 간섭이 발생하는 메소드 2	
	private synchronized void check() {
		if (name.charAt(0) != address.charAt(0)) {
			System.out.println("*****BROKEN*****" + toString());
		}
	}
```

 메소드에 동기화 처리후 실행하면 추돌하는 메세지가 콘솔에서 사라졌음을 발견할 수 있다.

## SharedResource(공유자원)
Single Threaded Execution 패턴에는 SharedResource 역할이 존재하면 예제에서 Gate.java가 이에 해당함
SharedResource에는 두 가지 분류의 메소드가 존재하는데
1) safeMethod: 복수의 thread에서 동시에 호출해도 아무런 문제가 없는 메소드
2) unsafeMethod: 복수의 쓰레드에서 동시에 호출하면 안 되기 때문에 가드(Guard)가 필요한 메소드

Java에서는 unsafeMethod를 synchronized를 사용함으서 가드함

## 어느 때 사용하는가(적용 가능성)
1) 멀티쓰레드
2) 복수의 쓰레드가 액세스할 때
3) 상태가 변화할 가능성이 있을 때
4) 안전성이 확보할 필요가 있을 때

## 상속이상
멀티 thread 프로그래밍에서는 상속이 번거로운 문제를 만들 수 있음 이를 상속 이상(inhertitance anomaly)이라고 한다


## 크리티컬 섹션의 크기와 수행 능력
일반적으로 Single Threaded Execution 패턴은 수행 능력을 떨어뜨린다.

### 1. 락을 취득하는데 시간이 걸리기 때문에

### 2. 쓰레드의 충돌로 대기하기 때문에


## 계수 세마포어와 Semaphore 클래스

Single Threaded Execution은 어떠한 영역을 [단 한 개의 Thread]만 실행하는 패턴이라면
일반화하여 어떤 영역을 [최대 N개의 쓰레드]까지 실행 할 수 있도록 설정하고 싶을 때 사용하는 기술을
계수 세마포어라고 한다. 계수 세마포어는 용량을 제어한다.

java.util.concurrent 패키지에서 계수 세마포어를 나타내는 Semaphore 클래스를 제공한다.


## Semaphore 클래스를 사용한 예제 프로그램
10개의 멀티쓰레드로 3개의 자원을 돌리는 프로그램

main 영역 (Ch2Main.java)

```
public static void main(String[] args) {
	// 3개의 리소스를 준비한다
	BoundedResource resource = new BoundedResource(3);
	
	//  10개의 쓰레드가 사용한다.
	for (int i =0; i < 10; i++) {
		new Ch2UserThread(resource).start();
	}	
}
```

ShareResource 영역(BoundedResource.java)


```
	private final Semaphore semaphore;
	private final int permits;
	private final static Random random = new Random(314159);
	
	//클래스 생성자(permits는 리소스의 개수)
	public BoundedResource(int permits) {
		this.semaphore = new Semaphore(permits);
		this.permits = permits;
	
	}
	
	// 리소스를 사용한다
	public void use() throws InterruptedException {
		semaphore.acquire(); // 사용할 수 있는 리소스가 있는지 조사, 모든 리소스가 사용 주이면 블록함
		try {
			doUse();
		} finally {
			semaphore.release(); // 사용한 리소스를 해제한다
		}
	}
	
	// 리소스를 실제로 사용한다.(여기에서는 Thread.sleep하고 있을 뿐)
	protected void doUse() throws InterruptedException{
		Log.println("BEGIN: used = " + (permits - semaphore.availablePermits()));
		Thread.sleep(random.nextInt(500));
		Log.println("END:   used= " +  (permits - semaphore.availablePermits()));
	}
```

Thread 영역 (Ch2UserThread.java)

```
	private final static Random random = new Random();
	private final BoundedResource resource;
	
	public Ch2UserThread(BoundedResource resource) {
		this.resource =  resource;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				resource.use();
				Thread.sleep(random.nextInt(3000));
			}
		} catch (InterruptedException e) {
			
		}
	}
```

Log 영역, 별로중요하지 않음 (Log.java)


```
	public static void println(String s) {
		System.out.println(Thread.currentThread().getName() + ": " + s);
	}
```
