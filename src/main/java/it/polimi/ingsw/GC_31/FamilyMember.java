package it.polimi.ingsw.GC_31;

import it.polimi.ingsw.GC_31.board.SpaceWrapper;
import it.polimi.ingsw.GC_31.board.GameBoard;

public class FamilyMember {
	private final DiceColor diceColor;
	private final PlayerColor playerColor;
	private Boolean isNeutral;
	private Boolean isPlaced;
	private int dicePoints;
	private SpaceWrapper currentPosition;
	private GameBoard board;

	public FamilyMember(DiceColor diceColor, PlayerColor playerColor, Boolean isNeutral, GameBoard board) {
		this.diceColor = diceColor;
		this.playerColor = playerColor;
		this.isNeutral = isNeutral;
		this.isPlaced = false;
		this.board = board;
	}
	
	public ArrayList<SpaceWrapper> checkPossibleMovements() {
		ArrayList<SpaceWrapper> possibleMovements = board.openSpaces();
		return possibleMovements;

	}
	
	public void moveToPosition(SpaceWrapper position) {
		this.currentPosition = position;
		return;
		
	}
	

}
