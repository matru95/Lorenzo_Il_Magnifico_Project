package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.List;
import java.util.Map;

public abstract class SpaceWrapper {

    private final int positionID;
    private final int diceBond;
    private boolean isOccupied;
    private GameBoard gameBoard;

    public SpaceWrapper(int positionID , int diceBond, GameBoard gameBoard) {
        this.positionID = positionID;
        this.diceBond = diceBond;
        this.gameBoard = gameBoard;
        isOccupied = false;
    }

    public abstract void execWrapper(Map<String, Resource> playerResources);

//  Check whether a player has enough resources to move a familymember here.
    public abstract boolean isAffordable(Map<String, Resource> playerResources);

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public int getPositionID() {
        return positionID;
    }

    public int getDiceBond() {
        return diceBond;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
