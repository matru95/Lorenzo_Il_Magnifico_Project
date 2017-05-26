package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.GameInstance;

public class MartWrapper extends SpaceWrapper {

    MartWrapper(int positionID, int diceBond, GameInstance gameInstance) {
        super(positionID, diceBond, gameInstance);
    }

    @Override
    public void execWrapper() {
        //TODO
        setOccupied(true);
    }

}
