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

    public ExchangeEffect(List<Exchange> exchange){
        this.exchanges = exchanges;
    }

    @Override
    public ServerMessage exec(Player player){
        Map<ResourceName, Resource> playerResources = player.getRes();

        for(Exchange exchange: exchanges) {
            List<Resource> resourcesToGive = exchange.getResourcesToGive();

            for(Resource resource: resourcesToGive) {
                ResourceName resourceName = resource.getResourceName();

                if(playerResources.get(resourceName).getNumOf() > resource.getNumOf()) {

                }
            }
        }

        //SINGOLO E DOPPIO EXCHANGE
//        int numOfExchnge= resourcesToGive.size();
//        Map <String,String> payload=new HashMap<>();
//
//        for(int i=(numOfExchnge-1); i>0 ; i--){
//
//            Resource resourceGive= resourcesToGive.get(i);
//            if(resourceGive.getNumOf()<player.getRes().get(resourceGive.getResourceName()).getNumOf()){
//                return new ServerMessage(ServerMessageEnum.EXCHANGEREQUEST,payload);
//            }
//        }
        /*
        if(resourcesToGive.size()<2){
            Resource resourceGive=resourcesToGive.get(0);
            Map<ResourceName,Resource> playerRes= player.getRes();
            Resource resource = playerRes.get(resourceGive.getResourceName());
            if(resourceGive.getNumOf()<resource.getNumOf()){
                return new ServerMessage(ServerMessageEnum.EXCHANGEREQUEST,payload);
            }
        }
        if(resourcesToGive.size()==2){
            //itero lo stesso ragionamento per il singolo exchange 2 volte
        }*/
        return null;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
