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

 
 ###