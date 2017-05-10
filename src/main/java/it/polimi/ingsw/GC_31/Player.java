package it.polimi.ingsw.GC_31;

enum PlayerColor {
	RED, BLUE, GREEN, YELLOW
}

public class Player {
	private int playerID;
	private String playerName;
	PlayerColor playerColor;
	private int wood;
	private int stone;
	private int servant;
	private int gold;
	private int faithPoints;
	private int warPoints;
	private int victoryPoints;
	
	public Player(int playerID, String playerName, PlayerColor playerColor) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.playerColor = playerColor;
		
		//Game preparation phase
		this.stone = 2; 
		this.servant = 3;
		this.wood = 2;
	}
}
