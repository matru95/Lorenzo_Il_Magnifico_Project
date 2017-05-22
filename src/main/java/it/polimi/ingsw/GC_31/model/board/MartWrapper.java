package it.polimi.ingsw.GC_31.model.board;

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
