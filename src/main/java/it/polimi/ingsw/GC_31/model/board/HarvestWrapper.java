package it.polimi.ingsw.GC_31.model.board;

public class HarvestWrapper extends SpaceWrapper {

    private boolean isMultiple;

    public HarvestWrapper(int positionID, int diceBond, boolean isMultiple) {
        super();
        this.positionID = positionID;
        this.diceBond = diceBond;
        this.isMultiple = isMultiple;
    }

    @Override
    public void execWrapper() {
        harvest();
        if (isMultiple = false) { isOccupied = true; }
    }

    private void harvest(){

    }

}