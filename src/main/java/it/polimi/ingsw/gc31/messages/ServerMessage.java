package it.polimi.ingsw.gc31.messages;

import java.io.Serializable;
import java.util.Map;

public class ServerMessage implements Message, Serializable{
    private Map<String, String> payload;
    private ServerMessageEnum messageType;

    public ServerMessage(ServerMessageEnum messageType, Map<String, String> payload) {
        this.messageType = messageType;
        this.payload = payload;
    }

    public ServerMessage() {}


    @Override
    public void setPayLoad(Map<String, String> payLoad) {
        this.payload = payLoad;
    }

    @Override
    public Map<String, String> getPayload() {
        return payload;
    }

    public ServerMessageEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(ServerMessageEnum messageType) {
        this.messageType = messageType;
    }
}
