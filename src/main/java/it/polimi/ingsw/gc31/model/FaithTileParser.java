package it.polimi.ingsw.gc31.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    //TODO CREARE METODI SET PER IL FAITHOBJ E SETTARLI DAL PARSER.
    public void parse() {
        JsonNode faithtilesJSON= rootNode.path("faithtiles");
        for (JsonNode faithTile : faithtilesJSON){

            int id=faithTile.path("id").asInt();
            int age=faithTile.path("age").asInt();
            FaithTile faithTile1=new FaithTile(id,age);

            List <Resource> gainFewerStack=new ArrayList<>();
            if(faithTile.has("gainfewer")){
                JsonNode gainFewer=faithTile.path("gainfewer");
                gainFewerStack=parseResources(gainFewer);
                faithTile1.setGainFewerStack(gainFewerStack);

            }else {
                gainFewerStack=null;
                faithTile1.setGainFewerStack(gainFewerStack);
            }

            int harvestFewer=faithTile.path("harvestfewer").asInt();
            faithTile1.setHarvestFewer(harvestFewer);

            int profuctionFewer=faithTile.path("productionfewer").asInt();
            faithTile1.setProfuctionFewer(profuctionFewer);

            int diceFewer=0;
            diceFewer=faithTile.path("dicefewer").asInt();
            faithTile1.setDiceFewer(diceFewer);

            if(faithTile.has("fewerdicecard")){
                JsonNode fewerDiceCard= faithTile.path("fewerdicecard");
                String cardColorString=fewerDiceCard.path("cardcolor").asText().toUpperCase();

                CardColor cardColor=CardColor.valueOf(cardColorString);
                faithTile1.setFewerdicecardcolor(cardColor);

                int diceValue=fewerDiceCard.path("dicevalue").asInt();
                faithTile1.setFewerDiceCardValue(diceValue);
            }

            boolean noMarket=faithTile.path("nomarket").asBoolean();
            faithTile1.setNoMarket(noMarket);

            boolean doubleServants=faithTile.path("doubleservants").asBoolean();
            faithTile1.setDoubleServants(doubleServants);

            boolean skipFirstRound=faithTile.path("skipfirstround").asBoolean();
            faithTile1.setSkipFirstRound(skipFirstRound);

            if(faithTile.has("noendgamepoints")){
                JsonNode noEndGamePoints=faithTile.path("noendgamepoints");
                String cardColorString=noEndGamePoints.path("cardcolor").asText().toUpperCase();

                CardColor cardColor=CardColor.valueOf(cardColorString);
                faithTile1.setNoEndGamePointsCardColor(cardColor);
            }
            List <Resource> forEveryRes=new ArrayList<>();
            List <Resource> loseRes=new ArrayList<>();
            if(faithTile.has("loseforevery")){
                JsonNode loseForEvery=faithTile.path("loseforevery");
                JsonNode forEvery=loseForEvery.path("for");
                JsonNode lose=loseForEvery.path("lose");

                forEveryRes=parseResources(forEvery);
                faithTile1.setForEveryRes(forEveryRes);

                loseRes=parseResources(lose);
                faithTile1.setLoseRes(loseRes);
            }
            if(faithTile.has("loseforeverycost")){
                JsonNode loseForEveryCost=faithTile.path("loseforeverycost");
                String cardColorString=loseForEveryCost.path("cardcolor").asText().toUpperCase();
                CardColor cardColor=CardColor.valueOf(cardColorString);
                faithTile1.setLoseForEveryCostCardColor(cardColor);
            }
            boolean loseForEveryResource=faithTile.path("loseforeveryresource").asBoolean();
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
    public List<FaithTile> getFaithTiles() {
        return faithTiles;
    }
}
