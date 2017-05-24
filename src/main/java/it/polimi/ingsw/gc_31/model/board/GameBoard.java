package it.polimi.ingsw.gc_31.model.board;

import java.util.ArrayList;

=======
import it.polimi.ingsw.gc_31.model.cards.CardColor;
>>>>>>> 8ed99268d0443b32529f9aecbc6be04b199fe4f5:src/main/java/it/polimi/ingsw/GC_31/model/board/GameBoard.java

public class GameBoard {

    private ArrayList<SpaceWrapper> board = new ArrayList<SpaceWrapper>();

    public GameBoard() {

        // positionID starting from value 1
        int pos = 1;

        // Initialize Tower on GameBoard
        for (CardColor c: CardColor.values()) {
            int dice = 1;
            for(int floor = 0; floor < 4; floor++) {
                board.add(new TowerSpaceWrapper(pos, dice, floor, c));
                pos++;
                dice+=2;
            }
        }

        //Initialize CouncilsPalace
        board.add(new CouncilsPalaceWrapper(pos,1));
        pos++;

        //Initialize Mart
        for (int i = 0; i < 4; i++) {
        board.add(new MartWrapper(pos,1));
        pos++;
        }

        //Initialize Harvest & Production
        board.add(new ProductionWrapper(pos, 1, false));
        pos++;
        board.add(new ProductionWrapper(pos, 3,true));
        pos++;
        board.add(new HarvestWrapper(pos, 1, false));
        pos++;
        board.add(new HarvestWrapper(pos, 3,true));
    }
    
    public ArrayList<SpaceWrapper> openSpaces() {
    	ArrayList<SpaceWrapper> availablePlaces = new ArrayList<SpaceWrapper>();

    	for(SpaceWrapper space : board) {
    		if(!space.isOccupied()) availablePlaces.add(space);
    	}
    	return availablePlaces;
    }

    public ArrayList<SpaceWrapper> getBoard() {
        return board;
    }
}
