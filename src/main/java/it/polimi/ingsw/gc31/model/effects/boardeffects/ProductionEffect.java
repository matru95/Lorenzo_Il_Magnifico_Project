package it.polimi.ingsw.gc31.model.effects.boardeffects;


import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.messages.Message;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.List;

public class ProductionEffect implements BoardEffect {

    public ProductionEffect() {};

    @Override
    public Message exec(Player player, int value) throws NoResourceMatch {
        List<Card> yellowCards = player.getCards().get(CardColor.YELLOW);
        List<Resource> productionTileResources = player.getPlayerTile().getProductionBonus();
        Effect productionTile = new AddResEffect(productionTileResources);
        productionTile.exec(player);


        for (Card singleCard : yellowCards) {
            if (singleCard.getActivationValue() <= value) {
                singleCard.execNormalEffect(player);
            }
        }

        return null;
    }
}
