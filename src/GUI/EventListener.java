package GUI;

public interface EventListener extends GUIComponent{
	
	void onEvent(EventType type, int[] data);
	boolean acceptEvents();
	
}
