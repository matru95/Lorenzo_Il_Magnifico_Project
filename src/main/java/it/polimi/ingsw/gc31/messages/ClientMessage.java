package it.polimi.ingsw.gc31.messages;

import java.util.Map;

public class ClientMessage implements Message{
    private Map<String, Object> payload;
    private String gameID;
    private ClientMessageEnum clientMessageType;

    public ClientMessage() {}

    public ClientMessage(ClientMessageEnum clientMessageType, Map<String, Object> payload) {
        this.clientMessageType = clientMessageType;
        this.payload = payload;
    }



    @Override
    public void setPayLoad(Map<String, Object> payLoad) {
        this.payload = payLoad;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void setClientMessageType(ClientMessageEnum clientMessageType) {
        this.clientMessageType = clientMessageType;
    }

    public String getGameID() {

        return gameID;
    }

    public ClientMessageEnum getClientMessageType() {
        return clientMessageType;
    }

    @Override
    public Map<String, Object> getPayload() {
        return payload;
    }
}
