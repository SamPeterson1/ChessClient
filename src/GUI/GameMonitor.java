package GUI;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import Game.GameState;

public class GameMonitor implements GUIComponent {
	
	private long messageTime = -1;
	private int lifeSpanMs = 5000;
	private GameState lastState;
	private Font font;
	private int id;
	
	public GameMonitor() {
		GUICanvas.addComponent(this);
		this.font = new Font("Helvetica", Font.BOLD, 20);
	}
	
	public void display(GameState state) {
		this.messageTime = System.currentTimeMillis();
		this.lastState = state;
	}
	
	@Override
	public Image getImage() {
		BufferedImage img = new BufferedImage(1000, 100, BufferedImage.TYPE_INT_ARGB);
		if(this.messageTime != -1 && this.update()) {
			Graphics2D g = img
					.createGraphics();
			g.setRenderingHint(
			        RenderingHints.KEY_TEXT_ANTIALIASING,
			        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			FontMetrics fm = g.getFontMetrics(font);
			int width = fm.stringWidth(this.lastState.getName());
			int height = fm.getHeight();
			int drawX = (1000-width)/2;
			int drawY = (100-height)/2;
			
			g.drawString(this.lastState.getName(), drawX, drawY);
			g.dispose();

		}
		
		return img;
	}
	
	private boolean update() {
		if(System.currentTimeMillis() - this.messageTime >= this.lifeSpanMs) {
			return false;
		}
		return true;
	}

	@Override
	public int getX() {return 0;}

	@Override
	public int getY() {return 550;}

	@Override
	public void setX(int x) {}

	@Override
	public void setY(int y) {}

	@Override
	public void setCenterX(int cx) {}

	@Override
	public void setCenterY(int cy) {}

	@Override
	public int getWidth() {return 1000;}

	@Override
	public int getHeight() {return 100;}

	@Override
	public int getID() {return this.id;}

	@Override
	public void setID(int id) {this.id = id;}

	@Override
	public boolean cameraStatic() {return true;}

}
