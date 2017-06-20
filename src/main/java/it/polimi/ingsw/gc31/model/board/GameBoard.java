package it.polimi.ingsw.gc31.model.board;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Dice;
import it.polimi.ingsw.gc31.model.DiceColor;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.cards.CardParser;
import it.polimi.ingsw.gc31.model.parser.GameBoardParser;

public class GameBoard implements Serializable {

    private Map<CardColor,Tower> towers;
    private Map<DiceColor, Dice> dices;
    private Map<String, SpaceWrapper> boardSpaces;
    private GameInstance gameInstance;
    private transient GameBoardParser parser;
    private int positionIndex;
    private CardParser cardParser;

    public GameBoard(GameInstance gameInstance) {

        this.gameInstance = gameInstance;
        this.dices = new HashMap<>();
        this.boardSpaces = new HashMap<>();
        this.parser = new GameBoardParser("src/config/Settings.json", this);
        this.cardParser = new CardParser("src/config/Card.json");
        this.cardParser.parse();

        //Initialize dice
        createDice();

        setPositionIndex(1);

        // Initialize Towers on GameBoard
        this.towers = this.parser.parseTowers();

        for(Map.Entry<CardColor, Tower> towerEntry: towers.entrySet()) {

            Tower tower = towerEntry.getValue();
            tower.setDeck(this.cardParser.getCardsByColor(towerEntry.getKey()));
        }


        //Initialize CouncilsPalace
        boardSpaces.put("COUNCILS PALACE", parser.parseCouncilsPalace());
        incrementPositionIndex();

        //TODO Condizione  2-4 giocatori

        //Initialize Mart
        List<MartWrapper> marketZones = parser.parseMart();
        int index = 1;

        for(MartWrapper myMartWrapper : marketZones) {
            boardSpaces.put("MART #" + index, myMartWrapper);
            index++;
        }

        //Initialize Harvest & Production

        if(this.gameInstance.getNumOfPlayers() == 2) {
            this.initSpacesTwoPlayers();
            this.removeMart();
        } else if(this.gameInstance.getNumOfPlayers() == 3) {
            this.initSpacesThreePlayers();
            this.removeMart();
        } else {
            this.initSpacesThreePlayers();
        }

        setPositionIndex(1);
    }

    private void createDice() {
        for(DiceColor color: DiceColor.values()) {
            dices.put(color, new Dice(color));
        }
    }

    private void removeMart() {

        for(int i=2; i<5; i++) {
            boardSpaces.remove("MART #"+i);
        }
        return;
    }

    private void initSpacesThreePlayers() {
        boolean isMultiple = true;

        boardSpaces.put("PRODUCTION", parser.parseProductionZone(isMultiple));
        boardSpaces.put("HARVEST", parser.parseHarvestZone(isMultiple));
    }

    private void initSpacesTwoPlayers() {
        boolean isMultiple = false;

        boardSpaces.put("PRODUCTION", parser.parseProductionZone(isMultiple));
        boardSpaces.put("HARVEST", parser.parseHarvestZone(isMultiple));
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
