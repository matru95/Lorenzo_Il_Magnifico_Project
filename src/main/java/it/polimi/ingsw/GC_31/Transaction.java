package it.polimi.ingsw.GC_31;

public abstract class Transaction {
	private int wood;
	private int gold;
	private int stone;
	private int servant;
	private int warPoints;
	
	public Transaction(int wood, int gold, int stone, int servant, int warPoints) {
		this.wood = wood;
		this.gold = gold;
		this.stone = stone;
		this.servant = servant;
		this.warPoints = warPoints;
	}
	
	

}
