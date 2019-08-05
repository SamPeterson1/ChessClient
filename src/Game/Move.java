package Game;

import Net.Request;

public class Move {
	
	int x1, y1, x2, y2;
	
	public Move(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public static void makeMove(Request move, ChessBoard board) {
		int x1 = move.getInt("x1");
		int x2 = move.getInt("x2");
		int y1 = move.getInt("y1");
		int y2 = move.getInt("y2");
		
		board.movePiece(x1, y1, x2, y2, true);
	}
	
	public String toString() {
		Request move = new Request("move");
		move.addParameter("x1", x1);
		move.addParameter("x2", x2);
		move.addParameter("y1", y1);
		move.addParameter("y2", y2);
		
		return move.toString();
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}
	
}
