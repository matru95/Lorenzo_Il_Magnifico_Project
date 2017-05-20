package it.polimi.ingsw.GC_31.board;

public class CouncilsPalaceWrapper extends SpaceWrapper implements SpaceBonus {

    public CouncilsPalaceWrapper(int positionID, int diceBond) {
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
        putPlayerFirst();
    }

    private void putPlayerFirst() {
        // TODO
    }
}
