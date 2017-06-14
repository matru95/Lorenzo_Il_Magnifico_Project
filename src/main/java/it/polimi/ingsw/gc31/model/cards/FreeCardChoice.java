package it.polimi.ingsw.gc31.model.cards;

import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;

public class FreeCardChoice {
//  If points != 0 and cardColor == null, then you can choose from all colors
    private int points;
    private CardColor cardColor;
    private List<Resource> resources;

    public FreeCardChoice() {
        points = 0;
        cardColor = null;
        resources = new ArrayList<>();
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
}
