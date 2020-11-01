package me.dinosauruncle.chapter5.balking;

public class Ch5AMain {

	public static void main(String[] args) {
		Data data = new Data("data.txt", "(empty)");
		new ChangerThread("ChangerThread", data).start();
		new SaverThread("SaverThread", data).start();

	}

}
