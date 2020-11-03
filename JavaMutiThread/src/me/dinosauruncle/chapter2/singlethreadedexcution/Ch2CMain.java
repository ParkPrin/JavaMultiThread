package me.dinosauruncle.chapter2.singlethreadedexcution;

public class Ch2CMain {
	public static void main(String[] args) {
		System.out.println("Testing EaterThread, hit CTRL+C to exit.");
		Tool spoon = new Tool("Spoon");
		Tool fork = new Tool("Fork");
		new EaterThread("Alice", spoon, fork).start();
		new EaterThread("Bobby", fork, spoon).start();
	}
}