package it.polimi.ingsw.gc31.model;

import java.util.List;

import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;

public class FamilyMember {
	private final DiceColor color;
	private final PlayerColor playerColor;
	private Dice dice;

	private Boolean isNeutral;
	private Boolean isPlaced;

	private int dicePoints;
	private int value;

	private SpaceWrapper currentPosition;
	private GameBoard board;

	public FamilyMember(DiceColor color, PlayerColor playerColor, GameBoard board) {
		this.color = color;
		this.playerColor = playerColor;
		this.isPlaced = false;
		this.board = board;

		if(color == DiceColor.NEUTRAL) {
		    this.isNeutral = true;
        }

        this.setMyDice(this.board.getDice());
	}

	public void setValueFromDice() {
        this.value = this.dice.getValue();

    }

    private void setMyDice(Dice[] dice) {
//      Find the dice with this family member's color

        for(int i=0; i<dice.length; i++) {
            if(dice[i].getColor() == this.color) {
                this.dice = dice[i];
            }
        }
    }
	
	public List<SpaceWrapper> checkPossibleMovements() {
		return board.openSpaces();

	}
	
	public DiceColor getColor() {
		return this.color;
	}
	
	public void moveToPosition(SpaceWrapper position) {
		this.currentPosition = position;

	}

	public SpaceWrapper getCurrentPosition() {
		return currentPosition;
	}

	public Boolean getPlaced() {

		return isPlaced;
	}

	public int getValue() {

	    return this.value;
    }

	public Boolean getNeutral() {

		return isNeutral;
	}

	public PlayerColor getPlayerColor() {

		return playerColor;
	}
}
