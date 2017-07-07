package it.polimi.ingsw.gc31.model.effects;


import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.Exchange;
import it.polimi.ingsw.gc31.model.resources.Resource;
import jdk.nashorn.internal.ir.ObjectNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExchangeEffect extends Effect{
    private List<Exchange> exchanges;
    private Card card;

    public ExchangeEffect(List<Exchange> exchanges, Card card){
        this.exchanges = exchanges;
        this.card = card;
    }

    @Override
    public ServerMessage exec(Player player){

        Map<ResourceName, Resource> playerResources = player.getRes();
        Map <String,String> payload=new HashMap<>();

        for(Exchange exchange: exchanges) {

            List<Resource> resourcesToGive = exchange.getResourcesToGive();
            int checkNumOf = 0;

            for(Resource resource: resourcesToGive) {
                ResourceName resourceName = resource.getResourceName();

                if(playerResources.get(resourceName).getNumOf() > resource.getNumOf()) {
                    checkNumOf++;
                }

                if(playerResources.get(resourceName).getNumOf() > resource.getNumOf() && checkNumOf > 0){
                    payload.put("cardID", String.valueOf(card.getCardID()));
                    return new ServerMessage(ServerMessageEnum.EXCHANGEREQUEST, payload);
                }
            }
        }
        return null;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }

    public List<Exchange> getExchanges() {
        return exchanges;
    }
}
