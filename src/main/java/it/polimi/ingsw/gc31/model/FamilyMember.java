package it.polimi.ingsw.gc31.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.board.TowerSpaceWrapper;
import it.polimi.ingsw.gc31.model.resources.Resource;

public class FamilyMember {
	private final DiceColor color;
	private Dice dice;
    private final Player player;
	private final PlayerColor playerColor;
	private Boolean isNeutral;
	private Boolean isPlaced;
	private int dicePoints;
	private int value;
	private SpaceWrapper currentPosition;
	private GameBoard board;

	public FamilyMember(DiceColor color, Player player, GameBoard board) {
        this.color = color;
		this.playerColor = player.getPlayerColor();
        this.player = player;
		this.isPlaced = false;
		this.board = board;

		if(color == DiceColor.NEUTRAL) {
		    this.isNeutral = true;
		    this.value = 0;
        }

        this.setMyDice();
	}

	public void setValueFromDice() {
//	    This will be called after the dice is thrown for the first time.
        this.value = this.dice.getValue();

    }

    private void setMyDice() {
		this.dice = this.board.getDiceByColor(this.color);
    }

	public List<SpaceWrapper> checkPossibleMovements() {
        List<SpaceWrapper> openSpaces = board.openSpaces();
        return openSpaces;
	}

	private boolean[] isAffordable(TowerSpaceWrapper spaceWrapper) {
        Map<String, Resource> playerResources = this.player.getResources();
        Map<String, Resource>[] cardResources = spaceWrapper.getCard().getCost();
        boolean[] results = new boolean[cardResources.length];

        for(int i=0; i<cardResources.length; i++) {
            for(Map.Entry<String, Resource> playerResource: playerResources.entrySet()) {
                int playerResourceValue = playerResource.getValue().getNumOf();
                int cardResourceValue = cardResources[i].get(playerResource.getKey()).getNumOf();

                if(playerResourceValue < cardResourceValue) {
                    results[i] = false;
                }
            }
            results[i] = true;
        }
        return results;
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

	public Player getPlayer() {
		return player;
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

    public int getDicePoints() {
        return dicePoints;
    }

}
