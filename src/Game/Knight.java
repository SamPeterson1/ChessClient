package Game;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import Events.DragController;
import Events.EventHandler;
import GUI.EventType;
import GUI.GUICanvas;
import Net.Session;

public class Knight implements ChessPiece {

	boolean showMoves = false;
	boolean firstMove = true;
	Image imgWhite = new ImageIcon(getClass().getResource("/Assets/KnightBlack.png")).getImage();
	Image imgBlack = new ImageIcon(getClass().getResource("/Assets/KnightWhite.png")).getImage();
	int x = 0;
	int y = 0;
	int gridX = 0;
	int gridY = 0;
	int xOff = 25;
	int yOff = 16;
	int width = imgWhite.getWidth(null);
	int height = imgWhite.getHeight(null);
	int id = 0;
	int side;
	boolean captured = false;
	ChessBoard b;
	
	public Knight(ChessBoard b, int x, int y, int side) {
		this.b = b;
		this.x = x*102 + 93 + xOff;
		this.y = y*102 + 93 + yOff;
		this.gridX = x;
		this.gridY = y;
		this.side = side;
		GUICanvas.addComponent(this);
		EventHandler.addListener(this);
	}
	
	@Override
	public void onEvent(EventType type, int[] data) {
		if(type == EventType.LEFT) {
			this.showMoves = true;
			this.getLegalMoves(b);
		} else if(type == EventType.DRAG) {
			int id = DragController.setPos(data, this);
			if(id != -1) {
				DragController.flush();
				this.b.movePiece((data[5]-93)/102, (data[6]-93)/102, id%8, id/8, true);
				this.firstMove = false;
			}
		}
	}
	
	
	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.x = gridX*102+93+xOff;
		this.gridX = gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.y = gridY*102+93+yOff;
		this.gridY = gridY;
	}

	@Override
	public Image getImage() {
		if(this.side == -1) {
			return imgBlack;
		}
		return imgWhite;
	}

	@Override
	public int getX() {return this.x;}

	@Override
	public int getY() {return this.y;}

	@Override
	public void setX(int x) {this.x = x;}

	@Override
	public void setY(int y) {this.y = y;}

	@Override
	public void setCenterX(int cx) {this.x = cx+xOff;}

	@Override
	public void setCenterY(int cy) {this.y = cy+yOff;}

	@Override
	public int getWidth() {return this.width;}

	@Override
	public int getHeight() {return this.height;}

	@Override
	public void setID(int id) {this.id = id;}

	@Override
	public boolean cameraStatic() {return false;}

	@Override
	public int getID() {return this.id;}
	
	@Override
	public List<Move> getValidMoves(ChessBoard b) {
		ArrayList<Move> moves = new ArrayList<Move>();
		moves.add(new Move(gridX, gridY, gridX+2, gridY-1));
		moves.add(new Move(gridX, gridY, gridX+1, gridY-2));
		moves.add(new Move(gridX, gridY, gridX-2, gridY-1));
		moves.add(new Move(gridX, gridY, gridX-1, gridY-2));
		moves.add(new Move(gridX, gridY, gridX+2, gridY+1));
		moves.add(new Move(gridX, gridY, gridX+1, gridY+2));
		moves.add(new Move(gridX, gridY, gridX-2, gridY+1));
		moves.add(new Move(gridX, gridY, gridX-1, gridY+2));
		
		moves = b.checkMoves(moves,this.side);
		
		return moves;
	}

	@Override
	public void executeMove(int x, int y, boolean sim) {this.setGridX(x); this.setGridY(y); if(!sim) this.firstMove = false;}

	@Override
	public int getSide() {return this.side;}

	@Override
	public void capture() {
		this.x = ChessBoard.capX;
		this.y = 1000;
		this.captured = true;
	}
	
	@Override
	public boolean acceptEvents() {
		return !this.captured && this.side == Session.getSide() && ChessBoard.thisTurn;
	}

	@Override
	public synchronized List<Move> getLegalMoves(ChessBoard b) {
		ArrayList<Move> moves = b.checkForCheck((ArrayList<Move>)this.getValidMoves(b));
		
		DragController.flush();
		DragController.init(moves);
		
		return moves;	
	}
}
