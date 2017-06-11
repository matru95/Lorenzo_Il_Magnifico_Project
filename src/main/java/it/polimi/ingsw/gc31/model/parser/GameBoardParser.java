package it.polimi.ingsw.gc31.model.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.model.board.*;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameBoardParser {
    private String fileLocation;
    private JsonNode rootNode;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private GameBoard gameBoard;

    public GameBoardParser(String fileLocation, GameBoard gameBoard) {
        this.fileLocation = fileLocation;
        this.gameBoard = gameBoard;

        ObjectMapper mapper = new ObjectMapper();
        File jsonInputFile = new File(this.fileLocation);
        try {
            this.rootNode = mapper.readTree(jsonInputFile).path("gameBoard");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Config file not found");
        }
    }

    public Map<CardColor, Tower> parseTowers() {
        Map<CardColor, Tower> towers = new HashMap<>();
        List<String> cardColorsString = new ArrayList<>();

        JsonNode towersJson = rootNode.path("towers");

        for(CardColor cardColor: CardColor.values()) {
            cardColorsString.add(cardColor.toString().toLowerCase());
        }

        for(String cardColorString: cardColorsString) {
            JsonNode currentTower = towersJson.path(cardColorString);
            Tower tower = new Tower(CardColor.valueOf(cardColorString.toUpperCase()), this.gameBoard, currentTower);
            towers.put(CardColor.valueOf(cardColorString.toUpperCase()), tower);
        }

        return towers;
    }

    public ProductionWrapper parseProductionZone(boolean isMultiple) {
        JsonNode productionZoneJson = rootNode.path("productionZone");

        JsonNode singleProductionZoneJson = productionZoneJson.path("single");
        JsonNode multipleProductionZoneJson = productionZoneJson.path("multiple");

        if(!isMultiple) {
            int positionID = singleProductionZoneJson.path("positionID").asInt();
            int diceBond = singleProductionZoneJson.path("diceBond").asInt();

            return new ProductionWrapper(positionID, diceBond, isMultiple, this.gameBoard);
        } else {
            int positionID = multipleProductionZoneJson.path("positionID").asInt();
            int diceBond = multipleProductionZoneJson.path("diceBond").asInt();
            int malus = multipleProductionZoneJson.path("malus").asInt();

            ProductionWrapper multipleProductionWrapper = new ProductionWrapper(positionID, diceBond, isMultiple, this.gameBoard);
            multipleProductionWrapper.setMalus(malus);

            return multipleProductionWrapper;
        }
    }

    public HarvestWrapper parseHarvestZone(boolean isMultiple) {
        JsonNode harvestZoneJson = rootNode.path("harvestZone");

        JsonNode singleHarvestZone = harvestZoneJson.path("single");
        JsonNode multipleHarvestZone = harvestZoneJson.path("multiple");

        if(!isMultiple) {
            int positionID = singleHarvestZone.path("positionID").asInt();
            int diceBond = singleHarvestZone.path("diceBond").asInt();

            return new HarvestWrapper(positionID, diceBond, isMultiple, this.gameBoard);
        } else {
            int positionID = multipleHarvestZone.path("positionID").asInt();
            int diceBond = multipleHarvestZone.path("diceBond").asInt();
            int malus = multipleHarvestZone.path("malus").asInt();

            HarvestWrapper multipleHarvestWrapper = new HarvestWrapper(positionID, diceBond, isMultiple, this.gameBoard);
            multipleHarvestWrapper.setMalus(malus);

            return multipleHarvestWrapper;
        }
    }

    public List<MartWrapper> parseMart() {
        int numOfPlayers = this.gameBoard.getGameInstance().getNumOfPlayers();
        List<MartWrapper> martZones = new ArrayList<>();
        JsonNode martZoneJson = rootNode.path("martZone");

        for(JsonNode singleMartZoneJson : martZoneJson){

            if(numOfPlayers > singleMartZoneJson.path("playerNumberRestriction").asInt()) {
                int positionID = singleMartZoneJson.path("positionID").asInt();
                JsonNode bonus = singleMartZoneJson.path("bonus");
                int diceBond = singleMartZoneJson.path("diceBond").asInt();
                List<Resource> resources = new ArrayList<>();

                while(bonus.fields().hasNext()) {
                    ResourceName bonusName = ResourceName.valueOf(bonus.fields().next().getKey().toUpperCase());
                    int amount = bonus.fields().next().getValue().asInt();
                    resources.add(new Resource(bonusName, amount));
                }

                MartWrapper myMartWrapper = new MartWrapper(positionID, diceBond, this.gameBoard, resources);
                martZones.add(myMartWrapper);
            }
        }

        return martZones;
    }

    public CouncilsPalaceWrapper parseCouncilsPalace() {
        JsonNode councilsPalaceJson = rootNode.path("councilsPalace");
        int positionID = councilsPalaceJson.path("positionID").asInt();
        int diceBond = councilsPalaceJson.path("diceBond").asInt();
        JsonNode bonusJson = councilsPalaceJson.path("bonus");
        String bonusName = bonusJson.fieldNames().next().toString();
        int amount = bonusJson.path(bonusName).asInt();

        Resource res = new Resource(ResourceName.valueOf(bonusName.toUpperCase()), amount);

        return new CouncilsPalaceWrapper(positionID, diceBond, this.gameBoard, res);
    }
}
