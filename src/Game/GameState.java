package Game;

public enum GameState {
	
	NORMAL("Normal"), STALEMATE("Stalemate"), CHECK("Check"), CHECKMATE("Checkmate");
	
	String name;
	
	GameState(String name) {
		this.name = name;
	}	
	
	public String getName() {
		return this.name;
	}
}
