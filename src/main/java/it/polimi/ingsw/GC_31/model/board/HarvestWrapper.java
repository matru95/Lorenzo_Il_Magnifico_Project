package it.polimi.ingsw.GC_31.model.board;

public class HarvestWrapper extends SpaceWrapper {

    private boolean isMultiple;

    HarvestWrapper(int positionID, int diceBond, boolean isMultiple) {
        super(positionID, diceBond);
        this.isMultiple = isMultiple;
    }

    @Override
    public void execWrapper() {
        harvest();
        if (!isMultiple) setOccupied(true);
    }

    private void harvest(){
        //TODO
    }

}