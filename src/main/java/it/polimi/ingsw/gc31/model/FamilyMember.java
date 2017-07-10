package it.polimi.ingsw.gc31.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.exceptions.MovementInvalidException;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.board.*;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.effects.permanent.*;
import it.polimi.ingsw.gc31.model.effects.permanent.Bonus;
import it.polimi.ingsw.gc31.model.effects.permanent.CardColorBonus;
import it.polimi.ingsw.gc31.model.effects.permanent.Malus;
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
    //TODO DOCUMENTATION

    /**
     *
     * @param color
     * @param player
     * @param board
     */
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

    /**
     *
     * @return
     */
	public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode familyMemberNode = mapper.createObjectNode();

        familyMemberNode.put("color", dice.getColor().toString());
        familyMemberNode.put("playerColor", player.getPlayerColor().toString());
        familyMemberNode.put("value", dice.getValue());
        familyMemberNode.put("currentPositionID", currentPosition.getPositionID());

        return familyMemberNode;
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param position
     */
    private void moveToTower(TowerSpaceWrapper position) throws MovementInvalidException {
        player.addCard(position.getCard());
    }

    /**
     *
     * @param position
     * @param numOfServantsPaid
     * @return
     * @throws MovementInvalidException
     */
    public List<ServerMessage> moveToPosition(SpaceWrapper position, int numOfServantsPaid) throws MovementInvalidException {
	    int bonusAmount = 0;

        if(position == null || !isMovementPossible(position) || !hasEnoughValue(position)) {
	        throw new MovementInvalidException();
        }


        if(position.getClass() == TowerSpaceWrapper.class) {

            if(((TowerSpaceWrapper) position).getCard().getCardID() == 0) {

                throw new MovementInvalidException();
            }
            moveToTower((TowerSpaceWrapper) position);
            bonusAmount += player.getCardColorBonusValue(((TowerSpaceWrapper) position).getColor());
        }

	    int positionDiceBond = position.getDiceBond();

//	    Check if I should pay servants and pay them
	    checkAndPayServants(position,positionDiceBond, bonusAmount);

		this.currentPosition = position;
		position.setFamilyMember(this);
		this.isMovedThisTurn = true;

		return position.execWrapper(this, numOfServantsPaid);
    }

    /**
     *
     * @param position
     * @return
     */
	private boolean isMovementPossible(SpaceWrapper position) {
        Map<ResourceName, Resource> playerResources = this.player.getRes();
        boolean cardLimitReached = false;
        boolean towerHasFamilyMemberSameColor = false;

        if(position.getClass() == TowerSpaceWrapper.class) {
            cardLimitReached = isCardLimitReached(((TowerSpaceWrapper) position).getColor());
            Tower tower = ((TowerSpaceWrapper) position).getTower();

            if(getColor() != DiceColor.NEUTRAL) {

                towerHasFamilyMemberSameColor = tower.hasFamilyMemberSameColor(this.playerColor);
            }
        }

        if(position.isOccupied() || cardLimitReached || this.isMovedThisTurn || towerHasFamilyMemberSameColor) {
            return false;
        }

        return position.isAffordable(this, playerResources, playerColor);
    }

    private boolean hasEnoughValue(SpaceWrapper position) {
	    int positionValue = position.getDiceBond();
	    int numOfServants = player.getRes().get(ResourceName.SERVANTS).getNumOf();
	    int possibleValue = this.value + numOfServants;
        if(position.getClass() == TowerSpaceWrapper.class && player.getMaluses()!=null ){
            List<Malus> maluses= player.getMaluses();
            for(Malus malus: maluses){
                if(malus.getMalusType()== MalusEnum.CARDDICEMALUS){
                    CardColor cardColor=((CardDiceMalus)malus).getFewerDiceCardColor();
                    int fewerDiceCardValue=((CardDiceMalus)malus).getFewerDiceCardValue();
                    if(((TowerSpaceWrapper)position).getColor()==cardColor){
                        possibleValue-=fewerDiceCardValue;
                    }
                }
            }
        }

	    if(positionValue > possibleValue) {
	        return false;
        }

        return true;
    }

    /**
     *
     * @param positionDiceBond
     * @param bonusPoints
     */
	private void checkAndPayServants(SpaceWrapper position,int positionDiceBond, int bonusPoints) {
	    int finalDiceBond = positionDiceBond;

	    if(player.getMaluses() != null) {
	        List<Malus> maluses = player.getMaluses();
            for(Malus malus: maluses){
                if(malus.getMalusType()==MalusEnum.CARDDICEMALUS){
                    if(position.getClass()==TowerSpaceWrapper.class) {
                        if (((TowerSpaceWrapper) position).getColor() == ((CardDiceMalus) malus).getFewerDiceCardColor()) {
                            finalDiceBond += ((CardDiceMalus) malus).getFewerDiceCardValue();
                        }
                    }

                }
            }
	    }

        if(finalDiceBond > this.value + bonusPoints) {
            Map<ResourceName, Resource> playerResources = player.getRes();
            int currentServants = playerResources.get(ResourceName.SERVANTS).getNumOf();
            int costToPay = finalDiceBond - this.value - bonusPoints;

            playerResources.get(ResourceName.SERVANTS).setNumOf(currentServants - costToPay);
        }
    }

    /**
     *
     * @param cardColor
     * @return
     */
    public boolean isCardLimitReached(CardColor cardColor){
//      Check bond on green cards
        if(cardColor.equals(CardColor.GREEN)) {
            int numOfGreenCards = player.getNumOfCards(cardColor);
            int warPoints = player.getRes().get(ResourceName.WARPOINTS).getNumOf();

            if(numOfGreenCards == 2 && warPoints < 3) {
                return false;
            } else if(numOfGreenCards == 3 && warPoints < 7) {
                return false;
            } else if(numOfGreenCards == 4 && warPoints < 12) {
                return false;
            } else if(numOfGreenCards == 5 && warPoints < 18) {
                return false;
            }
        }

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

    public void setValue(int value){
        this.value=value;
    }

    public void setCurrentPosition(SpaceWrapper currentPosition) {
        this.currentPosition = currentPosition;
    }
}
