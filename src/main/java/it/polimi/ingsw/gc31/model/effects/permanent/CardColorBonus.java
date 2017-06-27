package it.polimi.ingsw.gc31.model.effects.permanent;

import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.List;

public class CardColorBonus implements Bonus{
    private int points;
    private CardColor cardColor;
    private List<Resource> resources;

    public CardColorBonus() {
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public int getPoints() {
        return points;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

}
