package it.polimi.ingsw.gc31.model.cards;

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

    public Card(CardColor cardColor, String cardName, int cardID, int cardAge) {
        this.cost = new ArrayList<>();
        this.normalEffectResources = new ArrayList<>();
        this.instantEffectResources = new ArrayList<>();
        this.cardColor = cardColor;
        this.cardName = cardName;
        this.cardID = cardID;
        this.cardAge = cardAge;
        this.isOnDeck = true;
    }

    public void setActivationValue(int activationValue) {
        this.activationValue = activationValue;
    }

    public void setCost(Resource cost){
        Map<ResourceName, Resource> resourceToAdd = new HashMap<>();
        resourceToAdd.put(cost.getResourceName(), cost);
        this.cost.add(resourceToAdd);
    }

    public void setInstantEffectResources(List<Resource> instantEffect) {
        this.instantEffectResources = instantEffect;
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
}
