package it.polimi.ingsw.GC_31.board;

public class MartWrapper extends SpaceWrapper implements SpaceBonus {

    public MartWrapper(int positionID, int diceBond) {
        super();
        this.positionID = positionID;
        this.diceBond = diceBond;
    }

    @Override
    public void execWrapper() {
        execBonus();
        isOccupied = true;
    }

    @Override
    public void execBonus() {
        //TODO
    }
}
