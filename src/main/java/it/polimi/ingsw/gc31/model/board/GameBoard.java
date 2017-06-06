package it.polimi.ingsw.gc31.model.board;

import java.util.HashMap;
import java.util.Map;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Dice;
import it.polimi.ingsw.gc31.model.DiceColor;
import it.polimi.ingsw.gc31.model.cards.CardColor;

public class GameBoard {

    private Map<CardColor,Tower> towers;
    private Map<DiceColor, Dice> dices;
    private Map<String, SpaceWrapper> boardSpaces;
    private GameInstance gameInstance;
    private int positionIndex;

    public GameBoard(GameInstance gameInstance) {

        this.gameInstance = gameInstance;

        //Initialize dice
        createDice();

        setPositionIndex(1);

        // Initialize Tower on GameBoard
        for (CardColor color: CardColor.values()) {
            towers.put(color,new Tower(color, this));
        }

        //Initialize CouncilsPalace
        boardSpaces.put("COUNCILS PALACE", new CouncilsPalaceWrapper(positionIndex,1, this));
        incrementPositionIndex();

        //TODO Condizione  2-4 giocatori

        //Initialize Mart
        for (int i = 1; i < 5; i++) {
        boardSpaces.put("MART #" + i, new MartWrapper(positionIndex,1, this));
        incrementPositionIndex();
        }

        //Initialize Harvest & Production
        boardSpaces.put("PRODUCTION SINGLE", new ProductionWrapper(positionIndex, 1, false, this));
        incrementPositionIndex();
        boardSpaces.put("PRODUCTION MULTIPLE", new ProductionWrapper(positionIndex, 3,true, this));
        incrementPositionIndex();
        boardSpaces.put("HARVEST SINGLE", new HarvestWrapper(positionIndex, 1, false, this));
        incrementPositionIndex();
        boardSpaces.put("HARVEST MULTIPLE", new HarvestWrapper(positionIndex, 3,true, this));

        setPositionIndex(1);
    }

    private void createDice() {
        for(DiceColor color: DiceColor.values()) {
            dices.put(color, new Dice(color));
        }
    }

    public Map<DiceColor, Dice> getDices() {
        return dices;
    }

    public Dice getDiceByColor(DiceColor diceColor) {
        return dices.get(diceColor);
    }

    public Map<String, SpaceWrapper> getOpenSpaces() {

    	Map availablePlaces = new HashMap<String, SpaceWrapper>();

    	for(Map.Entry<String,SpaceWrapper> entry: boardSpaces.entrySet()) {
            if (!entry.getValue().isOccupied()) {
                availablePlaces.put(entry.getKey(), entry.getValue());
            }
        }
    	return availablePlaces;
    }

    public Map<String, SpaceWrapper> getBoardSpaces() {
        return boardSpaces;
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

    public Map<CardColor, Tower> getTowers() {
        return towers;
    }

    public Tower getTowerByColor(CardColor color) {
        return towers.get(color);
    }
}
