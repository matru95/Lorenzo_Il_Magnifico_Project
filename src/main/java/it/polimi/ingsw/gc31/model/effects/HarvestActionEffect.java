package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.effects.boardeffects.HarvestEffect;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import jdk.nashorn.internal.ir.ObjectNode;

public class HarvestActionEffect extends Effect{
    private int value;

    public HarvestActionEffect(int value) {
        this.value = value;

//      TODO ask for num of servants
    }


    @Override
    public ServerMessage exec(Player player) throws NoResourceMatch {
        HarvestEffect harvestEffect = new HarvestEffect();
        harvestEffect.exec(player, value);
        return null;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}