package me.dinosauruncle.chapter4.guardedsuspension;

public class Ch4AMain {
	public static void main(String[] args) {
		RequestQueue requestQueue = new RequestQueue();
		new ClientThread(requestQueue, "Alice", 3141582L).start();
		new ServerThread(requestQueue, "Bobby", 6535897L).start();
	}

}
