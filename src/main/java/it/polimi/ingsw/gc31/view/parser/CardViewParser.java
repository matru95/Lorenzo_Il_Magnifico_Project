package it.polimi.ingsw.gc31.view.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        String result = "{";

        for(JsonNode singleCostNode: costsNode) {
            List<String> singleCostResult = new ArrayList<>();

            singleCostNode.fields().forEachRemaining(field -> {
                String resourceName = field.getKey();
                int amount = field.getValue().asInt();

                singleCostResult.add(resourceName + ": " + amount);
            });

            result += singleCostResult.toString();
        }
        result += "}";

        return "cost: "+result;
    }
}
