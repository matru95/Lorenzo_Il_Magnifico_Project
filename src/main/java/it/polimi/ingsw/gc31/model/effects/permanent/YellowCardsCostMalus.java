package it.polimi.ingsw.gc31.model.effects.permanent;

import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.List;

public class YellowCardsCostMalus implements Malus{
    private MalusEnum type;
    private CardColor loseForEveryCostCardColor;
    private List<Resource> loseForEveryCost;

    public YellowCardsCostMalus(MalusEnum type, CardColor loseForEveryCostCardColor, List<Resource> loseForEveryCost) {
        this.type = type;
        this.loseForEveryCostCardColor = loseForEveryCostCardColor;
        this.loseForEveryCost = loseForEveryCost;
    }

    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    public CardColor getLoseForEveryCostCardColor() {
        return loseForEveryCostCardColor;
    }

    public List<Resource> getLoseForEveryCost() {
        return loseForEveryCost;
    }
}
