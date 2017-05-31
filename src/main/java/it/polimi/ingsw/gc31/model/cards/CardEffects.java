package it.polimi.ingsw.gc31.model.cards;

import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.Map;

public class CardEffects {

    private Map<String, Resource>[] cost;
    private Effect[] normalEffect;
    private Effect[] instantEffect;
    private int warPointsBond;

    public CardEffects(Map<String, Resource>[] cost, Effect[] normalEffect, Effect[] instantEffect, int warPointsBond) {
        this.cost = cost;
        this.normalEffect = normalEffect;
        this.instantEffect = instantEffect;
        this.warPointsBond = warPointsBond;
    }

    private CardEffects(){
    }

    public Map<String, Resource>[] getCost() {
        return cost;
    }

    public Effect[] getNormalEffect() {
        return normalEffect;
    }

    public Effect[] getInstantEffect() {
        return instantEffect;
    }

    public int getWarPointsBond() {
        return warPointsBond;
    }
}
