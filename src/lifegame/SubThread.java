package lifegame;

public class SubThread extends Thread {
	private BoardModel model;
	public SubThread(BoardModel model) {
		this.model = model;
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
				model.next();
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
