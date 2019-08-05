package Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import Events.DragController;
import GUI.GameMonitor;
import Net.Request;
import Net.Session;

public class ChessBoard {
	
	private HashMap<Integer, ChessPiece> pieces;
	private ChessPiece hold;
	private boolean holding = false;
	public static int capX = 0;
	public static boolean thisTurn;
	private static int side;
	private GameState state;
	private GameMonitor monitor;
	
	public ChessBoard() {
		this.monitor = new GameMonitor();
		this.state = GameState.NORMAL;
		this.pieces = new HashMap<Integer, ChessPiece>();
		this.pieces.put(0, new Rook(this, 0, 0, -1));
		this.pieces.put(7, new Rook(this, 7, 0, -1));
		this.pieces.put(1, new Knight(this, 1, 0, -1));
		this.pieces.put(6, new Knight(this, 6, 0, -1));
		this.pieces.put(2, new Bishop(this, 2, 0, -1));
		this.pieces.put(5, new Bishop(this, 5, 0, -1));
		this.pieces.put(3, new Queen(this, 3,0, -1));
		this.pieces.put(4, new King(this, 4, 0, -1));
		for(int i = 8; i < 16; i ++) {
			this.pieces.put(i, new Pawn(this, i-8, 1, -1));
		}
		
		this.pieces.put(56, new Rook(this, 0, 7, 1));
		this.pieces.put(63, new Rook(this, 7, 7, 1));
		this.pieces.put(57, new Knight(this, 1, 7, 1));
		this.pieces.put(62, new Knight(this, 6, 7, 1));
		this.pieces.put(58, new Bishop(this, 2, 7, 1));
		this.pieces.put(61, new Bishop(this, 5, 7, 1));
		this.pieces.put(59, new Queen(this, 3, 7, 1));
		this.pieces.put(60, new King(this, 4, 7, 1));
		for(int i = 48; i < 56; i ++) {
			this.pieces.put(i, new Pawn(this, i-48, 6, 1));
		}

	}
	
	public GameState getState() {
		return this.state;
	}
	
	public void updateState() {
		boolean movesRemaining = this.movesRemaining();
		if(this.kingsInCheck()) {
			if(movesRemaining) {
				this.state = GameState.CHECK;
			} else {
				this.state = GameState.CHECKMATE;
			}
		} else {
			if(!movesRemaining || this.pieces.size() == 2) {
				this.state = GameState.STALEMATE;
			} else {
				this.state = GameState.NORMAL;
			}
		}
		
		this.monitor.display(state);
		DragController.flush();
	}
	
	public static void setSide(int side) {
		ChessBoard.thisTurn = side == 1;
		ChessBoard.side = side;
	}
	
	private boolean movesRemaining() {
		ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
		for(ChessPiece piece: this.pieces.values()) {
			pieces.add(piece);
		}
		boolean whiteMoves = false;
		boolean blackMoves = false;
		for(ChessPiece piece: pieces) {
			if(piece.acceptEvents()) {
				if(piece.getSide() == ChessBoard.side) {
					if(piece.getLegalMoves(this).size() > 0) {
						whiteMoves = true;
					}
				} else {
					if(piece.getLegalMoves(this).size() > 0) {
						blackMoves = true;
					}
				}
			}
		}
		
		System.out.println(whiteMoves + " " + blackMoves);
		
		return whiteMoves && blackMoves;
	}
	
	private King defendingKing() {
		for(ChessPiece piece: this.pieces.values()) {
			if(piece instanceof King) {
				if(piece.getSide() == ChessBoard.side) {
					return (King) piece;
				}
			}
		}
		
		return null;
	}
	
	private King attackingKing() {
		for(ChessPiece piece: this.pieces.values()) {
			if(piece instanceof King) {
				if(piece.getSide() != ChessBoard.side) {
					return (King) piece;
				}
			}
		}
		
		return null;
	}
	
