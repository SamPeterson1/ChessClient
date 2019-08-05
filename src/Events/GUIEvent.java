package Events;


import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public final class GUIEvent {
	public final static int EVENT_KEY_PRESS = 1;
	public final static int EVENT_KEY_RELEASE = 2;
	public final static int EVENT_MOUSE_BUTTON_PRESS = 3;
	public final static int EVENT_MOUSE_BUTTON_RELEASE = 4;
	public final static int EVENT_MOUSE_DRAG = 5;
	public final static int EVENT_MOUSE_MOVE = 6;
	private char keyChar;
	private int keyCode;
	private boolean mouseLeftButton;
	private boolean mouseMiddleButton;
	private boolean mouseRightButton;
	private int mouseX;
	private int mouseY;
	private int type;

	public GUIEvent(KeyEvent event, int type) {

		this.setType(type);
		this.setMouseX((int) 0x0);
		this.setMouseY((int) 0x0);
		this.setMouseLeftButton(false);
		this.setMouseMiddleButton(false);
		this.setMouseRightButton(false);
		this.setKeyChar(event.getKeyChar());
		this.setKeyCode(event.getKeyCode());

		return;
	}

	public GUIEvent(MouseEvent event, int type) {

		this.setType(type);
		this.setMouseX(event.getX());
		this.setMouseY(event.getY());
		if (event.getButton() == MouseEvent.BUTTON1) {
			this.setMouseLeftButton(true);
		} else {
			this.setMouseLeftButton(false);
		}
		if (event.getButton() == MouseEvent.BUTTON2) {
			this.setMouseMiddleButton(true);
		} else {
			this.setMouseMiddleButton(false);
		}
		if (event.getButton() == MouseEvent.BUTTON3) {
			this.setMouseRightButton(true);
		} else {
			this.setMouseRightButton(false);
		}
		this.setKeyChar((char) 0x0);
		this.setKeyCode((int) 0x0);

		return;
	}

	public char getKeyChar() {
		return this.keyChar;
	}

	public int getKeyCode() {
		return this.keyCode;
	}

	public int getMouseX() {
		return this.mouseX;
	}

	public int getMouseY() {
		return this.mouseY;
	}

	public int getType() {
		return type;
	}

	public boolean isMouseLeftButton() {
		return this.mouseLeftButton;
	}

	public boolean isMouseMiddleButton() {
		return this.mouseMiddleButton;
	}

	public boolean isMouseRightButton() {
		return this.mouseRightButton;
	}

	private void setKeyChar(char keyChar) {
		this.keyChar = keyChar;
		return;
	}

	private void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
		return;
	}

	private void setMouseLeftButton(boolean mouseLeftButton) {
		this.mouseLeftButton = mouseLeftButton;
		return;
	}

	private void setMouseMiddleButton(boolean mouseMiddleButton) {
		this.mouseMiddleButton = mouseMiddleButton;
		return;
	}

	private void setMouseRightButton(boolean mouseRightButton) {
		this.mouseRightButton = mouseRightButton;
		return;
	}

	private void setMouseX(int mouseX) {
		this.mouseX = mouseX;
		return;
	}

	private void setMouseY(int mouseY) {
		this.mouseY = mouseY;
		return;
	}

	private void setType(int type) {

		this.type = type;

		return;
	}

}
