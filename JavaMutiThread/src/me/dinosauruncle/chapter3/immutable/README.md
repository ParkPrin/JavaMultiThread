# Immutable ����

## Immutable �����̶�?

Immutable�� [������], [���ϴ� ���� ����] �̶�� ���� ����<br>
Immutable ���Ͽ��� �ν��Ͻ��� ���°� ���� ������ �ʴ� Ŭ������ �����Ѵ�.<br>
�� �ν��Ͻ��� �׼������� �ð��� �ɸ��� ��Ÿ��� ���ʿ��ϱ� ������ �� ����ϸ� ����ɷ��� ���� �� �ִ�<br>

## ����

<table>
	<tr>
		<td>�̸�</td>
		<td>�ؼ�</td>
	</tr>
	<tr>
		<td>Person</td>
		<td>����� ��Ÿ���� Ŭ����</td>
	</tr>
	<tr>
		<td>Ch3AMain</td>
		<td>���� �׽�Ʈ�� Ŭ����</td>
	</tr>
	<tr>
		<td>PrintPersonThread</td>
		<td>Person �ν��Ͻ��� ǥ���ϴ� thread�� ��Ÿ���� Ŭ����</td>
	</tr>
</table>

### ���� �ڵ�

Person.java

```
public final class Person {
	private final String name;
	private final String address;
	public Person(String name, String address) {
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String toString() {
		return "[ Person: name = " + name + ", address = " + address + " ]";
	}

}
```

Ch3AMain.java

```
	public static void main(String[] args) {
		// �Һ��� ��ü�� �����ڿ����� �����
		Person alice = new Person("Alice", "Alaska");
		
		new PrintPersonThread(alice).start();
		new PrintPersonThread(alice).start();
		new PrintPersonThread(alice).start();

	}
```

PrintPersonThread.java

```
public class PrintPersonThread extends Thread {
	private Person person;
	// ��ü ��ü�� final�� ���ǵ� ���� ��ü�̱� ������ synchronized�� ���� �ʾƵ� ������ ������ �߻����� �ʴ´�
	// synchronized�� ���ʿ���
	public PrintPersonThread(Person person) {
		this.person = person;
	}
	
	public void run() {
		while (true) {
			// Thread.currentThread().getName�� �ڱ� thread�� �̸��� ���� �� ����Ѵ�
			// Thread.currentThread()�� ������ thread ���ϴ� �޼ҵ��̴�.
			System.out.println(Thread.currentThread().getName() + "prints" + person);
		}
	}
}
```

## single threaded execution�� immutable ���ϰ��� ��

single threaded execution�� immutable �� ���ϰ��� ������ thread�� �����ڿ��� ����ϴ� ���̴�
�׷��� ���� ū �������� single threaded execution�� �����ڿ��� ������ thread�� ����� ������
�����ڿ���ü ������ ������ �Ͼ����, immutable�� �����ڿ���ü�� ������ ���ٴ� ���̴�.
�׷��� �����ڿ���ü�� ���� ������ �Ͼ �� ������ ������ ���� ������ ���Ἲ�� ��������, ��ü ������ �Ұ��ϴٸ�
�ƹ��� ���� ���� thread�� �Һ��� ��ü�� �����ڿ����� ����ϴ��� ������ ������ �߻����� �����Ƿ�
��Ÿ���� ó���� ���� �ʾƵ� �ȴ�. �̷ν� �������� �������� �Ҿ������ �ʰ� ���� �ɷ��� ���� �� �ִ�.


## � �� ����ұ�? [���� ���ɼ�]

1. �ν��Ͻ� ���� �� ���°� ������ ���� ��
2. �ν��Ͻ��� �����Ǿ� ����ϰ� �׼��� �� ��

## ǥ�� Ŭ���� ���̺귯������ ���Ǵ� Immutable ����

1. ���ڿ��� ��Ÿ���� java.lnag.String Ŭ����
2. ū ���� ��Ÿ���� java.math.BigInteger Ŭ����
3. ���� ǥ���� ������ ��Ÿ���� java.util.regex.Pattern Ŭ����
4. ���� ǥ���ϴ� java.awt.Color
5. java.lang.Integer

