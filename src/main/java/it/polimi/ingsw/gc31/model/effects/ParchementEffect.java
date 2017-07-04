package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.resources.*;
import jdk.nashorn.internal.ir.ObjectNode;


import java.util.HashMap;
import java.util.Map;


public class ParchementEffect extends Effect{
    private int numOfParchments;

    ParchementEffect(int numOfParchments) {
        this.numOfParchments = numOfParchments;
    }

    @Override
    public ServerMessage exec(Player player) throws NoResourceMatch {
        Map<String,String> payload= new HashMap<>();
        payload.put("parchments", String.valueOf(numOfParchments));
        ServerMessage request = new ServerMessage(ServerMessageEnum.PARCHMENTREQUEST, payload);
    return request;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
