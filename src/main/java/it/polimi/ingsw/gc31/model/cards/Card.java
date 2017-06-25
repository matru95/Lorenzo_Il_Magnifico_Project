package it.polimi.ingsw.gc31.model.cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Card {

    private final CardColor cardColor;
    private final String cardName;
    private final int cardID;
    private final int cardAge;
    private Boolean isOnDeck;
    private List<Map<ResourceName, Resource>> cost;
    private List<Resource> normalEffectResources;
    private List<Resource> instantEffectResources;
    private int activationValue;
    private int numOfInstantParchment;
    private int numOfNormalParchment;
    private Map<String, Object> normalMultiplier;
    private Map<String, Object> instantMultiplier;
    private List<Exchange> exchanges;
    private CardColorBonus cardColorBonus;
    private Map<String, Object> harvestBonusPoints;
    private Map<String, Object> productionBonusPoints;
    private boolean blockTowerBonus;
    private FreeCardChoice freeCardChoice;
    private Resource costBond;
    private int productionAction;
    private int harvestAction;


    public void setProductionAction(int productionAction) {
        this.productionAction = productionAction;
    }

    public void setHarvestAction(int harvestAction) {
        this.harvestAction = harvestAction;
    }

    public Card(CardColor cardColor, String cardName, int cardID, int cardAge) {
        this.cost = new ArrayList<>();
        this.normalEffectResources = new ArrayList<>();
        this.instantEffectResources = new ArrayList<>();
        this.cardColor = cardColor;
        this.cardName = cardName;
        this.cardID = cardID;
        this.cardAge = cardAge;
        this.isOnDeck = true;
        this.activationValue = 0;
        this.numOfNormalParchment = 0;
        this.numOfInstantParchment = 0;
        this.normalMultiplier = null;
        this.instantMultiplier = null;
        this.exchanges = new ArrayList<>();
        this.cardColorBonus = new CardColorBonus();
        this.productionBonusPoints = new HashMap<>();
        this.harvestBonusPoints = new HashMap<>();
        this.freeCardChoice = new FreeCardChoice();
        this.costBond = null;
        this.productionAction = 0;
        this.harvestAction = 0;
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

    public void setInstantEffectResources(List<Resource> instantEffect) {
        this.instantEffectResources = instantEffect;
    }

    public void execInstantEffect(Player player) throws NoResourceMatch {
        if(this.instantEffectResources.size()>0){
            Effect addResourceEffect = new AddResEffect(this.instantEffectResources);
            addResourceEffect.exec(player);
        }
    }
    public void execNormalEffect(Player player) throws NoResourceMatch {
        if(this.normalEffectResources.size()>0){
            Effect addResourceEffect = new AddResEffect(this.normalEffectResources);
            addResourceEffect.exec(player);
        }
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

    public void setNumOfInstantParchment(int numOfInstantParchment) {
        this.numOfInstantParchment = numOfInstantParchment;
    }

    public void setNumOfNormalParchment(int numOfNormalParchment) {
        this.numOfNormalParchment = numOfNormalParchment;
    }

    public void setNormalEffectResources(List<Resource> normalEffectResources) {
        this.normalEffectResources = normalEffectResources;
    }

    public int getNumOfInstantParchment() {
        return this.numOfInstantParchment;
    }

    public void insertExchange(Exchange exchange) {
        this.exchanges.add(exchange);
    }

    public void setNormalMultiplier(Map<String, Object> normalMultiplier) {
        this.normalMultiplier = normalMultiplier;
    }

    public Map<String, Object> getNormalMultiplier() {
        return normalMultiplier;
    }

    public List<Exchange> getExchanges() {
        return exchanges;
    }

    public List<Resource> getNormalEffectResources() {
        return normalEffectResources;
    }

    public List<Resource> getInstantEffectResources() {
        return instantEffectResources;
    }

    public void setCardColorBonus(CardColorBonus cardColorBonus) {
        this.cardColorBonus = cardColorBonus;
    }

    public void setHarvestBonusPoints(Map<String, Object> harvestBonusPoints) {
        this.harvestBonusPoints = harvestBonusPoints;
    }

    public void setFreeCardChoice(FreeCardChoice freeCardChoice) {
        this.freeCardChoice = freeCardChoice;
    }

    public void setProductionBonusPoints(Map<String, Object> productionBonusPoints) {
        this.productionBonusPoints = productionBonusPoints;
    }

    public void setBlockTowerBonus(boolean blockTowerBonus) {
        this.blockTowerBonus = blockTowerBonus;
    }

    public void setInstantMultiplier(Map<String, Object> instantMultiplier) {
        this.instantMultiplier = instantMultiplier;
    }

    public int getActivationValue() {
        return activationValue;
    }

    public Map<String, Object> getInstantMultiplier() {
        return instantMultiplier;
    }

    public void setCostBond(Resource costBond) {
        this.costBond = costBond;
    }
}
