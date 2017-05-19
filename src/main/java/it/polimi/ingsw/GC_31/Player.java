package it.polimi.ingsw.GC_31;

enum PlayerColor {
	RED, BLUE, GREEN, YELLOW;
	
	public static Boolean contains(String value) {
		String upperCaseValue = value.toUpperCase();
		for(PlayerColor color : PlayerColor.values()) {
			if(color.name().equals(upperCaseValue)) {
				return true;
			}
		}
		return false;
	}
}

public class Player {
	private final int playerID;
	private final String playerName;
	private final PlayerColor playerColor;
	
//	Resources
	private int wood;
	private int stone;
	private int servant;
	private int gold;
	private int faithPoints;
	private int warPoints;
	private int victoryPoints;
	
//	Cards
	private Card[] cards;

// 	TODO Family Members
	
	public Player(int playerID, String playerName, PlayerColor playerColor) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.playerColor = playerColor;
		
//		Game preparation phase
//		Points
		this.stone = 2; 
		this.servant = 3;
		this.wood = 2;

//		Cards
		this.cards = new Card[24];
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public int getStone() {
		return stone;
	}

	public void setStone(int stone) {
		this.stone = stone;
	}

	public int getServant() {
		return servant;
	}

	public void setServant(int servant) {
		this.servant = servant;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getFaithPoints() {
		return faithPoints;
	}

	public void setFaithPoints(int faithPoints) {
		this.faithPoints = faithPoints;
	}

	public int getWarPoints() {
		return warPoints;
	}

	public void setWarPoints(int warPoints) {
		this.warPoints = warPoints;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public int getPlayerID() {
		return playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public PlayerColor getPlayerColor() {
		return playerColor;
	}
}
