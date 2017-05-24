package it.polimi.ingsw.gc31.model.board;

public class ProductionWrapper extends SpaceWrapper {

    private boolean isMultiple;

    ProductionWrapper(int positionID, int diceBond, boolean isMultiple) {
        super(positionID, diceBond);
        this.isMultiple = isMultiple;
    }

    @Override
    public void execWrapper() {
        produce();
        if (!isMultiple) setOccupied(true);
    }

    private void produce(){

    }

}
