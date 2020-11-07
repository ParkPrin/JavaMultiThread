# Read-Write Pattern

## Read-Write Pattern이란?

Thread가 인스턴스의 상태를  [읽는다]고 하는 처리를 실행해도 인스턴스의 상태를 바뀌지 않는다.
인스턴스의 상태가 바뀌는 것은 쓰레드가 [쓰다]라고 하는 처리를 실행했을때이다.
인스턴스의 상태 변화의 관점에서 볼 때 읽는다와 쓰다는 본질적으로 다르다.

읽기 처리를 실행해도 인스턴스의 상태가 바뀌지 않으므로 여러 개의 쓰레드가
동시에 읽는 것은 문제가 없다. 하지만 읽고 있는 중간에는 쓸 수 없다.
쓰기를 실행하면 인스턴스의 상태가 달라지기 때문에 어느 한 쓰레드가 쓰기를
하는 중에는 다른 쓰레드가 읽고 쓰는 것이 불가능하다.
=> 배타제어를 하면 수행 능력이 떨어지지만 쓰기에 대한 배타제어와 읽기에 
대한 배타제어를 분리해서 생각하면 수행 능력을 높일 수가 있다

## 예제

<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Ch7AMain</td>
		<td>동작 테스트용 클래스</td>
	</tr>
	<tr>
		<td>Data</td>
		<td>읽고 쓰기가 가능한 클래스</td>
	</tr>
	<tr>
		<td>WriterThread</td>
		<td>쓰려고 하는 쓰레드를 나타내는 클래스</td>
	</tr>
	<tr>
		<td>ReadWriteLock</td>
		<td>읽고 쓰기의 락을 제공하는 클래스</td>
	</tr>
</table>


Ch7AMain.class

```
public class Ch7AMain {
	public static void main(String[] args) {
		Data data = new Data(10);
		new ReaderThread(data).start();
		new ReaderThread(data).start();
		new ReaderThread(data).start();
		new ReaderThread(data).start();
		new ReaderThread(data).start();
		new ReaderThread(data).start();
		new WriterThread(data, "ABCDEFGHIJKLMNOPQRSTUVWXYZ").start();
		new WriterThread(data, "abcdefghijklmnopqrstuvwxyz").start();
	}
}
```

Data.class

```
package me.dinosauruncle.chapter7.read_write_lock;

public class Data {
	private final char[] buffer;
	private final ReadWriteLock lock = new ReadWriteLock();
	public Data(int size) {
		this.buffer = new char[size];
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = '*';
		}
	}
	
	public char[] read() throws InterruptedException {
		lock.readLock();
		try {
			return doRead();
		} finally {
			lock.readUnlock();
		}
	}
	
	public void write (char c) throws InterruptedException {
		lock.writeLock();
		try {
			doWrite(c);
		} finally {
			lock.writeUnlock();
		}
	}
	
	private char[] doRead() {
		char[] newbuf = new char[buffer.length];
		for (int i = 0; i < buffer.length; i++) {
			newbuf[i] = buffer[i];
		}
		slowly();
		return newbuf;
	}
	
	private void doWrite(char c) {
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = c;
			slowly();
		}
	}
	
	private void slowly() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {}
	}
}

```

ReaderThread.class

```
public class ReaderThread extends Thread {
	private final Data data;
	public ReaderThread(Data data) {
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				char[] readbuf = data.read();
				System.out.println(Thread.currentThread().getName() + " reads " + String.valueOf(readbuf));
			}
		} catch (InterruptedException e) {
			
		}
	}

}
```

ReadWriteLock.class

