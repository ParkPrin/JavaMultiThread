package me.dinosauruncle.chapter1;

public class CMain {

	public static void main(String[] args) {
		Bank bank = new Bank("계좌", 30000);
		new Thread(new Client(bank, "프린이")).start();
		new Thread(new Client(bank, "공쥬")).start();
		new Thread(new Client(bank, "RM")).start();

	}

}