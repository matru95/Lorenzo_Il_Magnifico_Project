package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.Map;

public class HarvestWrapper extends SpaceWrapper {

    private boolean isFirstPlayer;
    private boolean isMultiple;

    HarvestWrapper(int positionID, int diceBond, boolean isMultiple, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
        this.isMultiple = isMultiple;
    }

    @Override
    public void execWrapper(Map<String, Resource> playerResources) {
        harvest();
        if (!isMultiple) setOccupied(true);
    }

    @Override
    public boolean isAffordable(Map<String, Resource> playerResources) {
        return true;
    }

    private void harvest(){
        //TODO
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