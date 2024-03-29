package it.polimi.ingsw.gc31.model.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FaithPointParser {
    private JsonNode rootNode;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Map<Integer,Integer> faithPoints;

    public FaithPointParser(String filePath){
        ObjectMapper mapper = new ObjectMapper();
        File jsonInputFile = new File(filePath);
        faithPoints = new HashMap<>();

        try {
            rootNode = mapper.readTree(jsonInputFile);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "JSON file for faithPoints not found");
        }

    }
    public Map<Integer, Integer> parse() {
        JsonNode faithPointsJSON= rootNode.path("faithPoints");

        for(JsonNode faithPoint: faithPointsJSON){
            JsonNode id=faithPoint.path("id");
            JsonNode victoryPoints=faithPoint.path("victoryPoints");
            this.faithPoints.put(id.asInt(),victoryPoints.asInt());
        }
        return faithPoints;
    }

}
