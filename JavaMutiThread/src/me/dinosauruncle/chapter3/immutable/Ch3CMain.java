package me.dinosauruncle.chapter3.immutable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



public class Ch3CMain {
	public static void main(String[] args) {
		final List<Integer> list =new CopyOnWriteArrayList<Integer>();
		new WriterThread(list).start();
		new ReaderThread(list).start();
	}
}