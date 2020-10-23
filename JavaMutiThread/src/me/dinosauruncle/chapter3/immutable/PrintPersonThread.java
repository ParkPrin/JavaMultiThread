package me.dinosauruncle.chapter3.immutable;

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
