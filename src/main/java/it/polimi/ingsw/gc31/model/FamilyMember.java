package it.polimi.ingsw.gc31.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.exceptions.MovementInvalidException;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.board.*;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

public class FamilyMember {
	private Dice dice;
    private final Player player;
	private final PlayerColor playerColor;
	private Boolean isNeutral;
	private Boolean isPlaced;
    private int value;
	private SpaceWrapper currentPosition;
	private GameBoard board;
	private boolean isMovedThisTurn;

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
        familyMemberNode.put("playerColor", player.getPlayerColor().toString());
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
            return "";
        }

    }

    /**
     * Finds the correct dice by color and sets its value to this family member.
     */
    public void setValueFromDice() {
        this.value = this.dice.getValue();

    }

    /**
     * It's called once after the creation of the family member and sets the dice color.
     * @param color the dice color that belongs to this family member.
     */
    private void setMyDice(DiceColor color) {
		this.dice = this.board.getDiceByColor(color);
    }

    public void moveToTower(TowerSpaceWrapper position) {
        checkAndPayExtraGold(position);
        player.addCard(position.getCard());
    }


    public List<ServerMessage> moveToPosition(SpaceWrapper position, int numOfServantsPaid) throws MovementInvalidException {

	    if(!isMovementPossible(position)) {
	        throw new MovementInvalidException();
        }

        if(position.getClass() == TowerSpaceWrapper.class) {
            moveToTower((TowerSpaceWrapper) position);
        }

	    int positionDiceBond = position.getDiceBond();

//	    Check if I should pay servants and pay them
	    checkAndPayServants(positionDiceBond);

		this.currentPosition = position;
		position.setFamilyMember(this);
		this.isMovedThisTurn = true;

		List<ServerMessage> requests =  position.execWrapper(this, numOfServantsPaid);
		return requests;
    }

	private boolean isMovementPossible(SpaceWrapper position) {
        Map<ResourceName, Resource> playerResources = this.player.getRes();
        boolean cardLimitReached = false;

        if(position.getClass() == TowerSpaceWrapper.class) {
            cardLimitReached = isCardLimitReached(((TowerSpaceWrapper) position).getColor());
        }

        if(position.isOccupied() || cardLimitReached || this.isMovedThisTurn) {
            return false;
        }

        return position.isAffordable(this, playerResources, playerColor);
    }


	private void checkAndPayServants(int positionDiceBond) {
        if(positionDiceBond > this.value) {
            Map<ResourceName, Resource> playerResources = player.getRes();
            int currentServants = playerResources.get(ResourceName.SERVANTS).getNumOf();
            int costToPay = positionDiceBond - this.value;

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

    public boolean isCardLimitReached(CardColor cardColor){

        return this.player.getCards().get(cardColor).size() == 6;
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

    public void setMovedThisTurn(boolean movedThisTurn) {
        isMovedThisTurn = movedThisTurn;
    }
}
