package it.polimi.ingsw.GC_31.board;

import java.util.ArrayList;

public class GameBoard {

    private ArrayList<SpaceWrapper> board = new ArrayList<SpaceWrapper>();

    public GameBoard() {

        // positionID starting from value 1
        int pos = 1;

        // Initialize Tower on GameBoard
        for (CardColor c: CardColor.values) {
            int dice = 1;
            for(int floor = 0; floor < 4; floor++) {
                board.add(new TowerSpaceWrapper(pos, dice, floor, c));
                pos++;
                dice+=2;
            }

        }
    }

    public SpaceWrapper[] getSpace() {
        return board;
    }
}
