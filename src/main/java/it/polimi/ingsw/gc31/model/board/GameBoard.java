package it.polimi.ingsw.gc31.model.board;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Dice;
import it.polimi.ingsw.gc31.model.DiceColor;
import it.polimi.ingsw.gc31.model.cards.CardColor;

public class GameBoard {

    private ArrayList<SpaceWrapper> board = new ArrayList<>();
    private GameInstance gameInstance;
    private Dice[] dice;

    public GameBoard(GameInstance gameInstance) {

        this.gameInstance = gameInstance;

        // positionID starting from value 1
        int pos = 1;

        // Initialize Tower on GameBoard
        for (CardColor c: CardColor.values()) {
            int dice = 1;
            for(int floor = 0; floor < 4; floor++) {
                board.add(new TowerSpaceWrapper(pos, dice, this.gameInstance, floor, c));
                pos++;
                dice+=2;
            }
        }

        //Initialize CouncilsPalace
        board.add(new CouncilsPalaceWrapper(pos,1, this.gameInstance));
        pos++;

        //Initialize Mart
        for (int i = 0; i < 4; i++) {
        board.add(new MartWrapper(pos,1, this.gameInstance));
        pos++;
        }

        //Initialize Harvest & Production
        board.add(new ProductionWrapper(pos, 1, false, this.gameInstance));
        pos++;
        board.add(new ProductionWrapper(pos, 3,true, this.gameInstance));
        pos++;
        board.add(new HarvestWrapper(pos, 1, false, this.gameInstance));
        pos++;
        board.add(new HarvestWrapper(pos, 3,true, this.gameInstance));

        //Initialize dice
        this.dice = new Dice[4];
        createDice();

    }

    private void createDice() {
        int key = 0;
        for(DiceColor color: DiceColor.values()) {
            dice[key] = new Dice(color);
            key++;
        }
    }

    public Dice[] getDice() {
        return dice;
    }

    public Dice getDiceByColor(DiceColor diceColor) {

        for(int i=0; i<dice.length; i++) {
            if(dice[i].getColor() == diceColor) {
                return dice[i];
            }
        }

        return null;
    }

    public List<SpaceWrapper> openSpaces() {
    	ArrayList<SpaceWrapper> availablePlaces = new ArrayList<>();

    	for(SpaceWrapper space : board) {
    		if(!space.isOccupied()) availablePlaces.add(space);
    	}
    	return availablePlaces;
    }

    public List<SpaceWrapper> getBoard() {
        return board;
    }

}
