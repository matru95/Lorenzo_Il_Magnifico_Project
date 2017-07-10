package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.permanent.Bonus;
import jdk.nashorn.internal.ir.ObjectNode;


public class CardColorBonusEffect extends Effect{
    Bonus bonus;

    /**
     * Constructor of CardColorBonusEffect
     * @param bonus the bonus of the effect.
     */
    public CardColorBonusEffect(Bonus bonus) {
        this.bonus = bonus;
    }

    @Override
    public ServerMessage exec(Player player) {
        player.addBonus(bonus);
        return null;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
