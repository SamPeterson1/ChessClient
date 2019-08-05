package Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import GUI.EventListener;
import GUI.EventType;
import GUI.GUICanvas;

public class EventHandler {
	
	GUIEventQueue queue;
	GUICanvas canvas;
	private static ArrayList<EventListener> listeners = new ArrayList<EventListener>();
	private static HashMap<EventType, EventSequence> sequences = new HashMap<EventType, EventSequence>();
	
	public EventHandler(GUIEventQueue queue, GUICanvas canvas) {
		this.queue = queue;
		this.canvas = canvas;
		EventHandler.sequences.put(EventType.LEFT, new SequenceLeftClick());
		EventHandler.sequences.put(EventType.DRAG, new SequenceDrag());
	}
	
	public static void addListener(EventListener el) {
		EventHandler.listeners.add(el);
	}
	
	public static void addSequence(EventSequence e) {
		EventHandler.sequences.put(e.getType(), e);
	}
	
	public String handleEvents(String id) {

		HashMap<EventType, List<Integer>> happened = new HashMap<EventType, List<Integer>>();

		if(queue.isEventToProcess()) {
			GUIEvent e = queue.getEvent();
			for(EventType type: EventHandler.sequences.keySet()) {
				EventSequence sequence = EventHandler.sequences.get(type);
				sequence.update(e);
				if(sequence.isActive()) {
					happened.put(type, sequence.getAffectedIDs());
				}
			}
			
			for(EventType type: happened.keySet()) {
				List<Integer> affectedIDs = happened.get(type);
				EventSequence sequence = EventHandler.sequences.get(type);
				for(EventListener listener: EventHandler.listeners) {
					if(listener.acceptEvents()) {
						if(affectedIDs.contains(listener.getID())) {
							listener.onEvent(type, sequence.getData());
						}
					}
				}
				if(sequence.isFinished()) {
					sequence.reset();
				}
			}
		}
		
		return "";
	}
	
}
