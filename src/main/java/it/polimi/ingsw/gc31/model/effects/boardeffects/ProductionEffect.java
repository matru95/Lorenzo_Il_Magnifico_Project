package it.polimi.ingsw.gc31.model.effects.boardeffects;


import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;

public class ProductionEffect implements BoardEffect {

    public ProductionEffect() {};

    @Override
    public List<ServerMessage> exec(Player player, int value) {
        List<ServerMessage> messages = new ArrayList<>();
        List<Card> yellowCards = player.getCards().get(CardColor.YELLOW);
        List<Resource> productionTileResources = player.getPlayerTile().getProductionBonus();
        Effect harvestTile = new AddResEffect(productionTileResources);

        harvestTile.exec(player);


        for (Card singleCard : yellowCards) {
            if (singleCard.getActivationValue() <= value) {
                List<ServerMessage> cardMessages = singleCard.execNormalEffect(player);

                for(ServerMessage cardMessage: cardMessages)
                    messages.add(cardMessage);
            }
        }

        return messages;

    }
}
