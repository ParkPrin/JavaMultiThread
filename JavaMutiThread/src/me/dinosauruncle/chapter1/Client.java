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
				// 짝수 영역
				// 입금함
				bank.deposit(randomMoney);
				System.out.println("입금자: " + clientName);
			} else {
				// 홀수 영역
				// 인출함
				bank.withdraw(randomMoney);
				System.out.println("인출자: " + clientName);
			}
			System.out.println(bank.getName() + "님 계좌의 현재잔고는 " + bank.getMoney() +  "원 입니다.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
