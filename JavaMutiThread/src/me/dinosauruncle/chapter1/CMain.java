package me.dinosauruncle.chapter1;

public class CMain {

	public static void main(String[] args) {
		Bank bank = new Bank("¹ÚÁ¾ÈÆ", 30000);
		new Thread(new Client(bank, "ÇÁ¸°ÀÌ")).start();
		new Thread(new Client(bank, "°øÁê")).start();
		new Thread(new Client(bank, "RM")).start();

	}

}
