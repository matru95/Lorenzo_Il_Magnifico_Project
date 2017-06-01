package it.polimi.ingsw.gc31.model.cards;

import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
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

    /**
     * Method for executing an array of effects of the card.
     * @param effect an array of effects (could be either Normals or Instants)
     * @throws NoResourceMatch
     */
    private void execEffect(Effect[] effect, Player player) throws NoResourceMatch {
        for (Effect anEffect : effect) anEffect.exec(player);
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
