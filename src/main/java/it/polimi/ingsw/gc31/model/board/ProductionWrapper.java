package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.GameInstance;

public class ProductionWrapper extends SpaceWrapper {

    public boolean isFirstPlayer;
    private boolean isMultiple;

    ProductionWrapper(int positionID, int diceBond, boolean isMultiple, GameInstance gameInstance) {
        super(positionID, diceBond, gameInstance);
        this.isMultiple = isMultiple;
    }

    @Override
    public void execWrapper() {
        produce();
        if (!isMultiple) setOccupied(true);
    }

    private void produce(){
        //TODO
    }

}
