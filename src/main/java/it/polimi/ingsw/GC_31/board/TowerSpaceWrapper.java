package it.polimi.ingsw.GC_31.board;

import it.polimi.ingsw.GC_31.Card;
import it.polimi.ingsw.GC_31.CardColor;

public class TowerSpaceWrapper extends SpaceWrapper implements SpaceBonus {

    private final int floorID;
    private Card card;
    //private final CardColor color;

    public TowerSpaceWrapper(int positionID, int diceBond, int floorID, Card card, CardColor color) {
        super();
        this.positionID = positionID;
        this.diceBond = diceBond;
        this.floorID = floorID;
        //this.color = color;
        this.card = card;
    }

    @Override
    public void execWrapper() {
        execBonus();
    }

    @Override
    public void execBonus() {
        //TODO
    }
}
