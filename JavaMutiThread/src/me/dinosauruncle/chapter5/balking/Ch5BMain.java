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