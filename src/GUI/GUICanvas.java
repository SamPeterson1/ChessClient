package GUI;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.ImageIcon;

import Events.DragController;
import Events.DragNode;
import Events.GUIEventQueue;

public class GUICanvas extends Canvas {
	//TODO: generate new long for serialVersionUID
	private static final long serialVersionUID = -557652432650828632L;
	private int width;
	private int height;
	private Image board = new ImageIcon(getClass().getResource("/Assets/ChessBoard.png")).getImage();
	private Image target = new ImageIcon(getClass().getResource("/Assets/Target.png")).getImage();
	private static HashMap<Integer, GUIComponent> components = new HashMap<Integer, GUIComponent>();	
	private static int idCout = 0;
	
	public GUICanvas(int width, int height) {
		this.setBackground(Color.WHITE);
		this.setSize(width, height-20);
		this.width = width;
		this.height = height;
	}
	
	public static Collection<GUIComponent> getComponents() {
		return GUICanvas.components.values();
	}
	
	public static void addComponent(GUIComponent c) {
		GUICanvas.idCout ++;
		c.setID(GUICanvas.idCout);
		GUICanvas.components.put(GUICanvas.idCout, c);
	}
	
	public void draw(Graphics g2) {
		BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		this.clear(g2);	
		
		g.drawImage(board, 0, 0, null);
		
		for(GUIComponent c: components.values()) {
			g.drawImage(c.getImage(), c.getX(), c.getY(), null);
		}
		
		g.setColor(Color.blue);
		for(DragNode n: DragController.getNodes()) {
			g.drawImage(target, n.getCx()-25, n.getCy()-25, null);
		}
		
		g.dispose();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
	}

	public void clear(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.width, this.height);
	}
	
	public BufferedImage scaleToFrameSize(BufferedImage img) {
		
		int[] targetRes = new int[2];
		targetRes[0] = this.getWidth();
		targetRes[1] = this.getHeight();
		
		return scale(targetRes, img);
		
	}
	
	public BufferedImage scale(int[] targetRes, BufferedImage img) {
		if(targetRes[1] != Main.WIDTH && targetRes[0] != Main.HEIGHT - 20) {
			Image image = img.getScaledInstance(targetRes[0], targetRes[1], Image.SCALE_SMOOTH);
			BufferedImage resizedImage = new BufferedImage(targetRes[0], targetRes[1], BufferedImage.TYPE_INT_ARGB); 
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(image, 0, 0, targetRes[0], targetRes[1], null);
			g.dispose();
		    return resizedImage;
		}
		return img;
	}
	
	public void addEventQueue(GUIEventQueue queue)  {

		this.addKeyListener(queue);
		this.addMouseListener(queue);
		this.addMouseMotionListener(queue);

		return;
	}

}
