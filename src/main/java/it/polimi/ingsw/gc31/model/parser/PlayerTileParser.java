package it.polimi.ingsw.gc31.model.parser;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.model.PlayerTile;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerTileParser {
    private JsonNode rootNode;
    private transient Logger logger = Logger.getLogger(this.getClass().getName());
    private List<PlayerTile> playerTiles;

    public PlayerTileParser(String filePath){
        ObjectMapper mapper =new ObjectMapper();
        File jsonInputFile = new File(filePath);
        playerTiles = new ArrayList<>();

        try {
            rootNode = mapper.readTree(jsonInputFile);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "JSON file for playerTiles not found");
        }

    }

    public void parse(){
        JsonNode playerTilesJSON = rootNode.path("playertiles");
        List<Resource> harvest,production;
        for(JsonNode playerTile : playerTilesJSON){
            JsonNode idNode=playerTile.path("id");
            int id= idNode.asInt();
            JsonNode harvestNode=playerTile.path("harvest");
            harvest=parseResources(harvestNode);
            JsonNode productionNode=playerTile.path("production");
            production=parseResources(productionNode);
            PlayerTile playerTileCurrent= new PlayerTile(harvest,production,id);
            this.playerTiles.add(playerTileCurrent);
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
    public List<PlayerTile> getPlayerTiles() {
        return playerTiles;
    }
}
