package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.boardeffects.ProductionEffect;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import jdk.nashorn.internal.ir.ObjectNode;

public class ProductionActionEffect extends Effect{
    private int value;

    public ProductionActionEffect(int value) {
        this.value = value;
//      TODO ask number of servants and add it to value
    }

    @Override
    public ServerMessage exec(Player player) throws NoResourceMatch {
        ProductionEffect productionEffect = new ProductionEffect();
        productionEffect.exec(player, value);
        return null;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
