package it.polimi.ingsw.gc31.view.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CardViewParser implements ViewParser{

    public CardViewParser() {
    }

    @Override
    public String parse(String JSONString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode;
        String result;

        try {
            rootNode = mapper.readTree(JSONString);
            result = createString(rootNode);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String createString(JsonNode rootNode) {
        String cardName = rootNode.path("cardName").toString();
        String cardID = rootNode.path("cardID").toString();

        JsonNode costsNode = rootNode.path("cost");
        String costs = createCostString(costsNode);

        return costs + " | "  + cardName + "|" + cardID;
    }

    private String createCostString(JsonNode costsNode) {
        String result = "";

        for(JsonNode singleCostNode: costsNode) {
            String costName = singleCostNode.fieldNames().next();
            String amount = singleCostNode.path(costName).path("numOf").toString();

            result = result + "[" + costName + ": " + amount + "]";
        }

        return "{"+result+"}";
    }
}
