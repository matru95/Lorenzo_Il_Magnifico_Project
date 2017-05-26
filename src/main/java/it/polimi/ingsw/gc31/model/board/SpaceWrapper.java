package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.GameInstance;

public abstract class SpaceWrapper {

    private final int positionID;
    private final int diceBond;
    private boolean isOccupied;
    private FamilyMember member;
    private GameInstance gameInstance;

    public SpaceWrapper(int positionID , int diceBond, GameInstance gameInstance) {
        this.positionID = positionID;
        this.diceBond = diceBond;
        this.gameInstance = gameInstance;
        isOccupied = false;
    }

    public abstract void execWrapper();

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

    public FamilyMember getMember() {
        return member;
    }

    public GameInstance getGameInstance() {
        return gameInstance;
    }
}
