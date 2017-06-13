package it.polimi.ingsw.gc31.model.cards;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
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

        this.setEffectResources(cardJSON, card);
        this.setParchments(cardJSON, card);
    }

    private void parseYellowCard(JsonNode cardJSON, Card card) {
//      Cost
        JsonNode costsNode = cardJSON.path("cost");
        parseCost(costsNode, card);


        int activationValue = cardJSON.path("activationValue").asInt();
        card.setActivationValue(activationValue);

        this.setEffectResources(cardJSON, card);
        this.setParchments(cardJSON, card);

        JsonNode normalEffectNode = cardJSON.path("normalEffect");

//      Normal effect with multiplier
        if(normalEffectNode.has("multiplier")) {
            Map<String, Object> multiplier = new HashMap<>();

            parseMultiplier(normalEffectNode, multiplier);
            card.setMultiplier(multiplier);
        }

//      Normal effect with exchange
        if (normalEffectNode.has("exchange")) {
            JsonNode exchangeNodes = normalEffectNode.path("exchange");

            for(JsonNode singleExchangeNode: exchangeNodes) {
                Exchange myExchange = new Exchange();

//              Parse the give part
                JsonNode giveNode = singleExchangeNode.path("give");
                myExchange.setResourcesToGive(parseExchangeResources(giveNode));

//              Parse the receive part
                JsonNode receiveNode = singleExchangeNode.path("receive");
                myExchange.setResourcesToReceive(parseExchangeResources(receiveNode));

                card.insertExchange(myExchange);
            }
        }

    }


    private List<Resource> parseExchangeResources(JsonNode node) {
        List<Resource> exchangeResources = new ArrayList<>();

        node.fields().forEachRemaining(currentResource -> {
            String resourceNameString = currentResource.getKey();
            ResourceName resourceName = ResourceName.valueOf(resourceNameString.toUpperCase());
            int amount = currentResource.getValue().asInt();

            exchangeResources.add(new Resource(resourceName, amount));
        });

        return exchangeResources;
    }

    private void parseCost(JsonNode costsNode, Card card) {
        List<Map<ResourceName, Resource>> cost = new ArrayList<>();

        for(JsonNode costNode: costsNode) {
            Map<ResourceName, Resource> singleCost = new HashMap<>();

            costNode.fields().forEachRemaining(currentCostResource -> {
                String resourceNameString = currentCostResource.getKey();
                ResourceName resourceName = ResourceName.valueOf(resourceNameString.toUpperCase());
                int amount = currentCostResource.getValue().asInt();

                singleCost.put(resourceName, new Resource(resourceName, amount));
            });
            cost.add(singleCost);
        }
        card.setCost(cost);
    }

    private void parseMultiplier(JsonNode effectNode, Map<String, Object> multiplier) {
//      Receive part
        JsonNode receiveNode = effectNode.path("multiplier").path("receive");
        String resourceNameString = receiveNode.fields().next().getKey();
        ResourceName resourceName = ResourceName.valueOf(resourceNameString.toUpperCase());
        int amountToReceive = receiveNode.path(resourceNameString).asInt();

        Resource resourceToReceive = new Resource(resourceName, amountToReceive);
        multiplier.put(resourceNameString.toUpperCase(), resourceToReceive);

//      For part
        JsonNode forNode = effectNode.path("multiplier").path("for");
        String cardColorString = forNode.path("color").asText();

        CardColor cardColor = CardColor.valueOf(cardColorString.toUpperCase());
        multiplier.put("color", cardColor);

    }

    private void setEffectResources(JsonNode cardJSON, Card card) {
        JsonNode instantEffectNode = cardJSON.path("instantEffect").path("bonus");
        JsonNode normalEffectResourcesNode = cardJSON.path("normalEffect").path("bonus");

        List<Resource> normalEffectResources = initEffectResources(normalEffectResourcesNode);
        List<Resource> instantEffectResources = initEffectResources(instantEffectNode);

        card.setInstantEffectResources(instantEffectResources);
        card.setNormalEffectResources(normalEffectResources);
    }

    private void setParchments(JsonNode cardJSON, Card card) {
        int numOfInstantParchment = getNumOfParchment(cardJSON.path("instantEffect"));
        int numOfNormalParchment = getNumOfParchment(cardJSON.path("normalEffect"));


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



    public List<Card> getCards() {
        return cards;
    }

}
