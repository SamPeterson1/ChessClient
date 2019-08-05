package Events;

public class DragNode {
	
	private int id;
	private int cx;
	private int cy;
	private int tolerance;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCx() {
		return cx;
	}

	public void setCx(int cx) {
		this.cx = cx;
	}

	public int getCy() {
		return cy;
	}

	public void setCy(int cy) {
		this.cy = cy;
	}

	public int getTolerance() {
		return tolerance;
	}

	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
	}

	public DragNode(Request cfgLine) {
		this.id = cfgLine.getInt("id");
		this.cx = cfgLine.getInt("cx");
		this.cy = cfgLine.getInt("cy");
		this.tolerance = cfgLine.getInt("tolerance");
	}
	
	public DragNode(int id, int cx, int cy, int tolerance) {
		this.id = id;
		this.cx = cx;
		this.cy = cy;
		this.tolerance = tolerance;
	}
	
	public boolean snap(int cx, int cy) {
		float dx = Math.abs(cx-this.cx);
		float dy = Math.abs(cy-this.cy);
		if(dx < this.tolerance && dy <= this.tolerance) {
			return Math.sqrt(dx*dx+dy*dy) <= this.tolerance;
		}
		
		return false;
	}
	
}
