package Events;

import java.util.ArrayList;
import java.util.List;
import GUI.EventType;
import GUI.GUICanvas;
import GUI.GUIComponent;

public class SequenceDrag implements EventSequence {

	int state = 0;
	int mouseX=0, mouseY=0, deltaX=0, deltaY=0, initX=0, initY=0;
	ArrayList<Integer> affectedIDs = new ArrayList<Integer>();
	boolean dragging = false;
	boolean released = false;
	
	public SequenceDrag() {
		DragController.flush();
	}
	
	@Override
	public void update(GUIEvent e) {
		if(e.getType() == GUIEvent.EVENT_MOUSE_BUTTON_PRESS && e.isMouseLeftButton()) {
			for(GUIComponent c: GUICanvas.getComponents()) {
				int x = e.getMouseX();
				int y = e.getMouseY();
				if(x >= c.getX() && y >= c.getY() && x <= c.getX()+c.getWidth() && y <= c.getY()+c.getHeight()) {
					if(this.affectedIDs.size() > 0) this.affectedIDs = new ArrayList<Integer>();
					this.affectedIDs.add(c.getID());
					this.initX = c.getX();
					this.initY = c.getY();
					break;
				}
			}
			
		}
		if(e.getType() == GUIEvent.EVENT_MOUSE_DRAG) {
			this.state = 1;
			if(dragging) {
				this.deltaX = e.getMouseX() - this.mouseX;
				this.deltaY = e.getMouseY() - this.mouseY;
			}
			this.dragging = true;
			this.mouseX = e.getMouseX();
			this.mouseY = e.getMouseY();

		} else if(this.state == 1 && e.getType() == GUIEvent.EVENT_MOUSE_BUTTON_RELEASE && e.isMouseLeftButton()) {
			this.state = 2;
			this.released = true;
		}
	}

	@Override
	public void reset() {
		this.state = 0;
		this.dragging = false;
		this.affectedIDs = new ArrayList<Integer>();
		this.released = false;
	}

	@Override
	public int getState() {return this.state;}

	@Override
	public int getPriority() {return 0;}

	@Override
	public boolean isActive() {return this.state > 0;}

	@Override
	public EventType getType() {return EventType.DRAG;}

	@Override
	public int[] getData() {
		int[] retVal = {deltaX, deltaY, released ? 1: 0, mouseX, mouseY, initX, initY};
		return retVal;
	}

	@Override
	public boolean isFinished() {return this.state == 2;}

	@Override
	public List<Integer> getAffectedIDs() {
		if(this.dragging) return this.affectedIDs;
		else return new ArrayList<Integer>();
	}
}