<table>
	<tr>
		<td>�⺻��</td>
		<td>���� Ŭ����</td>
	</tr>
	<tr>
		<td>boolean</td>
		<td>java.lang.Boolean</td>
	</tr>
	<tr>
		<td>char</td>
		<td>java.lang.Char</td>
	</tr>
	<tr>
		<td>float</td>
		<td>java.lang.Float</td>
	</tr>
	<tr>
		<td>long</td>
		<td>java.lang.Long</td>
	</tr>
	<tr>
		<td>void</td>
		<td>java.lang.Void</td>
	</tr>
	<tr>
		<td>byte</td>
		<td>java.lang.Byte</td>
	</tr>
	<tr>
		<td>double</td>
		<td>java.lang.Double</td>
	</tr>
	<tr>
		<td>Int</td>
		<td>java.lang.Integer</td>
	</tr>
	<tr>
		<td>short</td>
		<td>java.lang.Short</td>
	</tr>
</table>


## final

### final�� �ǹ�

#### final Ŭ����
Ŭ������ final�� ����� ��� �� Ŭ������ Ȯ���� �� ���� �ᱹ final�� Ŭ������ ���� Ŭ������ ���� �� ����.

#### final �޼ҵ�
�ν��Ͻ� �޼ҵ忡 final�� ����� ��� �� �޼ҵ�� ���� Ŭ������ �޼ҵ�� �������̵� �� �� ����.

#### final �ʵ�
final�� �ʵ忡�� �� �� �ۿ� ������ �� ����.
���Կ��� �ΰ��� ����� �ִ�

```
// ����� ���ÿ� �����ϴ� ���
final int value = 123;
```

```
class Something {
	// ������ �ϰ� ������ �����ڿ��� �ϴ� ���
	final int value;
	public Something(int value){
		this.value = value;
	}
}


```

## �÷��� Ŭ������ multi thread
�÷����̶� ������ �ν��Ͻ��� �����ϴ� �������̽��� Ŭ������ ���� ��Ī
java�� �÷����� ��κ� thread safe�� �ƴϴ�.
=> multi thread ����ϰ��� �ϴ� Ŭ������ �������̽��� thread safe���� Ȯ���ϴ� ���� �߿�

## Collections.synchronizedList �޼ҵ忡 ���� ����ȭ

Collections.synchronizedList �޼ҵ带 ����Ͽ� ����ȭ�ϸ� ������ �������� �ν��Ͻ��� Ȯ���� �� �ִ�

### Collections.synchronizedList ����

Ch3BMain.java

```
public class Ch3BMain {
	public static void main(String[] args) {
		final List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
		new WriterThread(list).start();
		new ReaderThread(list).start();
	}
}
```

WriterThread.java

```
public class WriterThread extends Thread {
	private final List<Integer> list;
	public WriterThread(List<Integer> list) {
		super("WriterThread");
		this.list = list;
	}
	
	@Override
	public void run() {
		for (int i = 0; true; i++) {
			list.add(i);
			list.remove(0);
		}
	}
}

```

ReaderThread.java

```
public class ReaderThread extends Thread {
	private final List<Integer> list;
	public ReaderThread(List<Integer> list) {
		super("ReaderThread");
		this.list = list;
	}
	
	@Override
	public void run() {
		while (true) {
			synchronized (list) {
				for (int n : list) {
					System.out.println(n);
				}
			}
		}
	}
}
```

## ī�� �� ����Ʈ�� ����� java.util.concurrent.CopyOnWriteArrayList Ŭ����

Collections.synchronizedList �޼ҵ带 ����Ͽ� ����ȭ��Ű�� �Ͱ� �޸�
ī�� �� ����Ʈ(copy-on-write)��� �ϴ� �ý����� �̿��� �а� ������ �浹�� �����Ѵ�

ī�� �� ����Ʈ�� [���� ���� �����Ѵ�]�� �ǹ��̸� ī�� �� ����Ʈ������ �÷��ǿ� ���Ͽ�
���� ������ �ϸ� ���ο� Ȯ���� �迭�� ��°�� �����Ѵ�. ���縦 �ϰ� �Ǹ� ���ͷ����͸�
����Ͽ� ��Ҹ� ������� �о�� ���߿� ��Ұ� ����� ������ ����.

### ����



