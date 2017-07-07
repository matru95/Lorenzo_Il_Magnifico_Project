package it.polimi.ingsw.gc31.model.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SettingsParser {
    private ObjectMapper mapper;
    private JsonNode rootNode;

    public SettingsParser(String filePath) {
        File inputFile = new File(filePath);
        mapper = new ObjectMapper();

        try {
            rootNode = mapper.readTree(inputFile).path("gameSettings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getServerWaitTime() {
        int serverWaitTime = rootNode.path("serverWait").asInt();

        return serverWaitTime * 1000;
    }

    public int getPlayerWaitTime() {
        int playerWaitTime = rootNode.path("playerWait").asInt();

        return playerWaitTime * 1000;
    }
}
