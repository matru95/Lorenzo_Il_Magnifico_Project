package it.polimi.ingsw.gc31.model.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.model.FaithTile;
import it.polimi.ingsw.gc31.model.parser.FaithTileParser;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Dice;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.parser.CardParser;
import it.polimi.ingsw.gc31.model.parser.GameBoardParser;

public class GameBoard implements Serializable {

    private Map<CardColor,Tower> towers;
    private Map<DiceColor, Dice> dices;
    private Map<Integer, SpaceWrapper> boardSpaces;
    private Map<Integer, FaithTile> church;
    private GameInstance gameInstance;
    private transient GameBoardParser parser;
    private CardParser cardParser;
    private FaithTileParser faithTileParser;
    public GameBoard(GameInstance gameInstance) {

        this.gameInstance = gameInstance;
        this.dices = new HashMap<>();
        this.boardSpaces = new HashMap<>();
        this.parser = new GameBoardParser("src/config/Settings.json", this);
        this.cardParser = new CardParser("src/config/Card.json");
        this.cardParser.parse();
        this.faithTileParser= new FaithTileParser("src/config/FaithTile.json");
        //Initialize dice
        createDice();

        // Initialize Towers on GameBoard
        this.towers = this.parser.parseTowers();

        for(Map.Entry<CardColor, Tower> towerEntry: towers.entrySet()) {

            Tower tower = towerEntry.getValue();
            tower.setDeck(this.cardParser.getCardsByColor(towerEntry.getKey()));
        }

        //Initialize Harvest & Production
        if(this.gameInstance.getNumOfPlayers() == 2) {
            this.initHarvestAndProduction(false);
        } else if(this.gameInstance.getNumOfPlayers() == 3) {
            this.initHarvestAndProduction(true);
        } else {
            this.initHarvestAndProduction(true);
        }

        //Initialize CouncilsPalace
        CouncilsPalaceWrapper councilsPalaceWrapper = parser.parseCouncilsPalace();
        boardSpaces.put(councilsPalaceWrapper.getPositionID(), councilsPalaceWrapper);

        //Initialize Mart
        List<MartWrapper> marketZones = parser.parseMart();

        for(MartWrapper myMartWrapper : marketZones) {
            boardSpaces.put(myMartWrapper.getPositionID(), myMartWrapper);
        }

        //Inizialize FaitTiles
        this.church=new HashMap<>();
        faithTileParser.parse();
        List <FaithTile> inGameFaithTiles=faithTileParser.getTilesByAge();
        int i=0;
        for (i=0;i<=2;i++){
            this.church.put(i,inGameFaithTiles.get(i));
        }

    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode gameBoardNode = mapper.createObjectNode();

        gameBoardNode.set("towers", createTowersJson(mapper));
        gameBoardNode.set("dices", createDicesJson(mapper));
        gameBoardNode.set("boardSpaces", createBoardSpacesJson(mapper));

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(gameBoardNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }


    }

    private ObjectNode createTowersJson(ObjectMapper mapper) {
        ObjectNode towersNode = mapper.createObjectNode();

        for(Map.Entry<CardColor, Tower> tower: towers.entrySet()) {
            towersNode.set(tower.getKey().toString(), tower.getValue().toJson());
        }

        return towersNode;
    }

    private ObjectNode createDicesJson(ObjectMapper mapper) {
        ObjectNode dicesNode = mapper.createObjectNode();

        for(Map.Entry<DiceColor, Dice> dice: dices.entrySet()) {
            dicesNode.put(dice.getKey().toString(), dice.getValue().getValue());
        }

        return dicesNode;
    }

    private ObjectNode createBoardSpacesJson(ObjectMapper mapper) {
        ObjectNode boardSpacesJson = mapper.createObjectNode();

        for(Map.Entry<Integer, SpaceWrapper> boardSpace: boardSpaces.entrySet()) {
            boardSpacesJson.set(boardSpace.getKey().toString(), boardSpace.getValue().toJson());
        }

        return boardSpacesJson;
    }

    private void createDice() {
        for(DiceColor color: DiceColor.values()) {
            dices.put(color, new Dice(color));
        }
    }

    private void initHarvestAndProduction(boolean isMultiple) {
        ProductionWrapper productionWrapper = parser.parseProductionZone(isMultiple);
        HarvestWrapper harvestWrapper = parser.parseHarvestZone(isMultiple);

        boardSpaces.put(productionWrapper.getPositionID(), productionWrapper);
        boardSpaces.put(harvestWrapper.getPositionID(), harvestWrapper);
    }

    public Map<DiceColor, Dice> getDices() {
        return dices;
    }

    public Dice getDiceByColor(DiceColor diceColor) {
        return dices.get(diceColor);
    }

    public Map<String, SpaceWrapper> getOpenSpaces() {

    	Map availablePlaces = new HashMap<String, SpaceWrapper>();

    	for(Map.Entry<Integer,SpaceWrapper> entry: boardSpaces.entrySet()) {
            if (!entry.getValue().isOccupied()) {
                availablePlaces.put(entry.getKey(), entry.getValue());
            }
        }
    	return availablePlaces;
    }

    public Map<Integer, SpaceWrapper> getBoardSpaces() {
        return boardSpaces;
    }

    public List<SpaceWrapper> getAllSpaces() {
        List<SpaceWrapper> allSpaces = new ArrayList<>();

        // Insert all tower spaces in the list
        for(Map.Entry<CardColor, Tower> tower: towers.entrySet()) {
            Tower singleTower = tower.getValue();

            singleTower.getTowerSpaces().forEach(towerSpace -> {
                allSpaces.add(towerSpace);
            });
        }

        for(Map.Entry<Integer, SpaceWrapper> boardSpace: boardSpaces.entrySet()) {
            allSpaces.add(boardSpace.getValue());
        }

        return allSpaces;
    }

    public SpaceWrapper getSpaceById(int positionID) {
        List<SpaceWrapper> allSpaces = getAllSpaces();
        SpaceWrapper position = allSpaces.stream().filter(positionIter ->
                positionIter.getPositionID() == positionID).findFirst().orElse(null);

        return position;
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
