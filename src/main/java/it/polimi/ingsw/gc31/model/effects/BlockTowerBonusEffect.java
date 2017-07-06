package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.permanent.BlockTowerBonus;
import jdk.nashorn.internal.ir.ObjectNode;

public class BlockTowerBonusEffect extends Effect{

    @Override
    public ServerMessage exec(Player player) {
        player.addBonus(new BlockTowerBonus());
        return null;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
