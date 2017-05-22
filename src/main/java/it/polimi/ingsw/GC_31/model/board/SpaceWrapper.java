package it.polimi.ingsw.GC_31.model.board;

public abstract class SpaceWrapper {

    protected int positionID;
    protected int diceBond;
    protected boolean isOccupied;
    //protected FamilyMember member;

    public SpaceWrapper() {
        isOccupied = false;
    }

    public abstract void execWrapper();

}