```
public final class ReadWriteLock {
	private int readingReaders = 0; // [A] 실제로 읽고 있는 중인 쓰레드의 수
	private int waitingWriters = 0; // [B] 쓰기를 기다리고 있는 쓰레드의 수
	private int writingWriters = 0; // [C] 실제로 쓰고 있는 중인 쓰레드의 수
	private boolean preferWriter = true; // 쓰는 것을 우선하면 true;
	
	public synchronized void readLock() throws InterruptedException {
		while (writingWriters > 0 || preferWriter && waitingWriters > 0) {
			wait();
		}
		readingReaders++;	// (A) 실제로 읽고 있는 쓰레드의 수를 한 개 늘린다.
	}
	
	public synchronized void readUnlock() {
		readingReaders--;	// (A) 실제로 읽고 있는 쓰레드의 수를 한 개 줄인다.
		preferWriter = true;
		notifyAll();
	}
	
	public synchronized void writeLock() throws InterruptedException {
		waitingWriters++;	// (B) 쓰기를 기다리고 있는 쓰레드의 수를 한 개 늘린다
		try {
			while (readingReaders > 0 || writingWriters > 0) {
				wait();
			}
		} finally {
			waitingWriters--;	// (B) 쓰기를 기다리고 있는 쓰레드의 수를 한 개 줄인다.
		}
		writingWriters++;	// (C) 실제로 쓰고 있는 쓰레드의 수를 한 개 늘린다.
	}
	
	public synchronized void writeUnlock() {
		writingWriters--;	// (C) 실제로 쓰고 있는 쓰레드의 수를 한 개 줄인다.
		preferWriter = false;
		notifyAll();
	}
}
```

WriterThread.class

```
public class WriterThread extends Thread {
	private static final Random random = new Random();
	private final Data data;
	private final String filler;
	private int index =0;
	public WriterThread(Data data, String filler) {
		this.data = data;
		this.filler = filler;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				char c = nextchar();
				data.write(c);
				Thread.sleep(random.nextInt(3000));
			}
		} catch (InterruptedException e) {
			
		}
	}
	
	private char nextchar () {
		char c = filler.charAt(index);
		index++;
		if (index >= filler.length()) {
			index =0;
		}
		return c;
	}

}

```

ReaderThread는 데이터를 읽는 쓰레드이며, 실제 데이터를 읽는 것에 기능을 가지고 있지 않으며
실제 데이터를 읽는 처리는 Data클래스가 전담한다. WriterThrea는 데이터를 쓰는 쓰레드이며
데이터를 쓰는 처리는 Data클래스가 전담한다. 그러나 데이터를 읽고 쓸때 각 쓰레드마다 Data
클래스를 점유하는 상황이 발생하고 그로인하여 점유하는(락을 처리하는) 규칙을 담당하는 클래스를
따로 규정했는데 이것이 ReadWriterLock 클래스이다. 

ReaderThread가 Data 클래스를 중복해서 점유해서 읽는 작업을 진행하는 것은 가능하다
그러나 WriterThread는 오직 하나의 쓰레드만 Data 클래스를 점유해서 쓰는 작업을 하도록
ReadWriterLock 클래스에서 규정하고 있다.

충돌나는 경우를 표현

<table>
	<tr>
		<td></td>
		<td>읽다</td>
		<td>쓰다</td>
	</tr>
	<tr><td>읽다</td>
		<td>총돌 없음</td>
		<td>[읽기]와 [쓰기]의 충돌 (read-write confilict}</td>
	</tr>
	<tr>
		<td>쓰다</td>
		<td>[읽기]와 [쓰기]의 충돌 (read-write confilict}</td>
		<td>[쓰기]와 [쓰기]의 충돌 (write-write confilict}</td>
	</tr>
</table>

## Read-Write Lock Pattern의 특징과 사용되는 경우
1. [읽기] 처리끼리는 충돌하지 않는 점을 이용하여 수행 능력을 높였다
2. 일기 처리가 무거울 때 유효
3. 읽기 빈도가 쓰기 빈도보다 높을 때 유효

## java.util.concurrent.locks를 사용한 예제 프로그램
java.util.concurrent.locks.ReadWriteLock 인터페이스는 예제 프로그램에서 작성한
ReadWriteLock 클래스와 다르며, [읽기 위한 락]과 [쓰기 위한 락]을 별도의 객체로서 취급한다.

<table>
	<tr>
		<td>예제 프로그램에서 만든 ReadWriteLock 클래스</td>
		<td>java.util.concurrent.locks 패키지의 ReadWriteLock 인터페이</td>
	</tr>
	<tr>
		<td>readLock()</td>
		<td>readLock().lock()</td>
	</tr>
	<tr>
		<td>readUnlock()</td>
		<td>readUnlock().unlock()</td>
	</tr>
	<tr>
		<td>writeLock()</td>
		<td>writeLock().lock</td>
	</tr>
	<tr>
		<td>writeUnlock()</td>
		<td>writeUnlock().unlock</td>
	</tr>
</table>


