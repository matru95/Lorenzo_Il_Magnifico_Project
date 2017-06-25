package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.server.rmiserver.GameServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;

public interface Client extends Remote{
    void joinServer(GameServer s, String playerName, PlayerColor playerColor) throws RemoteException, NoResourceMatch;

    void ping() throws RemoteException;

    UUID getPlayerID();

    /**
     * Method used by the controller and RMI server to send a response to the client
     * @param response
     */
    void send(Map<String, String> response) throws NoResourceMatch, RemoteException;

    void setPlayerID(UUID playerID);
}
