package Events;

import java.util.List;

import GUI.EventType;

public interface EventSequence {
	
	void update(GUIEvent e);
	void reset();
	
	int getState();
	int getPriority();
	boolean isActive();
	boolean isFinished();
	
	EventType getType();
	int[] getData();
	List<Integer> getAffectedIDs();
}
