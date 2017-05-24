package it.polimi.ingsw.gc_31.model.board;

public class CouncilsPalaceWrapper extends SpaceWrapper {

    CouncilsPalaceWrapper(int positionID, int diceBond) {
        super(positionID, diceBond);
    }

    @Override
    public void execWrapper() {
        //TODO
        putPlayerFirst();
        setOccupied(true);
    }

    private void putPlayerFirst() {
        // TODO
    }
}
