package it.polimi.ingsw.GC_31.board;

import it.polimi.ingsw.GC_31.Card;
import it.polimi.ingsw.GC_31.CardColor;

public class TowerSpaceWrapper extends SpaceWrapper implements SpaceBonus {

    private final int floorID;
    private final CardColor color;
    private Card card;

    public TowerSpaceWrapper(int positionID, int diceBond, int floorID, CardColor color) {
        super();
        this.positionID = positionID;
        this.diceBond = diceBond;
        this.floorID = floorID;
        this.color = color;
        //TODO Randomize a card
    }

    @Override
    public void execWrapper() {
        execBonus();
        isOccupied = true;
    }

    @Override
    public void execBonus() {
        //TODO
    }
}
