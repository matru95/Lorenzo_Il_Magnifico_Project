package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.permanent.Bonus;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import jdk.nashorn.internal.ir.ObjectNode;


public class CardColorBonusEffect extends Effect{
    Bonus bonus;

    public CardColorBonusEffect(Bonus bonus) {
        this.bonus = bonus;
    }

    @Override
    public void exec(Player player) throws NoResourceMatch {
        player.addBonus(bonus);
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
