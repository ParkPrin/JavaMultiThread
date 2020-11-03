# Balking Pattern

## Balking Pattern이란?
특정 처리를 실행하면 곤란하다거나 혹은 당장 실행할 필요가 없는 경우
처리 직전에 실행을 중단하고 돌아 가는 패턴

## 예제

<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Data</td>
		<td>변경·저장이 가능한 데이터를 나타내는 클래스</td>
	</tr>
	<tr>
		<td>SaverThread</td>
		<td>데이터의 내용을 정기적으로 저장하는 클래스</td>
	</tr>
	<tr>
		<td>ChangerThread</td>
		<td>데이터의 내용을 변경·저장하는 클래스</td>
	</tr>
	<tr>
		<td>Ch5AMain</td>
		<td>동작 테스트용 클래스</td>
	</tr>
</table>

---

Data.class

```
package me.dinosauruncle.chapter5.balking;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Data {
	private final String filename;	// 저장하는 파일명
	private String content;
	private boolean changed;
	
	public Data(String filename, String content) {
		this.filename = filename;
		this.content = content;
		this.changed = true;
	}
	
	// 데이터의 내용을 수정한다
	public synchronized void change(String newContent) {
		content = newContent;
		changed = true;
	}
	
	
	// 데이터의 내용이 변경되었으면 파일에 저장한다
	public synchronized void save() throws IOException {
		if (!changed) {
			return;
		}
		doSave();
		changed = false;
		
	}
	
	// 데이터의 내용을 실제로 파일에 저장한다
	private void doSave() throws IOException {
		System.out.println(Thread.currentThread().getName() 
				+ " calls doSave, content = " + content);
		Writer writer = new FileWriter(filename);
		writer.write(content);
		writer.close();
	}
}

```

SaverThread.class

```
package me.dinosauruncle.chapter5.balking;

import java.io.IOException;

public class SaverThread extends Thread {
	private final Data data;
	public SaverThread(String name, Data data) {
		super(name);
		this.data = data;
	}
	
	@Override
	public void run() {
	
		try {
			while (true) {
				data.save();			// 데이터를 저장하려 한다
				Thread.sleep(1000);		// 약 1초 휴식
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

```

ChangerThread.class

```
package me.dinosauruncle.chapter5.balking;

import java.io.IOException;
import java.util.Random;

public class ChangerThread extends Thread {
	private final Data data;
	private final Random random = new Random();
	public ChangerThread(String name, Data data) {
		super(name);
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			for (int i = 0; true; i++) {
				data.change("No. " + i);				// 데이터를 변경한다
				Thread.sleep(random.nextInt(1000));		// 작업
				data.save();							// 명시적으로 저장한다.
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

```

Ch5AMain.class

```
package me.dinosauruncle.chapter5.balking;

public class Ch5AMain {

	public static void main(String[] args) {
		Data data = new Data("data.txt", "(empty)");
		new ChangerThread("ChangerThread", data).start();
		new SaverThread("SaverThread", data).start();

	}

}

```

Balking Pattern의 핵심은 실행 필요유무를 결정하는 조건에 있으며
이 조건을 결정하는 변수나 필드를 플래그(flag: 깃발)라고 지징한다.

본 예제의 플래그는 Data 클래스의 changed 필드이다.

Data 클래스 안에는 내용을 수정하거나, 저장, 파일저장하는 기능이 모여있으며
Data 클래스는 공유자원에 해당한다.
이 공유자원 사용하는 SaverThread와 ChangerThread는 정기적인 동작을 하면서
내용을 저장하거나 변경작업을 진행한다. 각 작업을 진행할 때마다 콘솔에 기록이 남는다.
본 프로그램의 핵심은 수정된 내용이 있는 상태에서 저장을 하면 저장이 진행되지만
수엉된 내용이 없을시 저장 메소드는 실행되지 않는다.
이를 판단하는 것이 플래그에 해당하는 changed 필드이다.

## 어떤 경우에 사용하는가 (적용 가능성)
1) 진행중 사용하지 않는 경우가 발생할 때
2) 가드 조건이 충족되기를 기다리고 싶지 않을 때(guarded suspension의 반대개념)
3) 가드 조건을 만족하는 것이 처음 1회인 경우

## Balking Pattern과 Guarded Suspension Pattern의 중간
Balking Pattern에서는 가드 조건을 만족하지 않을 경우 balk하고 돌아간다.
그러나 Guarded Suspension Pattern에서는 가드 조건이 만족될 때까지 기다린다.
서로 반대되는 개념인데, 이 주간단계의 개념은
"가드 조건을 만족할 때까지 일정 시간 기다린다"
이며 이를 Guarded Timed로 명명한다.

## Guarded Timed 예제

Host.class

```
package me.dinosauruncle.chapter5.balking;

import java.util.concurrent.TimeoutException;

public class Host {
	private final long timeout; // 타임 아웃의 값
	private boolean ready = false; // 메소드를 실행해도 되면 true
	
	public Host(long timeout) {
		this.timeout = timeout;
	}
	
	// 상태를 변경한다
	
	public synchronized void setExecutable(boolean on) {
		ready = on;
		notifyAll();
	}
	
	// 상태를 고려하여 실행한다
	public synchronized void execute() 
			throws InterruptedException,TimeoutException{
		long start = System.currentTimeMillis(); // 개시 시각
		while (!ready) {
			long now = System.currentTimeMillis(); // 현재 시각
			long rest = timeout - (now - start);  //나머지 대기 시간
			
			if (rest <= 0) {
				throw new TimeoutException("now - start = "
						+ (now - start) + ", timeout = " + timeout);
			}
			wait(rest);
		}
		doExecute();
		
	}
	
	// 실제 처리
	private void doExecute() {
		System.out.println(Thread.currentThread().getName() + " calls doExecute");
	}
}

```

Ch5BMain.class

```
package me.dinosauruncle.chapter5.balking;

import java.util.concurrent.TimeoutException;

public class Ch5BMain {

	public static void main(String[] args) {
		Host host = new Host(10000);
		try {
			System.out.println("execute BEGIN");
			host.execute();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

```

타임아웃시간을 정해놓고 wait시간이 타임아웃 시간을 초과하면 TimeoutException 발생시킨다

## 정리
객체가 상태를 가지고 있고 자신의 상태가 적절할 때에만 처리를 실행하고
적절하지 않을 때 실행하지 않는 것이 balking pattern이다.
if문을 통해서 상태를 점검하며, 실행상태 조건에 맞지 않는 경우 
return이나 해당하는 exception 처리를 한다.
