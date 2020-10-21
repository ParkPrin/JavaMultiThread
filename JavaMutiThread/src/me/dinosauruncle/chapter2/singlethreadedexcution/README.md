# Single Threaded Execution ����

## Single Threaded Execution �����̶�?

�� ���� �����忡 ���� ���� => ��Ƽ ������ ���α׷����� ����
�ٸ� ��Ī���δ� Critical Section(�ƽ��ƽ��� ����, ���� ����) Ȥ�� Critical Region

���� �� ���� �볪�� ������ �ǳʴ� ����� ���뼭 ���� ����

## Single Threaded Execution �� ������� �ʴ� ����

```
���α׷������� �� ���� �� ��� �ۿ� �ǳ� �� ���� ��(gate)�� 3���� ����ϴ� ����� �����Ѵ�.
�� ����� ���� ����� ������ �� ���� ����, �� ����ϴ� ����� [�̸��� �����]�� ����ϰ� �ȴ�
```


<table>
	<tr>
		<td>�̸�</td>
		<td>�ؼ�</td>
	</tr>
	<tr>
		<td>Main</td>
		<td>���� �����, 3���� ������ �����Ű�� Ŭ����</td>
	</tr>
	<tr>
		<td>Gate</td>
		<td>���� ǥ���ϴ� Ŭ����, ����� ������ �� �̸��� ������� ����Ѵ�.</td>
	</tr>
	<tr>
		<td>User Thread</td>
		<td>����� ��Ÿ���� Ŭ����, ���� ����Ѵ�.</td>
	</tr>
</table>

Ch2AMain.java, Gate.java, UserThread.java �ڵ带 �̿��ؼ� ����


<h5>���� �����带 ����� �̸��� ������� ù���ڰ� �������� �ұ��ϰ� ����ġ�� ���� ������ �ֿܼ��� �߻��Ѵ�</h5>
=> ���� ������ ����� ������ ���̽��� �ϸ鼭 ������ ������ �߻��Ѵ� 

������ �Ǵ� �κ��� ������ ����

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
	// ������ ������ �߻��ϴ� �޼ҵ� 1
	public String toString() {
		return "No. " + counter + " : " +  name + ", " + address;
	}

	// ������ ������ �߻��ϴ� �޼ҵ� 2	
	private void check() {
		if (name.charAt(0) != address.charAt(0)) {
			System.out.println("*****BROKEN*****" + toString());
		}
	}

}

```

## Single Threaded Execution �� ����ϴ� ����

toString�� check �޼ҵ带 ������ �����尡 ���ÿ� ����� ��� �����Ͱ� ���̰� �Ǵ� ������ �߻��ϰԵȴ�

�̸� �����ϱ� ���ؼ�(��Ÿ��� �ϱ� ���ؼ�) ����ȭó���� �ϴ� synchronized ǥ�ø� �޼ҵ忡 �ؾ��Ѵ�

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

	// ������ ������ �߻��ϴ� �޼ҵ� 2	
	private synchronized void check() {
		if (name.charAt(0) != address.charAt(0)) {
			System.out.println("*****BROKEN*****" + toString());
		}
	}
```

 �޼ҵ忡 ����ȭ ó���� �����ϸ� �ߵ��ϴ� �޼����� �ֿܼ��� ��������� �߰��� �� �ִ�.

## SharedResource(�����ڿ�)
Single Threaded Execution ���Ͽ��� SharedResource ������ �����ϸ� �������� Gate.java�� �̿� �ش���
SharedResource���� �� ���� �з��� �޼ҵ尡 �����ϴµ�
1) safeMethod: ������ thread���� ���ÿ� ȣ���ص� �ƹ��� ������ ���� �޼ҵ�
2) unsafeMethod: ������ �����忡�� ���ÿ� ȣ���ϸ� �� �Ǳ� ������ ����(Guard)�� �ʿ��� �޼ҵ�

Java������ unsafeMethod�� synchronized�� ��������� ������

## ��� �� ����ϴ°�(���� ���ɼ�)
1) ��Ƽ������
2) ������ �����尡 �׼����� ��
3) ���°� ��ȭ�� ���ɼ��� ���� ��
4) �������� Ȯ���� �ʿ䰡 ���� ��

## ����̻�
��Ƽ thread ���α׷��ֿ����� ����� ���ŷο� ������ ���� �� ���� �̸� ��� �̻�(inhertitance anomaly)�̶�� �Ѵ�


## ũ��Ƽ�� ������ ũ��� ���� �ɷ�
�Ϲ������� Single Threaded Execution ������ ���� �ɷ��� ����߸���.

### 1. ���� ����ϴµ� �ð��� �ɸ��� ������

### 2. �������� �浹�� ����ϱ� ������


## ��� ��������� Semaphore Ŭ����

Single Threaded Execution�� ��� ������ [�� �� ���� Thread]�� �����ϴ� �����̶��
�Ϲ�ȭ�Ͽ� � ������ [�ִ� N���� ������]���� ���� �� �� �ֵ��� �����ϰ� ���� �� ����ϴ� �����
��� ���������� �Ѵ�. ��� ��������� �뷮�� �����Ѵ�.

java.util.concurrent ��Ű������ ��� ������� ��Ÿ���� Semaphore Ŭ������ �����Ѵ�.


## Semaphore Ŭ������ ����� ���� ���α׷�
10���� ��Ƽ������� 3���� �ڿ��� ������ ���α׷�

main ���� (Ch2Main.java)

```
public static void main(String[] args) {
	// 3���� ���ҽ��� �غ��Ѵ�
	BoundedResource resource = new BoundedResource(3);
	
	//  10���� �����尡 ����Ѵ�.
	for (int i =0; i < 10; i++) {
		new Ch2UserThread(resource).start();
	}	
}
```

ShareResource ����(BoundedResource.java)


```
	private final Semaphore semaphore;
	private final int permits;
	private final static Random random = new Random(314159);
	
	//Ŭ���� ������(permits�� ���ҽ��� ����)
	public BoundedResource(int permits) {
		this.semaphore = new Semaphore(permits);
		this.permits = permits;
	
	}
	
	// ���ҽ��� ����Ѵ�
	public void use() throws InterruptedException {
		semaphore.acquire(); // ����� �� �ִ� ���ҽ��� �ִ��� ����, ��� ���ҽ��� ��� ���̸� �����
		try {
			doUse();
		} finally {
			semaphore.release(); // ����� ���ҽ��� �����Ѵ�
		}
	}
	
	// ���ҽ��� ������ ����Ѵ�.(���⿡���� Thread.sleep�ϰ� ���� ��)
	protected void doUse() throws InterruptedException{
		Log.println("BEGIN: used = " + (permits - semaphore.availablePermits()));
		Thread.sleep(random.nextInt(500));
		Log.println("END:   used= " +  (permits - semaphore.availablePermits()));
	}
```

Thread ���� (Ch2UserThread.java)

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

Log ����, �����߿����� ���� (Log.java)


```
	public static void println(String s) {
		System.out.println(Thread.currentThread().getName() + ": " + s);
	}
```
