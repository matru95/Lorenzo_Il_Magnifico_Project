package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class SpaceWrapper implements Serializable{

    private final int positionID;
    private final int diceBond;
    private boolean isOccupied;
    private GameBoard gameBoard;

    /**
     *
     * @param positionID
     * @param diceBond
     * @param gameBoard
     */
    public SpaceWrapper(int positionID , int diceBond, GameBoard gameBoard) {
        this.positionID = positionID;
        this.diceBond = diceBond;
        this.gameBoard = gameBoard;
        isOccupied = false;
    }

    public abstract List<ServerMessage> execWrapper(FamilyMember familyMember, int amountOfServants);

    public abstract ObjectNode toJson();

    /**
     * Returns a JSON-formatted string of this class
     * @return JSON String representation of the class
     */
    public abstract String toString();

//  Check whether a player has enough resources to move a familymember here.
    public abstract boolean isAffordable(FamilyMember familyMember, Map<ResourceName, Resource> playerResources, PlayerColor playerColor);

    /**
     * 
     * @param familyMember
     */
    public abstract void setFamilyMember(FamilyMember familyMember);

    /**
     *
     * @return
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     *
     * @param occupied
     */
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    /**
     *
     * @return
     */
    public int getPositionID() {
        return positionID;
    }

    /**
     *
     * @return
     */
    public int getDiceBond() {
        return diceBond;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Returns an ObjectNode to create JSON implementations of SpaceWrappers in classes that extend this one.
     * @return ObjectNode
     * @see #toString()
     */
    protected ObjectNode toObjectNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode spaceWrapperNode = mapper.createObjectNode();

        spaceWrapperNode.put("positionID", positionID);
        spaceWrapperNode.put("diceBond", diceBond);
        spaceWrapperNode.put("isOccupied", isOccupied);

        return spaceWrapperNode;
    }

    /**
     * Return true if already has a family member with that color.
     * @param playerColor playercolor
     * @param familyMembers the family member
     * @return boolean
     */
    protected boolean hasFamilyMemberSameColor(PlayerColor playerColor, List<FamilyMember> familyMembers) {
        for(FamilyMember familyMember: familyMembers) {
            PlayerColor familyMemberColor = familyMember.getPlayerColor();

            if(familyMember.getColor() != DiceColor.NEUTRAL && familyMemberColor == playerColor) {

                return true;
            }
        }

        return false;
    }

    /**
     * Resets the wrapper to the initial state
     */
    public abstract void reset();

}
