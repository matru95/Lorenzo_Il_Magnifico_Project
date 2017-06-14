package it.polimi.ingsw.gc31.model.cards;

import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.effects.EffectFactory;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

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
        this.normalMultiplier = new HashMap<>();
        this.instantMultiplier = new HashMap<>();
        this.exchanges = new ArrayList<>();
        this.cardColorBonus = new CardColorBonus();
        this.productionBonusPoints = new HashMap<>();
        this.harvestBonusPoints = new HashMap<>();
        this.freeCardChoice = new FreeCardChoice();
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
            Effect addresource=EffectFactory.getEffect("addRes", this.instantEffectResources,0);
            addresource.exec(player);
        }
    }
    public void execNormalEffect(Player player) throws NoResourceMatch {
        if(this.instantEffectResources.size()>0){
            Effect addresource=EffectFactory.getEffect("addRes", this.instantEffectResources,0);
            addresource.exec(player);
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
}
