package it.polimi.ingsw.gc31.model.cards;

import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.Map;

public class Card {

    private final CardColor cardColor;
    private final String cardName;
    private final int cardID;
    private final int cardAge;
    private final CardEffects cardEffects;
    private Boolean isOnDeck;

    public Card(CardColor cardColor, String cardName, int cardID, int cardAge, CardEffects cardEffects) {
        this.cardColor = cardColor;
        this.cardName = cardName;
        this.cardID = cardID;
        this.cardAge = cardAge;
        this.cardEffects = cardEffects;
        this.isOnDeck = true;
    }

    @Override
    public String toString() {
        return "Age" + cardAge + " | " + cardColor + " | " + "ID" + cardID +
                " " + cardName +
                " Effects:" + cardEffects;
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

    public CardEffects getCardEffects() {
        return cardEffects;
    }

    public Boolean getOnDeck() {
        return isOnDeck;
    }

    public void setOnDeck(Boolean onDeck) {
        isOnDeck = onDeck;
    }

    public Map<String, Resource>[] getCost() {
        return this.cardEffects.getCost();
    }
}
