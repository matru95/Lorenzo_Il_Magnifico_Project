package it.polimi.ingsw.gc31.messages;

import java.io.Serializable;
import java.util.Map;

public class ClientMessage implements Message, Serializable {

    private ClientMessageEnum clientMessageType;
    private Map<String, String> payload;
    private String playerID;
    private String gameID;

    public ClientMessage(ClientMessageEnum clientMessageType, Map<String, String> payload, String playerID, String gameID) {
        this.clientMessageType = clientMessageType;
        this.payload = payload;
        this.playerID = playerID;
        this.gameID = gameID;
    }

    public ClientMessageEnum getClientMessageType() {
        return clientMessageType;
    }

    public void setClientMessageType(ClientMessageEnum clientMessageType) {
        this.clientMessageType = clientMessageType;
    }

    @Override
    public Map<String, String> getPayload() {
        return payload;
    }

    @Override
    public void setPayLoad(Map<String, String> payLoad) {
        this.payload = payLoad;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

}
