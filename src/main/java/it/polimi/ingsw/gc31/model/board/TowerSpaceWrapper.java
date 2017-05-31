package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;

public class TowerSpaceWrapper extends SpaceWrapper {

    private final int floorID;
    private final CardColor color;
    private Card card;

    TowerSpaceWrapper(int positionID, int diceBond, GameBoard gameBoard, int floorID, CardColor color) {
        super(positionID, diceBond, gameBoard);
        this.floorID = floorID;
        this.color = color;
        //TODO Randomize a card
    }

    @Override
    public void execWrapper() {
        //TODO
        setOccupied(true);
    }

    public int getFloorID() {
        return floorID;
    }

    public CardColor getColor() {
        return color;
    }

    public Card getCard() {
        return card;
    }
}
