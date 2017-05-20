package it.polimi.ingsw.GC_31.board;

public class ProductionWrapper extends SpaceWrapper {

    private boolean isMultiple;

    public ProductionWrapper(int positionID, int diceBond, boolean isMultiple) {
        super();
        this.positionID = positionID;
        this.diceBond = diceBond;
        this.isMultiple = isMultiple;
    }

    @Override
    public void execWrapper() {
        produce();
        if (isMultiple = false) { isOccupied = true; }
    }

    private void produce(){

    }

}
