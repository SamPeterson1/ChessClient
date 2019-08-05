package GUI;

import java.awt.Image;

public interface GUIComponent {
	
	Image getImage();
	int getX();
	int getY();
	void setX(int x);
	void setY(int y);
	void setCenterX(int cx);
	void setCenterY(int cy);
	int getWidth();
	int getHeight();
	int getID();
	void setID(int id);
	
	boolean cameraStatic();
}
