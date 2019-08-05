package Events;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import GUI.GUIComponent;
import Game.Move;

public class DragController {
	
	private static HashMap<Integer, DragNode> nodes;
	
	//NOTE: snap points relative to center of component
	
	public static void flush() {
		DragController.nodes = new HashMap<Integer, DragNode>();
	}
	
	public static void init(List<Move> moves) {
		for(Move move: moves) {
			DragNode node = new DragNode(move.getY2()*8+move.getX2(), move.getX2()*102+51 + 93, move.getY2()*102+51 + 93, 51);
			DragController.nodes.put(node.getId(), node);
		}
	}
	
	public static Collection<DragNode> getNodes() {
		return nodes.values();
	}
	
	public static int setPos(int[] data, GUIComponent c) {
		int[] retVal = new int[3];
		retVal[0] = c.getX() + data[0];
		retVal[1] = c.getY() + data[1];
		retVal[2] = -1;
		
		for(Integer id: DragController.nodes.keySet()) {
			DragNode node = DragController.nodes.get(id);
			if(data[2] == 1 && node.snap(data[3], data[4])) {
				retVal[2] = id;
				c.setCenterX(node.getCx());
				c.setCenterY(node.getCy());
				return id;
			}
		}
		
		if(data[2] == 1) {
			c.setX(data[5]);
			c.setY(data[6]);
			return -1;
		}
		
		c.setX(retVal[0]);
		c.setY(retVal[1]);
		
		return -1;
	}
	
}
