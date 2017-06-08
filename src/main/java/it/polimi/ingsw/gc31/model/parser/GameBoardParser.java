package it.polimi.ingsw.gc31.model.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.Tower;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;

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
            this.rootNode = mapper.readTree(jsonInputFile);
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
}
