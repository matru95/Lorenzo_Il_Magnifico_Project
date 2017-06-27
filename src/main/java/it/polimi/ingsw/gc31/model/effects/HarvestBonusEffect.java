package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.permanent.HarvestBonus;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import jdk.nashorn.internal.ir.ObjectNode;

public class HarvestBonusEffect extends Effect{
    private HarvestBonus harvestBonus;

    public HarvestBonusEffect(HarvestBonus bonus) {
        this.harvestBonus = bonus;
    }

    @Override
    public void exec(Player player) throws NoResourceMatch {
        player.addBonus(harvestBonus);
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
