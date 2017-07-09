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

        System.out.println("Doing exchange!");

        Map<ResourceName, Resource> playerResources = player.getRes();
        Map <String,String> payload=new HashMap<>();
        int checkNumOf = 0;
        int index = 1;

        for(Exchange exchange: exchanges) {

            List<Resource> resourcesToGive = exchange.getResourcesToGive();
            if(canPayResources(resourcesToGive, player)) {
                checkNumOf++;
            }

        }

        if(checkNumOf > 0) {
            payload.put("cardID", String.valueOf(card.getCardID()));
            payload.put("cardName", card.getCardName());

            for(Exchange exchange: exchanges) {
                payload.put(String.valueOf(index), exchange.toString());
                index++;
            }

            return new ServerMessage(ServerMessageEnum.EXCHANGEREQUEST, payload);
        }
        return null;
    }

    private boolean canPayResources(List<Resource> resources, Player player) {
        for(Resource resource: resources) {
            int amountOfResource = resource.getNumOf();
            int amountOfPlayerResource = player.getRes().get(resource.getResourceName()).getNumOf();

            if(amountOfPlayerResource< amountOfResource) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }

    public List<Exchange> getExchanges() {
        return exchanges;
    }
}
