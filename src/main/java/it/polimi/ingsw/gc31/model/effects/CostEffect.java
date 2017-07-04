package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.model.cards.Card;
import jdk.nashorn.internal.ir.ObjectNode;


import java.util.HashMap;
import java.util.Map;

//TODO implementare il controllo nella board o nella classe player o...?
public class CostEffect extends Effect {
    private Card purpleCard;

    CostEffect(Card purpleCard){
        this.purpleCard=purpleCard;
    }

    @Override
    public ServerMessage exec(Player player) throws NoResourceMatch {
        if(this.purpleCard.getCost().size()==2 && this.purpleCard.getCardColor()== CardColor.PURPLE) {
            Map<String, String> payload = new HashMap<>();
            payload.put("cardID",String.valueOf(purpleCard.getCardID()));
            ServerMessage request = new ServerMessage(ServerMessageEnum.COSTREQUEST,payload);
            return request;
        } else {
            return null;
        }
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}