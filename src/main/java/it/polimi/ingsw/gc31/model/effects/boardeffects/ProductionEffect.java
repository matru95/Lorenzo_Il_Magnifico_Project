package it.polimi.ingsw.gc31.model.effects.boardeffects;


import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.effects.permanent.HarvestMalus;
import it.polimi.ingsw.gc31.model.effects.permanent.Malus;
import it.polimi.ingsw.gc31.model.effects.permanent.MalusEnum;
import it.polimi.ingsw.gc31.model.effects.permanent.ProductionMalus;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;

public class ProductionEffect implements BoardEffect {
    /**
     * Constructor of ProductionEffect
     */
    public ProductionEffect() {};

    /**
     * Performs the Production effect.
     * @param player The player that casts the ProductionEffect.
     * @param value The ActionValue of the player's action.
     * @return List<ServerMessage>
     */
    @Override
    public List<ServerMessage> exec(Player player, int value, int amountToPay) {
        List<ServerMessage> messages = new ArrayList<>();
        List<Card> yellowCards = player.getCards().get(CardColor.YELLOW);
        List<Resource> productionTileResources = player.getPlayerTile().getProductionBonus();
        Effect harvestTile = new AddResEffect(productionTileResources);
        int bonus = player.getProductionBonusValue();

        player.getRes().get(ResourceName.SERVANTS).subNumOf(amountToPay);

        harvestTile.exec(player);

        int malusValue = 0;
        List<Malus> maluses = player.getMaluses();
        for(Malus malus:maluses){
            if(malus.getMalusType()== MalusEnum.PRODUCTIONMALUS){
                malusValue = ((ProductionMalus) malus).getProductionFewer();
            }
        }
        int finalValue= value-malusValue+bonus;

        for (Card singleCard : yellowCards) {
            if (singleCard.getActivationValue() <= finalValue) {
                List<ServerMessage> cardMessages = singleCard.execNormalEffect(player);

                for(ServerMessage cardMessage: cardMessages)
                    messages.add(cardMessage);
            }
        }

        return messages;

    }
}
