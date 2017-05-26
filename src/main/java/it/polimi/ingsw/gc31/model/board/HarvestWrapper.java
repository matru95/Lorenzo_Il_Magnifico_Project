package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.GameInstance;

public class HarvestWrapper extends SpaceWrapper {

    public boolean isFirstPlayer;
    private boolean isMultiple;

    HarvestWrapper(int positionID, int diceBond, boolean isMultiple, GameInstance gameInstance) {
        super(positionID, diceBond, gameInstance);
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