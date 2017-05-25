package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.FamilyMember;

public abstract class SpaceWrapper {

    private final int positionID;
    private final int diceBond;
    private boolean isOccupied;
    private FamilyMember member;

    public SpaceWrapper(int positionID , int diceBond) {
        this.positionID = positionID;
        this.diceBond = diceBond;
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
}
