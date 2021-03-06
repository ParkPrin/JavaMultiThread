package me.dinosauruncle.chapter8.threadpermessage;

public class Host {
	private final Helper helper = new Helper();
	public void request(final int count, final char c) {
		System.out.println("      request(" + count + ", " + c + ") BEGIN");
		/*
		 * 1) 익명의 클래스를 직접 선언하는 방법
		new Thread() {
			public void run() {
				helper.handle(count, c);
			}
		}.start();
		*/
		
		// 2) Runnable 인터페이스를 먼저 선언하고 run에 대한 메소드를 구현한 후
		//	-> 익명의 쓰레드를 생성 후 runnable 구현체를 주입하는 방법 
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				helper.handle(count, c);
				
			}
		};
		new Thread(runnable).start();
		System.out.println("      request(" + count + ", " + c + ") END");
	}
}
