package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.Map;

public class ProductionWrapper extends SpaceWrapper {

    private boolean isFirstPlayer;
    private boolean isMultiple;

    ProductionWrapper(int positionID, int diceBond, boolean isMultiple, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
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

    @Override
    public boolean isAffordable(Map<String, Resource> playerResources) {
        return true;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        isFirstPlayer = firstPlayer;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }
}
