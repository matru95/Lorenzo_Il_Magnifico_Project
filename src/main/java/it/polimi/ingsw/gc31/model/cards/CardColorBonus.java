package it.polimi.ingsw.gc31.model.cards;

import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.List;

public class CardColorBonus {
    private int points;
    private CardColor cardColor;
    private List<Resource> resources;
    private boolean exists;

    public CardColorBonus() {
        this.exists = false;
    }

    public boolean exists() {
        return exists;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
