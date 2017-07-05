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

public class CostEffect extends Effect {
    private Card card;

    public CostEffect(Card card){
        this.card = card;
    }

    @Override
    public ServerMessage exec(Player player) throws NoResourceMatch {
        Map<String, String> payload = new HashMap<>();
        String cardID = String.valueOf(card.getCardID());

        payload.put("cardID", cardID);
        ServerMessage request = new ServerMessage(ServerMessageEnum.COSTREQUEST,payload);
        return request;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}