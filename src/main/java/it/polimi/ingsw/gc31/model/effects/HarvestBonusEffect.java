package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.permanent.HarvestBonus;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import jdk.nashorn.internal.ir.ObjectNode;

public class HarvestBonusEffect extends Effect{
    private HarvestBonus harvestBonus;

    public HarvestBonusEffect(HarvestBonus bonus) {
        this.harvestBonus = bonus;
    }

    @Override
    public ServerMessage exec(Player player) throws NoResourceMatch {
        player.addBonus(harvestBonus);
        return null;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
