package it.polimi.ingsw.gc31.model.effects.permanent;

import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.effects.boardeffects.HarvestEffect;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

public class HarvestActionEffect extends Effect{
    private int value;

    public HarvestActionEffect(int value) {
        this.value = value;

//      TODO ask for num of servants
    }


    @Override
    public void exec(Player player) throws NoResourceMatch {
        HarvestEffect harvestEffect = new HarvestEffect();
        harvestEffect.exec(player, value);
    }
}
