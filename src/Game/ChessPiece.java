package Game;

import java.util.List;

import GUI.EventListener;

public interface ChessPiece extends EventListener {
	
	int getID();
	List<Move> getValidMoves(ChessBoard b);
	List<Move> getLegalMoves(ChessBoard b);
	
	void executeMove(int x, int y, boolean sim);
	void setGridY(int y);
	void setGridX(int x);
	int getGridX();
	int getGridY();
	int getSide();
	void capture();
	
}
