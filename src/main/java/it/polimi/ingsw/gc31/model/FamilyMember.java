package it.polimi.ingsw.gc31.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.board.*;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

public class FamilyMember {
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

		this.playerColor = player.getPlayerColor();
        this.player = player;
		this.isPlaced = false;
		this.board = board;
        this.currentPosition = new NullWrapper();

        if(color == DiceColor.NEUTRAL) {
		    this.isNeutral = true;
		    this.value = 0;
        }
        this.setMyDice(color);

	}

	public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode familyMemberNode = mapper.createObjectNode();

        familyMemberNode.put("color", dice.getColor().toString());
        familyMemberNode.put("value", dice.getValue());
        familyMemberNode.put("currentPositionID", currentPosition.getPositionID());

        return familyMemberNode;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode familyMemberNode = mapper.createObjectNode();

        familyMemberNode.put("color", dice.getColor().toString());
        familyMemberNode.put("value", dice.getValue());
        familyMemberNode.put("currentPositionID", currentPosition.getPositionID());

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(familyMemberNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

    }

	public void setValueFromDice() {
//	    This will be called after the dice is thrown for the first time.
        this.value = this.dice.getValue();

    }

    private void setMyDice(DiceColor color) {
		this.dice = this.board.getDiceByColor(color);
    }

	public List<SpaceWrapper> checkPossibleMovements() {

	    //TODO This method will be changed once FaithCards are implemented
        List<SpaceWrapper> possibleMovements = new ArrayList<>();
        List<SpaceWrapper> openSpaces = new ArrayList<>();

        insertOpenSpaces(openSpaces);

        int possiblePoints = dicePoints + this.player.getRes().get(ResourceName.SERVANTS).getNumOf();

        insertTowerSpaceWrappers(openSpaces);


        for(SpaceWrapper spaceWrapper: openSpaces) {
            boolean isAffordable = spaceWrapper.isAffordable(this.player.getRes(), this.playerColor);
            int spaceDiceBond = spaceWrapper.getDiceBond();

            if(isAffordable && spaceDiceBond <= possiblePoints) {
                possibleMovements.add(spaceWrapper);
            }
        }

        return possibleMovements;
	}

	private void insertOpenSpaces(List<SpaceWrapper> possibleMovements) {

//	    Get every board space except towers
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

            if(checkCardNumBond(towerEntry.getKey())) {

                Tower tower = towerEntry.getValue();
                boolean hasFamilyMemberColor = tower.hasFamilyMemberSameColor(playerColor);

                if (!hasFamilyMemberColor) {

                    for (Map.Entry<Integer, TowerSpaceWrapper> towerSpaceWrapperEntry : towerEntry.getValue().getTowerSpace().entrySet()) {
                        towerSpaceWrappers.add(towerSpaceWrapperEntry.getValue());
                    }
                }
            }
        }
    }

    public void moveToTower(TowerSpaceWrapper position, int numOfServantsPaid) throws NoResourceMatch {
        checkAndPayExtraGold(position);
        player.addCard(position.getCard());
    }

	public void moveToPosition(SpaceWrapper position, int numOfServantsPaid) throws NoResourceMatch {

        if(position.getClass() == TowerSpaceWrapper.class) {
            moveToTower((TowerSpaceWrapper) position, numOfServantsPaid);
        }

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
    public boolean checkCardNumBond(CardColor cardColor){

        if (this.player.getCards().get(cardColor).size() < 6) {
            return true;
        }

        return false;
    }

    /**
     * Getter for attribute "color" of FamilyMember's attribute "dice"
     * @return DiceColor
     */
    public DiceColor getColor() {
        return this.dice.getColor();
    }

    /**
     * Getter for attribute "currentPosition"
     * @return SpaceWrapper
     */
	public SpaceWrapper getCurrentPosition() {
		return currentPosition;
	}

    /**
     * Getter for attribute "isPlaced"
     * @return Boolean
     */
	public Boolean getPlaced() {
		return isPlaced;
	}

    /**
     * Getter for attribute "player"
     * @return Player
     */
	public Player getPlayer() {
		return player;
	}

    /**
     * Getter for attribute "value"
     * @return int
     */
	public int getValue() {
	    return this.value;
    }

    /**
     * Getter for attribute "isNeutral"
     * @return Boolean
     */
    public Boolean getNeutral() {
        return isNeutral;
    }

    /**
     * Getter for attribute "playerColor"
     * @return PlayerColor
     */
    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    /**
     * Getter for attribute "dicePoints"
     * @return int
     */
    public int getDicePoints() {
        return dicePoints;
    }

    /**
     * Getter for attribute "dice"
     * @return Dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Getter for attribute "board"
     * @return GameBoard
     */
    public GameBoard getBoard() {
        return board;
    }
}
