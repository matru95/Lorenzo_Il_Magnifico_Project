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
            List <Resource> gainFewerStack=new ArrayList<>();
            if(faithTile.has("gainfewer")){
                JsonNode gainFewer=faithTile.path("gainfewer");
                gainFewerStack=parseResources(gainFewer);
            }else {
                gainFewerStack=null;
            }
            int harvestFewer=faithTile.path("harvestfewer").asInt();

            int profuctionFewer=faithTile.path("productionfewer").asInt();
            int diceFewer=0;
            diceFewer=faithTile.path("dicefewer").asInt();
            if(faithTile.has("fewerdicecard")){
                JsonNode fewerDiceCard= faithTile.path("fewerdicecard");
                String cardColorString=fewerDiceCard.path("cardcolor").asText().toUpperCase();
                CardColor cardColor=CardColor.valueOf(cardColorString);
                int diceValue=fewerDiceCard.path("dicevalue").asInt();
            }
            boolean noMarket=faithTile.path("nomarket").asBoolean();
            boolean doubleServants=faithTile.path("doubleservants").asBoolean();
            boolean skipFirstRound=faithTile.path("skipfirstround").asBoolean();
            if(faithTile.has("noendgamepoints")){
                JsonNode noEndGamePoints=faithTile.path("noendgamepoints");
                String cardColorString=noEndGamePoints.path("cardcolor").asText().toUpperCase();
                CardColor cardColor=CardColor.valueOf(cardColorString);
            }
            List <Resource> forEveryRes=new ArrayList<>();
            List <Resource> loseRes=new ArrayList<>();
            if(faithTile.has("loseforevery")){
                JsonNode loseForEvery=faithTile.path("loseforevery");
                JsonNode forEvery=loseForEvery.path("for");
                JsonNode lose=loseForEvery.path("lose");
                forEveryRes=parseResources(forEvery);
                loseRes=parseResources(lose);
            }
            if(faithTile.has("loseforeverycost")){
                JsonNode loseForEveryCost=faithTile.path("loseforeverycost");
                String cardColorString=loseForEveryCost.path("cardcolor").asText().toUpperCase();
                CardColor cardColor=CardColor.valueOf(cardColorString);
            }
            boolean loseForEveryResource=faithTile.path("loseforeveryresource").asBoolean();
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
}
