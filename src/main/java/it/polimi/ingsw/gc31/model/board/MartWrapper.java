package it.polimi.ingsw.gc31.model.board;

public class MartWrapper extends SpaceWrapper {

    MartWrapper(int positionID, int diceBond) {
        super(positionID, diceBond);
    }

    @Override
    public void execWrapper() {
        //TODO
        setOccupied(true);
    }

}
