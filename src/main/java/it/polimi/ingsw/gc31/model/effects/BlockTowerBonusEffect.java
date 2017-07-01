package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.permanent.BlockTowerBonus;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import jdk.nashorn.internal.ir.ObjectNode;

public class BlockTowerBonusEffect extends Effect{

    @Override
    public void exec(Player player) throws NoResourceMatch {
        player.addBonus(new BlockTowerBonus());
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
