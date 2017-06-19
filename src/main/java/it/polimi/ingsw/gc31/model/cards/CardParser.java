package it.polimi.ingsw.gc31.model.cards;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardParser {
    private JsonNode rootNode;
    private transient Logger logger = Logger.getLogger(this.getClass().getName());
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
            card.setNormalMultiplier(multiplier);
        }

//      Normal effect with exchange
        if (normalEffectNode.has("exchange")) {
            JsonNode exchangeNodes = normalEffectNode.path("exchange");

            for(JsonNode singleExchangeNode: exchangeNodes) {
                Exchange myExchange = new Exchange();

//              Parse the give part
                JsonNode giveNode = singleExchangeNode.path("give");
                myExchange.setResourcesToGive(parseResources(giveNode));

//              Parse the receive part
                JsonNode receiveNode = singleExchangeNode.path("receive");

//              Parchments
                int numOfParchments = receiveNode.path("parchment").asInt();

                myExchange.setNumOfParchmentsToReceive(numOfParchments);
                myExchange.setResourcesToReceive(parseResources(receiveNode.path("resources")));

                card.insertExchange(myExchange);
            }
        }

    }

    private void parseBlueCard(JsonNode cardJSON, Card card) {
//      Cost
        JsonNode costsNode = cardJSON.path("cost");
        parseCost(costsNode, card);

        this.setEffectResources(cardJSON, card);
        this.setParchments(cardJSON, card);

        //      Free card choice
        JsonNode instantEffectNode = cardJSON.path("instantEffect");

        if(instantEffectNode.has("freeCardChoice")) {
            JsonNode freeCardChoiceNode = instantEffectNode.path("freeCardChoice");

            parseFreeCardChoice(freeCardChoiceNode, card);
        }

        JsonNode normalEffectNode = cardJSON.path("normalEffect");
//      Permanent effect
        if (normalEffectNode.has("permanent")) {
            JsonNode permanentEffectNode = normalEffectNode.path("permanent");
            parsePermanentEffect(permanentEffectNode, card);
        }

//      Multiplier
        if(instantEffectNode.has("multiplier")) {
            Map<String, Object> multiplier = new HashMap<>();
            parseMultiplier(instantEffectNode, multiplier);

            card.setInstantMultiplier(multiplier);
        }

//      Instant production or harvest bonus
        if(instantEffectNode.has("productionOrHarvestBonus")) {
            JsonNode productionOrHarvestBonusNode = instantEffectNode.path("productionOrHarvestBonus");

            parseProductionOrHarvestBonus(productionOrHarvestBonusNode, card, "instant");
        }

    }

    private void parsePurpleCard(JsonNode cardJSON, Card card) {
//      Cost
        JsonNode costsNode = cardJSON.path("cost");
        parseCost(costsNode, card);

//      Cost bond
        this.parseCostBond(cardJSON, card);

        this.setEffectResources(cardJSON, card);
        this.setParchments(cardJSON, card);

//      Free card choice
        JsonNode instantEffectNode = cardJSON.path("instantEffect");

        if(instantEffectNode.has("freeCardChoice")) {
            JsonNode freeCardChoiceNode = instantEffectNode.path("freeCardChoice");

            parseFreeCardChoice(freeCardChoiceNode, card);
        }

//      Harvest or production action
        if(instantEffectNode.has("productionOrHarvestAction")) {
            JsonNode actionNode = instantEffectNode.path("productionOrHarvestAction");

            String actionType = actionNode.fieldNames().next();
            int value = actionNode.path(actionType).asInt();

            if(actionType == "production") {

                card.setProductionAction(value);
            } else {

                card.setHarvestAction(value);
            }
        }


    }

    private void parseCostBond(JsonNode cardJSON, Card card) {
        if(cardJSON.has("costBond")) {
            JsonNode costBondNode = cardJSON.path("costBond");

            Resource costBond = parseSingleResource(costBondNode);
            card.setCostBond(costBond);
        }
    }

    private void parseFreeCardChoice(JsonNode freeCardChoiceNode, Card card) {
        FreeCardChoice freeCardChoice = new FreeCardChoice();

        String colorName = freeCardChoiceNode.path("cardColor").asText();
        int points = freeCardChoiceNode.path("points").asInt();

        if(colorName != "") {
            freeCardChoice.setCardColor(CardColor.valueOf(colorName.toUpperCase()));
        }

        freeCardChoice.setPoints(points);

//          Parse resources
        if(freeCardChoiceNode.has("resources")) {
            JsonNode resourcesNode = freeCardChoiceNode.path("resources");

            List<Resource> resources = parseResources(resourcesNode);
            freeCardChoice.setResources(resources);
        }

        card.setFreeCardChoice(freeCardChoice);
    }

    private void parsePermanentEffect(JsonNode permanentEffectNode, Card card) {
//          Card color bonus
        if(permanentEffectNode.has("cardColorBonus")) {
            JsonNode cardColorBonusNode = permanentEffectNode.path("cardColorBonus");

            parseCardColorBonus(cardColorBonusNode, card);
        } else if (permanentEffectNode.has("productionOrHarvestBonus")) {

            parseProductionOrHarvestBonus(permanentEffectNode.path("productionOrHarvestBonus"), card, "normal");
        } else if (permanentEffectNode.has("blockTowerBonus")){

            card.setBlockTowerBonus(true);
        }
    }

    private void parseProductionOrHarvestBonus(JsonNode productionOrHarvestBonusNode, Card card, String effectType) {
        Map<String, Object> productionBonus = new HashMap<>();
        Map<String, Object> harvestBonus = new HashMap<>();

        int productionBonusPoints = productionOrHarvestBonusNode.path("production").asInt();
        int harvestBonusPoints = productionOrHarvestBonusNode.path("harvest").asInt();

        productionBonus.put(effectType, productionBonusPoints);
        harvestBonus.put(effectType, harvestBonusPoints);

        card.setProductionBonusPoints(productionBonus);
        card.setHarvestBonusPoints(harvestBonus);
        return;
    }

    private void parseCardColorBonus(JsonNode cardColorBonusNode, Card card) {

        int points = cardColorBonusNode.path("points").asInt();
        String cardColorString = cardColorBonusNode.path("cardColor").asText();
        CardColor cardColor = CardColor.valueOf(cardColorString.toUpperCase());

        JsonNode resourceNode = cardColorBonusNode.path("resource");

        List<Resource> resources = parseResources(resourceNode);

        CardColorBonus cardColorBonus = new CardColorBonus();
        cardColorBonus.setExists(true);
        cardColorBonus.setCardColor(cardColor);
        cardColorBonus.setPoints(points);
        cardColorBonus.setResources(resources);

        card.setCardColorBonus(cardColorBonus);
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

        Resource resourceToReceive = parseSingleResource(receiveNode);
        multiplier.put("receive", resourceToReceive);

//      For part
        JsonNode forNode = effectNode.path("multiplier").path("for");

//      Check if card color or resource
        if(forNode.has("color")) {
            String cardColorString = forNode.path("color").asText();

            CardColor cardColor = CardColor.valueOf(cardColorString.toUpperCase());
            multiplier.put("for", cardColor);
        } else {
            String forResourceNameString = forNode.fields().next().getKey();
            ResourceName forResourceName = ResourceName.valueOf(forResourceNameString.toUpperCase());

            multiplier.put("for", forResourceName);
        }

    }

    private Resource parseSingleResource(JsonNode resourceNode) {
        String resourceNameString = resourceNode.fields().next().getKey();
        ResourceName resourceName = ResourceName.valueOf(resourceNameString.toUpperCase());
        int amount = resourceNode.path(resourceNameString).asInt();

        return new Resource(resourceName, amount);
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
            Resource effectResource = parseSingleResource(effectResourceNode);

            effectResources.add(effectResource);
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

    public List<Card> getCards() {
        return cards;
    }


    public Stack<Card> getCardsByColor(CardColor color) {
        List<Card> firstAgeCards = new ArrayList<>();
        List<Card> secondAgeCards = new ArrayList<>();
        List<Card> thirdAgeCards = new ArrayList<>();

        for(Card card: cards) {
            if(card.getCardColor() == color) {
                if(card.getCardAge() == 1) {

                    firstAgeCards.add(card);
                } else if(card.getCardAge() == 2) {

                    secondAgeCards.add(card);
                } else {

                    thirdAgeCards.add(card);
                }
            }
        }

//      Randomize
        long seed = System.nanoTime();
        Collections.shuffle(firstAgeCards, new Random(seed));
        Collections.shuffle(secondAgeCards, new Random(seed));
        Collections.shuffle(thirdAgeCards, new Random(seed));

        Stack<Card> deck = new Stack<>();

        for(Card card: thirdAgeCards) {
            deck.push(card);
        }

        for(Card card: secondAgeCards) {
            deck.push(card);
        }

        for(Card card: firstAgeCards) {
            deck.push(card);
        }

        return deck;
    }
}
