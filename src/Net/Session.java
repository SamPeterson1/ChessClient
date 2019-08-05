package Net;

import Game.ChessBoard;
import Game.GameState;

public class Session {
	
	private static ChessBoard board;
	private static Client client;
	private static int side;
	
	public static void init() {
		Session.board = new ChessBoard();
	}
	
	public static GameState getState() {
		return Session.board.getState();
	}
	
	public static int getSide() {
		return Session.side;
	}
	
	public static void update() {
		String message = client.nonBlockRead();
		if(!message.equals("nothing")) {
			Request r = Request.parseString(message);
			
			if(r.getMessage().equals("move")) {
				Session.board.movePiece(r.getInt("x1"), r.getInt("y1"), r.getInt("x2"), r.getInt("y2"), false);
			}
		}
	}
	
	public static void setClient(Client c) {
		Session.client = c;
		Session.side = Integer.parseInt(client.read());
		ChessBoard.setSide(Session.side);
	}
	
	public static void send(String message) {
		Session.client.write(message);
	}
	
	public static ChessBoard getBoard() {
		return Session.board;
	}
}
