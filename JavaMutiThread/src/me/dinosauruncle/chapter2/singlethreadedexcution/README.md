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

 
 ###