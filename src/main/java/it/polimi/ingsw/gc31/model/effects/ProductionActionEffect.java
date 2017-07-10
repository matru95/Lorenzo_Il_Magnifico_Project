package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.boardeffects.ProductionEffect;
import jdk.nashorn.internal.ir.ObjectNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionActionEffect extends Effect{
    private int value;

    public ProductionActionEffect(int value) {
        this.value = value;
    }

    @Override
    public ServerMessage exec(Player player) {
        int myServants = player.getRes().get(ResourceName.SERVANTS).getNumOf();
        List<ServerMessage> messages = new ArrayList<>();
        ServerMessage message = new ServerMessage();
        Map<String, String> payload = new HashMap<>();

        payload.put("positionType", "production");
        payload.put("cardValue", String.valueOf(value));
        payload.put("myServants", String.valueOf(myServants));

        message.setMessageType(ServerMessageEnum.SERVANTSREQUEST);
        message.setPayLoad(payload);
        return message;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
