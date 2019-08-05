package GUI;

import javax.swing.JFrame;

import Events.EventHandler;
import Events.GUIEventQueue;
import Game.ChessBoard;
import Net.Client;
import Net.Session;


public class Main {
	
	public static final int LOOP_SPEED_MS = 10;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1220;

	public static void main(String args[]) {

		GUIEventQueue queue = new GUIEventQueue();
		JFrame frame = new JFrame("Chess");
		GUICanvas canvas = new GUICanvas(WIDTH, HEIGHT);
		EventHandler eh = new EventHandler(queue, canvas);
		GUIFrame uiFrame = new GUIFrame(canvas, frame, eh);

		Client client = new Client();
		client.connect("localhost", 7777);	
		Session.init();
		Session.setClient(client);	
		
        uiFrame.setSize(Main.WIDTH, Main.HEIGHT);
        canvas.addEventQueue(queue);
		uiFrame.start(uiFrame.getWidth(), uiFrame.getHeight());        
        
	}
	
	public static void wait(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
