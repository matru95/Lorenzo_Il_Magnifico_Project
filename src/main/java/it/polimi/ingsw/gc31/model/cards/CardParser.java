package it.polimi.ingsw.gc31.model.cards;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardParser {
    private JsonNode rootNode;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private List<Card> cards;

    public CardParser(String filePath){
        ObjectMapper mapper =new ObjectMapper();
        File jsonInputFile = new File(filePath);
        cards = new ArrayList<>();

        try {
            rootNode = mapper.readTree(jsonInputFile);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "JSON file for cards not found");
        }

    }

    public void parse() {
        JsonNode cardsJSON = rootNode.path("cards");

        for(JsonNode cardJSON: cardsJSON) {
            String cardColorString = cardJSON.path("cardColor").asText();
            CardColor cardColor = CardColor.valueOf(cardColorString.toUpperCase().trim());
            String cardName = cardJSON.path("title").asText();
            int cardID = cardJSON.path("id").asInt();
            int cardAge = cardJSON.path("cardAge").asInt();

            Card card = new Card(cardColor, cardName, cardID, cardAge);

            switch (cardColorString) {
                case "green":
                   parseGreenCard(cardJSON, card);
                   break;
                case "yellow":
                    parseYellowCard(cardJSON, card);
                    break;
                case "blue":
                    parseBlueCard(cardJSON, card);
                    break;
                case "purple":
                    parsePurpleCard(cardJSON, card);
                    break;
                default:
                    return;
            }
            this.cards.add(card);
        }
    }

    private void parseGreenCard(JsonNode cardJSON, Card card){
        int activationValue = cardJSON.path("activationValue").asInt();
        card.setActivationValue(activationValue);

        JsonNode instantEffectNode = cardJSON.path("instantEffect").path("bonus");
        List<Resource> instantEffectResources = initEffectResources(instantEffectNode);

        JsonNode normalEffectResourcesNode = cardJSON.path("normalEffect").path("bonus");
        List<Resource> normalEffectResources = initEffectResources(normalEffectResourcesNode);

        int numOfInstantParchment = getNumOfParchment(cardJSON.path("instantEffect"));
        int numOfNormalParchment = getNumOfParchment(cardJSON.path("normalEffect"));

        card.setInstantEffectResources(instantEffectResources);
        card.setNormalEffectResources(normalEffectResources);

        card.setNumOfInstantParchment(numOfInstantParchment);
        card.setNumOfNormalParchment(numOfNormalParchment);
    }

    private List<Resource> initEffectResources(JsonNode effectResourceNode) {
        List<Resource> effectResources = new ArrayList<>();
        while (effectResourceNode.fields().hasNext()) {
            String resourceNameString = effectResourceNode.fields().next().getKey().toString();
            ResourceName resourceName = ResourceName.valueOf(resourceNameString.toUpperCase());
            int resourceAmount = effectResourceNode.fields().next().getValue().asInt();

            effectResources.add(new Resource(resourceName, resourceAmount));
            effectResourceNode = effectResourceNode.fields().next().getValue();
        }

        return effectResources;
    }

    private int getNumOfParchment(JsonNode effect) {
        if(effect.has("parchment")) {
            return effect.path("parchment").asInt();
        }

        return 0;
    }

    private void parsePurpleCard(JsonNode cardJSON, Card card) {
        return;
    }

    private void parseBlueCard(JsonNode cardJSON, Card card) {
        return;
    }

    private void parseYellowCard(JsonNode cardJSON, Card card) {
        return;
    }


    public List<Card> getCards() {
        return cards;
    }

}
