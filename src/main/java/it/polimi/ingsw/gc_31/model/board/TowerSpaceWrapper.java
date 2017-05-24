package it.polimi.ingsw.gc_31.model.board;

import it.polimi.ingsw.gc_31.model.cards.Card;
import it.polimi.ingsw.gc_31.model.cards.CardColor;
>>>>>>> 8ed99268d0443b32529f9aecbc6be04b199fe4f5:src/main/java/it/polimi/ingsw/GC_31/model/board/TowerSpaceWrapper.java

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
