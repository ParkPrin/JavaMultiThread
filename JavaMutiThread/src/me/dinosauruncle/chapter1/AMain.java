package me.dinosauruncle.chapter1;

public class AMain {

	/**
	 * 1) Thread 클래스를 상속받아서 스레드 클래스 생성
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * MyThread myThread = new MyThread(); myThread.start(); for (int i=0; i <10000;
		 * i++) { System.out.println("Good!"); }
		 */
		
		
		// Thread 클래스를 상속받아서 스레드 클래스 생성
//		new MyThread("Good!").start();
//		new MyThread("Nice!").start();
		
		// Runnable 인터페이스를 받아서 구현체로 구현 후 Thread 객체를 생성시 인자값으로 인터페이스 구현체를 주입함
		new Thread(new Printer("Good!")).start();
		new Thread(new Printer("Nice!")).start();
		
	}

}
