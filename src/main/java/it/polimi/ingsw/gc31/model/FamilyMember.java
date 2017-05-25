package it.polimi.ingsw.gc31.model;

import java.util.List;

import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;

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
	
	public List<SpaceWrapper> checkPossibleMovements() {
		return board.openSpaces();

	}
	
	public DiceColor getDiceColor() {
		return this.diceColor;
	}
	
	public void moveToPosition(SpaceWrapper position) {
		this.currentPosition = position;
		return;
		
	}

	public SpaceWrapper getCurrentPosition() {
		return currentPosition;
	}

	public Boolean getPlaced() {

		return isPlaced;
	}

	public Boolean getNeutral() {

		return isNeutral;
	}

	public PlayerColor getPlayerColor() {

		return playerColor;
	}
}
