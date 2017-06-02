package it.polimi.ingsw.gc31.model.board;

public class MartWrapper extends SpaceWrapper {

    MartWrapper(int positionID, int diceBond, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
    }

    @Override
    public void execWrapper() {
        //TODO
        setOccupied(true);
    }

}
