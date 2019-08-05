package Events;

import java.util.ArrayList;
import java.util.List;
import GUI.EventType;
import GUI.GUICanvas;
import GUI.GUIComponent;

public class SequenceLeftClick implements EventSequence {

	int state = 0;
	boolean active = false;
	int clickX = 0;
	int clickY = 0;
	ArrayList<Integer> affectedIDs = new ArrayList<Integer>();
	
	@Override
	public void update(GUIEvent e) {
		if(e.getType() == GUIEvent.EVENT_MOUSE_BUTTON_PRESS && e.isMouseLeftButton()) {
			this.state = 1;
			this.active = true;
			this.clickX = e.getMouseX();
			this.clickY = e.getMouseY();
			for(GUIComponent component: GUICanvas.getComponents()) {
				if(this.clickX >= component.getX() && this.clickY >= component.getY() && this.clickX <= component.getX() + component.getWidth() && this.clickY <= component.getY() + component.getHeight()) {
					this.affectedIDs.add(component.getID());
				}
			}
		}
	}

	@Override
	public void reset() {
		this.state = 0;
		this.active = false;
		this.affectedIDs = new ArrayList<Integer>();
	}
	@Override
	public int getState() {return this.state;}
	@Override
	public int getPriority() {return 0;}
	@Override
	public boolean isActive() {return this.active;}
	@Override
	public EventType getType() {return EventType.LEFT;}
	@Override
	public int[] getData() {
		int[] retVal = {this.clickX, this.clickY};
		return retVal;
	}

	@Override
	public boolean isFinished() {return this.active;}

	@Override
	public List<Integer> getAffectedIDs() {return this.affectedIDs;}
}
