# Java Thread�� �⺻���

## �������?
���α׷��� �����ϰ� �մ� ��ü

## Thread ����

������ ��ü�� �����ϴ� ����� �� ������ ����
###  Thread Ŭ������ ��ӹ޾Ƽ� ������ Ŭ���� ����
```
package me.dinosauruncle.chapter1;

public class AMain {
	public static void main(String[] args) {	
		new MyThread("Good!").start();
		new MyThread("Nice!").start();
	}

}


```

```
package me.dinosauruncle.chapter1;

public class MyThread extends Thread {
	private String message;
	
	public MyThread(String message) {
		this.message = message;
	}
	
	public void run() {
		for (int i =0; i<10000; i++) {
			System.out.println(message);
		}
	}
}
```

### Runnable �������̽��� �޾Ƽ� ����ü�� ���� �� Thread ��ü�� ������ ���ڰ����� �������̽� ����ü�� ������
```
package me.dinosauruncle.chapter1;

public class AMain {
	public static void main(String[] args) {
		new Thread(new Printer("Good!")).start();
		new Thread(new Printer("Nice!")).start();
	}

}
```

```
package me.dinosauruncle.chapter1;
public class Printer implements Runnable{
	private String message;
	
	public Printer(String message) {
		this.message = message;
	}

	@Override
	public void run() {
		for(int i= 0; i < 10000; i++) {
			System.out.println(message);
		}
	}

}
```

## Thread�� �Ͻ� ����

�⺻����
```
Thread.sleep()
sleep�� ù��° ���� �и���, �ι�° ���� ������
```

�ڵ忹��

```
package me.dinosauruncle.chapter1;

public class BMain {

	public static void main(String[] args) {
		for(int i=0; i < 10000; i++) {
			System.out.println("Good!");
			try {				
				Thread.sleep(1000); // �ش� �ڵ�� ���� 1�ʿ� �ѹ��� �ֿܼ� �޼��� ��µ� 
			} catch (InterruptedException e) {
			}
		}

	}

}
```

## Thread�� ��Ÿ����

### data race(������ ���̽�)
������ thread�� ���� ����� �� thread �۾��� �ٸ� thread�� �۾� �������� ���� ���ʿ��� �����ͺ��濡 ���� ����
=> �̸� �����ϱ� ���� ���������� ��Ÿ���� Ȥ�� ��ȣ��Ÿ(mutual exclusion)��� ��
==> Java������ Thread ��Ÿ��� ���� �� �� synchronized ��� Ű���带 �����


### synchronized �޼ҵ�(���� �޼ҵ�)
���ۿ���: �ش�Ű���带 �ٿ��� �����ϸ� �� �޼ҵ�� �ϳ��� thread�� ������
=> �ϳ��� thread�� �����Ѵٰ� �ؼ� � Ư���� thread �ܿ��� ������ �� ���ٴ� �ǹ̰� �ƴ϶� �ѹ��� �Ѱ��� ������

### synchronized ���

���� ���ϰ� �ν��ͽ��� �����


```
synchronized(��){
	...
}
```

## Thread�� ���º�ȯ
Thread ���º�ȯ ��ɾ�: wait, notify, notifyAll
wait�� �����·� ����,
notify�� �ڿ� ����ڰ� ������ �˸��� �����¿��� ������·� ��ȯ
notifyAll�� ��� �����¿� �ִ� Thread���� �ڿ��� ����� �����ϴٴ� ���� �˸� 

�����¿� �ִ� ��� Thread�� ������ wait set �̶����

### Wait �޼ҵ�
wait �޼ҵ�� ���۰����� Thread�� �����·� ����� => wait set�� Thread�� ����

### notify �޼ҵ�
wait set�� �ִ� �����¿� �ִ� Thread�� ������ ���� �����ϵ��� �����.


# ��Ƽ Thread ���α׷��� �򰡱���
1) ������: ��ü�� �����߸��� ���� ��
2) ������: �ʿ��� ó���� �̷��� �� <-> ������� �Ͼ�� ��
3) ���뼺: Ŭ������ �ٽ� ����� �� ���� ��
4) ����ɷ�: ��ӡ��뷮���� ó���� �� ���� ��, �ð��� ª������(���伺�� ����)

