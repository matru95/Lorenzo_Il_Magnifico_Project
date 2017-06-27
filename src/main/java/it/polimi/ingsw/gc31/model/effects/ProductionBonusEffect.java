package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.permanent.ProductionBonus;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

public class ProductionBonusEffect extends Effect{
    private ProductionBonus productionBonus;

    public ProductionBonusEffect(ProductionBonus bonus) {
        this.productionBonus = bonus;
    }


    @Override
    public void exec(Player player) throws NoResourceMatch {
        player.addBonus(productionBonus);
    }
}
