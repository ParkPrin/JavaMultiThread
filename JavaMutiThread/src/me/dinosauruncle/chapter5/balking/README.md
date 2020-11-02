# Balking Pattern

## Balking Pattern�̶�?
Ư�� ó���� �����ϸ� ����ϴٰų� Ȥ�� ���� ������ �ʿ䰡 ���� ���
ó�� ������ ������ �ߴ��ϰ� ���� ���� ����

## ����

<table>
	<tr>
		<td>�̸�</td>
		<td>�ؼ�</td>
	</tr>
	<tr>
		<td>Data</td>
		<td>���桤������ ������ �����͸� ��Ÿ���� Ŭ����</td>
	</tr>
	<tr>
		<td>SaverThread</td>
		<td>�������� ������ ���������� �����ϴ� Ŭ����</td>
	</tr>
	<tr>
		<td>ChangerThread</td>
		<td>�������� ������ ���桤�����ϴ� Ŭ����</td>
	</tr>
	<tr>
		<td>Ch5AMain</td>
		<td>���� �׽�Ʈ�� Ŭ����</td>
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
	private final String filename;	// �����ϴ� ���ϸ�
	private String content;
	private boolean changed;
	
	public Data(String filename, String content) {
		this.filename = filename;
		this.content = content;
		this.changed = true;
	}
	
	// �������� ������ �����Ѵ�
	public synchronized void change(String newContent) {
		content = newContent;
		changed = true;
	}
	
	
	// �������� ������ ����Ǿ����� ���Ͽ� �����Ѵ�
	public synchronized void save() throws IOException {
		if (!changed) {
			return;
		}
		doSave();
		changed = false;
		
	}
	
	// �������� ������ ������ ���Ͽ� �����Ѵ�
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
				data.save();			// �����͸� �����Ϸ� �Ѵ�
				Thread.sleep(1000);		// �� 1�� �޽�
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
				data.change("No. " + i);				// �����͸� �����Ѵ�
				Thread.sleep(random.nextInt(1000));		// �۾�
				data.save();							// ��������� �����Ѵ�.
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

Balking Pattern�� �ٽ��� ���� �ʿ������� �����ϴ� ���ǿ� ������
�� ������ �����ϴ� ������ �ʵ带 �÷���(flag: ���)��� ��¡�Ѵ�.

�� ������ �÷��״� Data Ŭ������ changed �ʵ��̴�.

Data Ŭ���� �ȿ��� ������ �����ϰų�, ����, ���������ϴ� ����� ��������
Data Ŭ������ �����ڿ��� �ش��Ѵ�.
�� �����ڿ� ����ϴ� SaverThread�� ChangerThread�� �������� ������ �ϸ鼭
������ �����ϰų� �����۾��� �����Ѵ�. �� �۾��� ������ ������ �ֿܼ� ����� ���´�.
�� ���α׷��� �ٽ��� ������ ������ �ִ� ���¿��� ������ �ϸ� ������ ���������
������ ������ ������ ���� �޼ҵ�� ������� �ʴ´�.
�̸� �Ǵ��ϴ� ���� �÷��׿� �ش��ϴ� changed �ʵ��̴�.

## � ��쿡 ����ϴ°� (���� ���ɼ�)
1) ������ ������� �ʴ� ��찡 �߻��� ��
2) ���� ������ �����Ǳ⸦ ��ٸ��� ���� ���� ��(guarded suspension�� �ݴ밳��)
3) ���� ������ �����ϴ� ���� ó�� 1ȸ�� ���

## Balking Pattern�� Guarded Suspension Pattern�� �߰�
Balking Pattern������ ���� ������ �������� ���� ��� balk�ϰ� ���ư���.
�׷��� Guarded Suspension Pattern������ ���� ������ ������ ������ ��ٸ���.
���� �ݴ�Ǵ� �����ε�, �� �ְ��ܰ��� ������
"���� ������ ������ ������ ���� �ð� ��ٸ���"
�̸� �̸� Guarded Timed�� ����Ѵ�.

## Guarded Timed ����

Host.class

```
package me.dinosauruncle.chapter5.balking;

import java.util.concurrent.TimeoutException;

public class Host {
	private final long timeout; // Ÿ�� �ƿ��� ��
	private boolean ready = false; // �޼ҵ带 �����ص� �Ǹ� true
	
	public Host(long timeout) {
		this.timeout = timeout;
	}
	
	// ���¸� �����Ѵ�
	
	public synchronized void setExecutable(boolean on) {
		ready = on;
		notifyAll();
	}
	
	// ���¸� ����Ͽ� �����Ѵ�
	public synchronized void execute() 
			throws InterruptedException,TimeoutException{
		long start = System.currentTimeMillis(); // ���� �ð�
		while (!ready) {
			long now = System.currentTimeMillis(); // ���� �ð�
			long rest = timeout - (now - start);  //������ ��� �ð�
			
			if (rest <= 0) {
				throw new TimeoutException("now - start = "
						+ (now - start) + ", timeout = " + timeout);
			}
			wait(rest);
		}
		doExecute();
		
	}
	
	// ���� ó��
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

Ÿ�Ӿƿ��ð��� ���س��� wait�ð��� Ÿ�Ӿƿ� �ð��� �ʰ��ϸ� TimeoutException �߻���Ų��

## ����
��ü�� ���¸� ������ �ְ� �ڽ��� ���°� ������ ������ ó���� �����ϰ�
�������� ���� �� �������� �ʴ� ���� balking pattern�̴�.
if���� ���ؼ� ���¸� �����ϸ�, ������� ���ǿ� ���� �ʴ� ��� 
return�̳� �ش��ϴ� exception ó���� �Ѵ�.
