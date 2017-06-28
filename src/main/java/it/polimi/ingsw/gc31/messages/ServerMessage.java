package it.polimi.ingsw.gc31.messages;

import java.util.Map;

public class ServerMessage implements Message{
    private Map<String, Object> payload;
    private ServerMessageEnum messageType;

    public ServerMessage(ServerMessageEnum messageType, Map<String, Object> payload) {
        this.messageType = messageType;
        this.payload = payload;
    }

    public ServerMessage() {}


    @Override
    public void setPayLoad(Map<String, Object> payLoad) {
        this.payload = payLoad;
    }

    @Override
    public Map<String, Object> getPayload() {
        return payload;
    }
}
