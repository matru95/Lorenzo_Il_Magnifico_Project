package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.permanent.ProductionBonus;
import jdk.nashorn.internal.ir.ObjectNode;

public class ProductionBonusEffect extends Effect{
    private ProductionBonus productionBonus;

    public ProductionBonusEffect(ProductionBonus bonus) {
        this.productionBonus = bonus;
    }


    @Override
    public ServerMessage exec(Player player) {
        player.addBonus(productionBonus);
        return null;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
