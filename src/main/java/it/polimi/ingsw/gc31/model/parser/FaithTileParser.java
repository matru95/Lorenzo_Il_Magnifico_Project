package it.polimi.ingsw.gc31.model.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.FaithTile;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
//TODO RANDOMIZZARE I FAITHTILE PARSER PER METTERLI SULLA BOARD IN MODO CASUALE. (CONTROLLARE SE CI SONO DELLE REGOLE)
public class FaithTileParser {
    private JsonNode rootNode;
    private transient Logger logger = Logger.getLogger(this.getClass().getName());
    private List<FaithTile> faithTiles;


    public FaithTileParser(String filePath){
        ObjectMapper mapper = new ObjectMapper();
        File jsonInputFile = new File(filePath);
        faithTiles= new ArrayList<>();

        try {
            rootNode = mapper.readTree(jsonInputFile);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "JSON file for FaithTiles not found");
        }
    }

    public void parse() {
        JsonNode faithTilesJSON= rootNode.path("faithTiles");
        for (JsonNode faithTile : faithTilesJSON){

            int id=faithTile.path("id").asInt();
            int age=faithTile.path("age").asInt();


            FaithTile faithTile1=new FaithTile(id,age);

            List <Resource> gainFewerStack;
            if(faithTile.has("gainFewer")){
                JsonNode gainFewer=faithTile.path("gainFewer");
                gainFewerStack=parseResources(gainFewer);


                faithTile1.setGainFewerStack(gainFewerStack);
            }else {
                gainFewerStack=null;
                faithTile1.setGainFewerStack(gainFewerStack);
            }

            int harvestFewer=faithTile.path("harvestFewer").asInt();
            faithTile1.setHarvestFewer(harvestFewer);


            int productionFewer=faithTile.path("productionFewer").asInt();
            faithTile1.setProductionFewer(productionFewer);

            int diceFewer=0;
            diceFewer=faithTile.path("diceFewer").asInt();
            faithTile1.setDiceFewer(diceFewer);


            if(faithTile.has("fewerDiceCard")){
                JsonNode fewerDiceCard= faithTile.path("fewerDiceCard");
                String cardColorString=fewerDiceCard.path("cardColor").asText().toUpperCase();

                CardColor cardColor=CardColor.valueOf(cardColorString);
                faithTile1.setFewerDiceCardColor(cardColor);

                int diceValue=fewerDiceCard.path("diceValue").asInt();
                faithTile1.setFewerDiceCardValue(diceValue);
            }

            boolean noMarket=faithTile.path("noMarket").asBoolean();
            faithTile1.setNoMarket(noMarket);

            boolean doubleServants=faithTile.path("doubleServants").asBoolean();
            faithTile1.setDoubleServants(doubleServants);

            boolean skipFirstRound=faithTile.path("skipFirstRound").asBoolean();
            faithTile1.setSkipFirstRound(skipFirstRound);

            if(faithTile.has("noEndGamePoints")){
                JsonNode noEndGamePoints=faithTile.path("noEndGamePoints");
                String cardColorString=noEndGamePoints.path("cardColor").asText().toUpperCase();
                CardColor cardColor=CardColor.valueOf(cardColorString);

                faithTile1.setNoEndGamePointsCardColor(cardColor);
            }
            List <Resource> forEveryRes;
            List <Resource> loseRes;
            if(faithTile.has("loseForEvery")){
                JsonNode loseForEvery=faithTile.path("loseForEvery");
                JsonNode forEvery=loseForEvery.path("for");
                JsonNode lose=loseForEvery.path("lost");

                forEveryRes=parseResources(forEvery);
                faithTile1.setForEveryRes(forEveryRes);

                loseRes=parseResources(lose);
                faithTile1.setLoseRes(loseRes);
            }
            if(faithTile.has("loseForEveryCost")){
                JsonNode loseForEveryCost=faithTile.path("loseForEveryCost");
                String cardColorString=loseForEveryCost.path("cardColor").asText().toUpperCase();
                JsonNode cardType=loseForEveryCost.path("cardTypeCost");
                List <Resource> cardTypeCost=parseResources(cardType);

                CardColor cardColor=CardColor.valueOf(cardColorString);
                faithTile1.setLoseForEveryCostCardColor(cardColor);
                faithTile1.setLoseForEveryCost(cardTypeCost);
            }
            boolean loseForEveryResource=faithTile.path("loseForEveryResource").asBoolean();


            faithTile1.setLoseForEveryResource(loseForEveryResource);
            this.faithTiles.add(faithTile1);
        }
    }
    private List<Resource> parseResources(JsonNode node) {
        List<Resource> exchangeResources = new ArrayList<>();

        node.fields().forEachRemaining(currentResource -> {
            String resourceNameString = currentResource.getKey();
            ResourceName resourceName = ResourceName.valueOf(resourceNameString.toUpperCase());
            int amount = currentResource.getValue().asInt();

            exchangeResources.add(new Resource(resourceName, amount));
        });

        return exchangeResources;
    }
    public List<FaithTile> getTilesByAge() {
        List<FaithTile> firstAgeTiles = new ArrayList<>();
        List<FaithTile> secondAgeTiles = new ArrayList<>();
        List<FaithTile> thirdAgeTiles = new ArrayList<>();

        for(FaithTile faithTile: faithTiles) {
            if(faithTile.getAge() == 1) {

                firstAgeTiles.add(faithTile);
            } else if(faithTile.getAge() == 2) {

                secondAgeTiles.add(faithTile);
            } else {

                thirdAgeTiles.add(faithTile);
            }

        }
        long seed = System.nanoTime();
        Collections.shuffle(firstAgeTiles, new Random(seed));
        Collections.shuffle(secondAgeTiles, new Random(seed));
        Collections.shuffle(thirdAgeTiles, new Random(seed));

        FaithTile firstAgeTile=firstAgeTiles.get(0);
        FaithTile secondAgeTile=secondAgeTiles.get(0);
        FaithTile thirdAgeTile=thirdAgeTiles.get(0);

        List<FaithTile> inGameFaithTiles= new ArrayList<>();
        inGameFaithTiles.add(firstAgeTile);
        inGameFaithTiles.add(secondAgeTile);
        inGameFaithTiles.add(thirdAgeTile);
        return inGameFaithTiles;
    }
    public List<FaithTile> getFaithTiles() {
        return faithTiles;
    }
}
