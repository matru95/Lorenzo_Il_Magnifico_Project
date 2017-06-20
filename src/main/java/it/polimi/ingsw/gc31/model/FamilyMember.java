package it.polimi.ingsw.gc31.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.board.Tower;
import it.polimi.ingsw.gc31.model.board.TowerSpaceWrapper;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

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
	    System.out.println(color);
	    System.out.println(board);
		this.dice = this.board.getDiceByColor(this.color);
    }

	public List<SpaceWrapper> checkPossibleMovements() {

	    //TODO This method will be changed once FaithCards are implemented

        List<SpaceWrapper> possibleMovements = new ArrayList<>();
        insertOpenSpaces(possibleMovements);

        int possiblePoints = this.player.getRes().get(ResourceName.SERVANTS).getNumOf();

        List<SpaceWrapper> towerSpaceWrappers = new ArrayList<>();
        insertTowerSpaceWrappers(towerSpaceWrappers);

        for(SpaceWrapper towerSpaceWrapper: towerSpaceWrappers) {
            boolean isAffordable = towerSpaceWrapper.isAffordable(this.player.getRes(), this.playerColor);
            int spaceDiceBond = towerSpaceWrapper.getDiceBond();
            if(isAffordable && spaceDiceBond <= possiblePoints) {
                possibleMovements.add(towerSpaceWrapper);
            }
        }

        return possibleMovements;
	}

	private void insertOpenSpaces(List<SpaceWrapper> possibleMovements) {
        for(Map.Entry<String, SpaceWrapper> spaceEntry: board.getOpenSpaces().entrySet()) {
            possibleMovements.add(spaceEntry.getValue());
        }
    }

    private void insertTowerSpaceWrappers(List<SpaceWrapper> towerSpaceWrappers) {
	    /*
	    Insert towerSpaceWrappers from towers that do not already have a family member
	    of the same color
	     */
        Map<CardColor, Tower> towers = this.board.getTowers();

        for(Map.Entry<CardColor, Tower> towerEntry: towers.entrySet()) {
            Tower tower = towerEntry.getValue();
            boolean hasFamilyMemberColor = tower.hasFamilyMemberSameColor(playerColor);

            if(!hasFamilyMemberColor) {

                for(Map.Entry<Integer, TowerSpaceWrapper> towerSpaceWrapperEntry: towerEntry.getValue().getTowerSpace().entrySet()) {
                    towerSpaceWrappers.add(towerSpaceWrapperEntry.getValue());
                }
            }
        }
    }

	public DiceColor getColor() {
		return this.color;
	}

    public void moveToTower(TowerSpaceWrapper position, int numOfServantsPaid) throws NoResourceMatch {
        checkAndPayExtraGold(position);
        moveToPosition(position, numOfServantsPaid);
    }

	public void moveToPosition(SpaceWrapper position, int numOfServantsPaid) throws NoResourceMatch {
	    int positionDiceBond = position.getDiceBond();

//	    Check if I should pay servants and pay them
	    checkAndPayServants(positionDiceBond);

		this.currentPosition = position;
		position.execWrapper(this, numOfServantsPaid);
	}


	private void checkAndPayServants(int positionDiceBond) {
        if(positionDiceBond > this.dicePoints) {
            Map<ResourceName, Resource> playerResources = player.getRes();
            int currentServants = playerResources.get(ResourceName.SERVANTS).getNumOf();
            int costToPay = positionDiceBond - this.dicePoints;

            playerResources.get(ResourceName.SERVANTS).setNumOf(currentServants - costToPay);
        }
    }

    private void checkAndPayExtraGold(TowerSpaceWrapper position) {
        Tower tower = position.getTower();
        if(tower.isOccupied()) {

//          Check if tower is occupied and pay 3 gold in that case
            player.getRes().get(ResourceName.GOLD).subNumOf(3);
        }

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

    public Dice getDice() {
        return dice;
    }

    public GameBoard getBoard() {
        return board;
    }
}
