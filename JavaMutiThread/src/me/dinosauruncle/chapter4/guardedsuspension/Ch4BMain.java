package me.dinosauruncle.chapter4.guardedsuspension;

public class Ch4BMain {
	public static void main(String[] args) {
		LinkedBlockingQueueUsedRequestQueue requestQueue = new LinkedBlockingQueueUsedRequestQueue();
		new ClientThread2(requestQueue, "Alice", 3141582L).start();
		new ServerThread2(requestQueue, "Bobby", 6535897L).start();
	}

}