	public boolean kingInCheck() {
		
		King defendingKing = this.defendingKing();
		
		if(defendingKing == null) return true;
		
		for(ChessPiece piece: this.pieces.values()) {
			if(piece.getSide() != ChessBoard.side) {
				for(Move m: piece.getValidMoves(this)) {
					if(m.getX2() == defendingKing.getGridX() && m.getY2() == defendingKing.getGridY()) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean kingsInCheck() {
		
		King attackingKing = this.attackingKing();
		
		if(attackingKing == null) return true;
		
		for(ChessPiece piece: this.pieces.values()) {
			if(piece.getSide() == ChessBoard.side) {
				for(Move m: piece.getValidMoves(this)) {
					if(m.getX2() == attackingKing.getGridX() && m.getY2() == attackingKing.getGridY()) {
						return true;
					}
				}
			}
		}
		
		if(this.kingInCheck()) return true;
		
		return false;
	}
	
	public ArrayList<Move> checkForCheck(ArrayList<Move> moves) {
		
		ArrayList<Move> retVal = new ArrayList<Move>();
		
		for(Move m: moves) {
			this.testMove(m.getX1(), m.getY1(), m.getX2(), m.getY2());
			if(!this.kingInCheck()) {
				retVal.add(m);
			}
			this.testMove(m.getX2(), m.getY2(), m.getX1(), m.getY1());
		}
		
		return retVal;
	}
	
	public ArrayList<Move> checkMoves(ArrayList<Move> moves, int side) {
		ArrayList<Move> retVal = new ArrayList<Move>();
		
		for(Move m: moves) {
			if(m.getX2() >= 0 && m.getY2() >= 0 && m.getX2() < 8 && m.getY2() < 8) {
				if(this.pieceAt(m.getX2(), m.getY2())) {
					if(this.getPiece(m.getX2(), m.getY2()).getSide() != side) {
						retVal.add(m);
					}
				} else {
					retVal.add(m);
				}
			}
		}
		
		return retVal;
	}
	
	public void testMove(int x1, int y1, int x2, int y2) {
		ChessPiece piece = this.pieces.get(y1*8+x1);
		piece.executeMove(x2, y2, true);
		if(!this.pieces.containsKey(y2*8+x2)) {
			this.pieces.remove(y1*8+x1);
			this.pieces.put(y2*8+x2, piece);
			if(this.hold != null && this.holding) {
				this.pieces.put(y1*8+x1, hold);
				this.holding = false;
			}
		} else {
			this.hold = this.pieces.get(y2*8+x2);
			this.holding = true;
			this.pieces.remove(y1*8+x1);
			this.pieces.put(y2*8+x2, piece);
		}
	}
	 
	public void movePiece(int x1, int y1, int x2, int y2, boolean sendMessage) {
		
		ChessBoard.thisTurn = !sendMessage;
		
		if(this.pieceAt(x2, y2)) {
			ChessPiece capture = this.getPiece(x2, y2);
			ChessBoard.capX += capture.getWidth() + 10;
			capture.capture();
			this.pieces.remove(capture.getGridY()*8 + capture.getGridX());
		}
		
		ChessPiece piece = this.pieces.get(y1*8+x1);
		piece.executeMove(x2, y2, false);
		this.pieces.remove(y1*8+x1);
		this.pieces.put(y2*8+x2, piece);
		
		if(sendMessage) {
			Request out = new Request("move");
			
			out.addParameter("x1", x1);
			out.addParameter("y1", y1);
			out.addParameter("x2", x2);
			out.addParameter("y2", y2);
			
			Session.send(out.toString());
		}
		
		this.updateState();
	}
	
	public boolean pieceAt(int x, int y) {
		return this.pieces.containsKey(y*8+x);
	}
	 
	public ChessPiece getPiece(int x, int y) {
		if(this.pieces.containsKey(y*8+x)) {
			ChessPiece piece = this.pieces.get(y*8+x);
			piece.setGridX(x);
			piece.setGridY(y);
			
			return piece;
		}
		
		return null;
	}
	
}
