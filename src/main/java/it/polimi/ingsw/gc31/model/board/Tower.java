package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.cards.CardColor;

public class Tower {
    private final CardColor towerColor;
    private boolean isOccupied;

    public Tower(CardColor towerColor, GameBoard gameBoard) {
        isOccupied = false;
        this.towerColor = towerColor;
        int diceValue = 1;
        for (int floor = 0; floor < 4; floor++) {
            gameBoard.getBoard().add(new TowerSpaceWrapper(
                    gameBoard.getPositionIndex(),
                    diceValue,
                    gameBoard,
                    floor,
                    towerColor));
            gameBoard.incrementPositionIndex();
            diceValue+=2;
        }
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public CardColor getTowerColor() {
        return towerColor;
    }
}
