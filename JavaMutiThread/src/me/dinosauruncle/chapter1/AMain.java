package me.dinosauruncle.chapter1;

public class AMain {

	/**
	 * 1) Thread Ŭ������ ��ӹ޾Ƽ� ������ Ŭ���� ����
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * MyThread myThread = new MyThread(); myThread.start(); for (int i=0; i <10000;
		 * i++) { System.out.println("Good!"); }
		 */
		
		
		// Thread Ŭ������ ��ӹ޾Ƽ� ������ Ŭ���� ����
//		new MyThread("Good!").start();
//		new MyThread("Nice!").start();
		
		// Runnable �������̽��� �޾Ƽ� ����ü�� ���� �� Thread ��ü�� ������ ���ڰ����� �������̽� ����ü�� ������
		new Thread(new Printer("Good!")).start();
		new Thread(new Printer("Nice!")).start();
		
	}

}
