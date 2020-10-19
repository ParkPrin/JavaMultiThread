package me.dinosauruncle.chapter1;

import java.util.Random;

public class Client implements Runnable {
	private String clientName;
	private Bank bank;
	private Random random;
	private int initMoney;
	
	public Client(Bank bank, String clientName) {
		this.bank = bank;
		this.clientName = clientName;
		this.initMoney = bank.getMoney();
		random = new Random();
	}

	@Override
	public void run() {
		for(int i=0; i< 10000; i++ ) {
			int randomMoney = random.nextInt(initMoney);
			if (randomMoney %2 == 0) {
				// ¦�� ����
				// �Ա���
				bank.deposit(randomMoney);
				System.out.println("�Ա���: " + clientName);
			} else {
				// Ȧ�� ����
				// ������
				bank.withdraw(randomMoney);
				System.out.println("������: " + clientName);
			}
			System.out.println(bank.getName() + "�� ������ �����ܰ�� " + bank.getMoney() +  "�� �Դϴ�.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
