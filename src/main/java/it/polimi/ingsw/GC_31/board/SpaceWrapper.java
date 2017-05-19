package it.polimi.ingsw.GC_31.board;

public abstract class SpaceWrapper {

    protected int positionID;
    protected int diceBond;
    protected boolean isOccupied;

    public SpaceWrapper() {
        isOccupied = false;
    }

    public abstract void execWrapper();

}