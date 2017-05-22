package it.polimi.ingsw.GC_31.model.board;

import it.polimi.ingsw.GC_31.model.Card;
import it.polimi.ingsw.GC_31.model.CardColor;

public class TowerSpaceWrapper extends SpaceWrapper {

    private final int floorID;
    private final CardColor color;
    private Card card;

    TowerSpaceWrapper(int positionID, int diceBond, int floorID, CardColor color) {
        super(positionID, diceBond);
        this.floorID = floorID;
        this.color = color;
        //TODO Randomize a card
    }

    @Override
    public void execWrapper() {
        //TODO
        setOccupied(true);
    }

}
