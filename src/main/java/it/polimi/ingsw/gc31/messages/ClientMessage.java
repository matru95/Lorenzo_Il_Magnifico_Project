package it.polimi.ingsw.gc31.messages;

import java.io.Serializable;
import java.util.Map;

public class ClientMessage implements Message, Serializable{
    private Map<String, String> payload;
    private String gameID;
    private ClientMessageEnum clientMessageType;
    private String playerID;


    public ClientMessage(String playerID) {
        this.playerID = playerID;
    }

    public ClientMessage(ClientMessageEnum clientMessageType, Map<String, String> payload, String playerID) {
        this.clientMessageType = clientMessageType;
        this.payload = payload;
        this.playerID = playerID;
    }


    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerID() {

        return playerID;
    }

    @Override
    public void setPayLoad(Map<String, String> payLoad) {
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
    public Map<String, String> getPayload() {
        return payload;
    }
}
