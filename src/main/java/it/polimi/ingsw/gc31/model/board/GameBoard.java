package it.polimi.ingsw.gc31.model.board;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Dice;
import it.polimi.ingsw.gc31.model.DiceColor;
import it.polimi.ingsw.gc31.model.cards.CardColor;

public class GameBoard {

    private Tower[] tower;
    private ArrayList<SpaceWrapper> board = new ArrayList<>();
    private GameInstance gameInstance;
    private Dice[] dice;
    private int positionIndex;

    public GameBoard(GameInstance gameInstance) {

        this.gameInstance = gameInstance;
        setPositionIndex(1);

        // Initialize Tower on GameBoard
        int n = 0;
        for (CardColor color: CardColor.values()) {
            tower[n] = new Tower(color, this);
            n++;
        }
        //Initialize CouncilsPalace
        board.add(new CouncilsPalaceWrapper(positionIndex,1, this));
        incrementPositionIndex();

        //Initialize Mart
        for (int i = 0; i < 4; i++) {
        board.add(new MartWrapper(positionIndex,1, this));
        incrementPositionIndex();
        }

        //Initialize Harvest & Production
        board.add(new ProductionWrapper(positionIndex, 1, false, this));
        incrementPositionIndex();
        board.add(new ProductionWrapper(positionIndex, 3,true, this));
        incrementPositionIndex();
        board.add(new HarvestWrapper(positionIndex, 1, false, this));
        incrementPositionIndex();
        board.add(new HarvestWrapper(positionIndex, 3,true, this));

        //Initialize dice
        this.dice = new Dice[4];
        createDice();

        setPositionIndex(1);
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

        for(int i = 0; i < dice.length; i++) {
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

    public void incrementPositionIndex() {
        positionIndex++;
    }

    public void setPositionIndex(int value) {
        positionIndex =  value;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public GameInstance getGameInstance() {
        return gameInstance;
    }

    public Tower[] getTower() {
        return tower;
    }

}
