package it.polimi.ingsw.gc31;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.model.FaithTile;
import it.polimi.ingsw.gc31.model.parser.FaithTileParser;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FaithTileParserTest extends TestCase {
    FaithTileParser parser;
    Logger logger = Logger.getLogger(this.getClass().getName());
    List<FaithTile> faithTiles;
    @Override
    public void setUp() {
        this.parser = new FaithTileParser("src/config/FaithTile.json");
        parser.parse();
        this.faithTiles = parser.getFaithTiles();
    }
    @Override
    public void tearDown() {
        this.parser = null;
        this.faithTiles = null;
    }
    @Test
    public void testFaithTileParserShouldParseFaithTile() {
        ObjectMapper mapper =new ObjectMapper();
        File jsonInputFile = new File("src/config/FaithTile.json");
        int lengthTilesJson = 21;

        try {
            JsonNode rootNode = mapper.readTree(jsonInputFile);
            JsonNode faithtileNode = rootNode.path("faithtiles");
            for(JsonNode faith: faithtileNode) {
                lengthTilesJson++;
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "JSON file for FaithTile not found");
        }

        assertEquals(lengthTilesJson, faithTiles.size());
    }
}

