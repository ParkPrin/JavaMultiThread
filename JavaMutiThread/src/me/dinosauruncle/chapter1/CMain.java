package me.dinosauruncle.chapter1;

public class CMain {

	public static void main(String[] args) {
		Bank bank = new Bank("������", 30000);
		new Thread(new Client(bank, "������")).start();
		new Thread(new Client(bank, "����")).start();
		new Thread(new Client(bank, "RM")).start();

	}

}
