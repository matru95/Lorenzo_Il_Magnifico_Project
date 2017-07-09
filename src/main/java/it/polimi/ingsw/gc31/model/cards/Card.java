package it.polimi.ingsw.gc31.model.cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Card {

    private final List<Effect> instantEffects;
    private final List<Effect> normalEffects;

    private final CardColor cardColor;
    private final String cardName;
    private final int cardID;
    private final int cardAge;
    private Boolean isOnDeck;
    private List<Map<ResourceName, Resource>> cost;
    private int activationValue;

    private Resource costBond;

    public Card(CardColor cardColor, String cardName, int cardID, int cardAge) {
        this.cost = new ArrayList<>();
        this.cardColor = cardColor;
        this.cardName = cardName;
        this.cardID = cardID;
        this.cardAge = cardAge;
        this.isOnDeck = true;
        this.activationValue = 0;
        this.costBond = null;

        normalEffects = new ArrayList<>();
        instantEffects = new ArrayList<>();
    }

    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode cardObjectNode = mapper.createObjectNode();
        cardObjectNode.put("cardID", cardID);
        cardObjectNode.put("cardName", cardName);

        ArrayNode costsNode = mapper.createArrayNode();

        for(Map<ResourceName, Resource> singleCost: cost) {
            ObjectNode singleCostNode = mapper.createObjectNode();

            for(Map.Entry<ResourceName, Resource> singleCostMap: singleCost.entrySet()) {
                singleCostNode.put(singleCostMap.getKey().toString(), singleCostMap.getValue().getNumOf());
            }

            costsNode.add(singleCostNode);
        }

        cardObjectNode.set("cost", costsNode);

        return cardObjectNode;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode cardObjectNode = toJson();

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cardObjectNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }


    }

    public void setActivationValue(int activationValue) {
        this.activationValue = activationValue;
    }

    public void setCost(List<Map<ResourceName, Resource>> cost){
        this.cost = cost;
    }


    public List<ServerMessage> execInstantEffect(Player player) {
        List<ServerMessage> result = new ArrayList<>();

        for(Effect effect: instantEffects) {
            ServerMessage message = effect.exec(player);
            result.add(message);
        }

        return result;
    }

    public List<ServerMessage> execNormalEffect(Player player) {
        List<ServerMessage> messages = new ArrayList<>();

        for(Effect effect: normalEffects) {
            ServerMessage message = effect.exec(player);
            messages.add(message);
        }

        return messages;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public String getCardName() {
        return cardName;
    }

    public int getCardID() {
        return cardID;
    }

    public int getCardAge() {
        return cardAge;
    }

    public Boolean getOnDeck() {
        return isOnDeck;
    }

    public void setOnDeck(Boolean onDeck) {
        isOnDeck = onDeck;
    }

    public List<Map<ResourceName,Resource>> getCost() {
        return this.cost;
    }

    public int getActivationValue() {
        return activationValue;
    }

    public void setCostBond(Resource costBond) {
        this.costBond = costBond;
    }

    public Resource getCostBond() {
        return costBond;
    }

    public void addNormalEffect(Effect effect) {
        this.normalEffects.add(effect);
    }

    public void addInstantEffect(Effect effect) {
        this.instantEffects.add(effect);
    }

    public List<Effect> getNormalEffects() {
        return normalEffects;
    }

    public List<Effect> getInstantEffects() {
        return instantEffects;
    }

    public boolean isAffordable(Map<ResourceName, Resource> playerResources) {
        int costBondAmount = 0;
        int costLength = cost.size();
        int unaffordableIndex = 0;

        if(costBond != null) {
            costBondAmount = costBond.getNumOf();

            int playerResourceAmount = playerResources.get(costBond.getResourceName()).getNumOf();

            if(costBondAmount > playerResourceAmount) {
                return false;
            }
        }

        if(costLength == 0) {

            return true;
        }

        for(Map<ResourceName, Resource> singleCost: cost) {

            for(Map.Entry<ResourceName, Resource> singleCostEntry: singleCost.entrySet()) {
                Resource playerResource = playerResources.get(singleCostEntry.getKey());
                int playerResourceAmount = playerResource.getNumOf();
                int costResourceAmount = singleCostEntry.getValue().getNumOf();

                if(costResourceAmount > playerResourceAmount) {
                    unaffordableIndex++;
                }
            }
        }

        if(unaffordableIndex == costLength) {
            return false;
        }

        return true;
    }

    public void payCosts(Map<ResourceName, Resource> playerResources) {
        Map<ResourceName, Resource> singleCost = getCost().get(0);

        for(Map.Entry<ResourceName, Resource> costEntry: singleCost.entrySet()) {
            playerResources.get(costEntry.getKey()).subNumOf(costEntry.getValue().getNumOf());
        }
    }
}
