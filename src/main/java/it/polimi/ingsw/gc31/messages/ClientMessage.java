package it.polimi.ingsw.gc31.messages;

import it.polimi.ingsw.gc31.client.Client;

import java.io.Serializable;
import java.util.Map;

public class ClientMessage implements Message, Serializable {

    private ClientMessageEnum clientMessageType;
    private Map<String, String> payload;
    private String playerID;
    private String gameID;

    private Client client;

    /**
     *
     * @param clientMessageType
     * @param payload
     * @param playerID
     * @param gameID
     */
    public ClientMessage(ClientMessageEnum clientMessageType, Map<String, String> payload, String playerID, String gameID) {
        this.clientMessageType = clientMessageType;
        this.payload = payload;
        this.playerID = playerID;
        this.gameID = gameID;
    }

    /**
     *
     * @param clientMessageType
     * @param payload
     * @param playerID
     * @param gameID
     * @param client
     */
    public ClientMessage(ClientMessageEnum clientMessageType, Map<String, String> payload, String playerID, String gameID, Client client) {
        this.clientMessageType = clientMessageType;
        this.payload = payload;
        this.playerID = playerID;
        this.gameID = gameID;
        this.client = client;
    }

    /**
     *
     */
    public ClientMessage() {

    }

    /**
     * Getter for "clientMessageType" attribute.
     * @return ClientMessageEnum
     */
    public ClientMessageEnum getClientMessageType() {
        return clientMessageType;
    }

    /**
     * Setter for "clientMessageType" attribute.
     * @param clientMessageType: ClientMessageEnum
     */
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

    /**
     * Getter for "playerID" attribute.
     * @return String
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Setter for "playerID" attribute.
     * @param playerID: String
     */
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    /**
     * Getter for "gameID" attribute.
     * @return String
     */
    public String getGameID() {
        return gameID;
    }

    /**
     * Setter for "gameID" attribute.
     * @param gameID: String
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    /**
     *
     * @param client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     *
     * @return
     */
    public Client getClient() {

        return client;
    }
}
