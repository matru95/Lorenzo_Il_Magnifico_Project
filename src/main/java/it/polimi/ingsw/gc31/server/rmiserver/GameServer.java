package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.view.client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;

public interface GameServer extends Remote {
    Map<UUID, GameInstance> getGames() throws RemoteException;

    GameInstance getGame(UUID instanceID) throws RemoteException;

    void join(UUID playerID, String playerName, PlayerColor color) throws RemoteException, NoResourceMatch;

    void leave(UUID playerID) throws RemoteException;

    UUID register(Client client, String playerName, PlayerColor playerColor) throws RemoteException, NoResourceMatch;

    /**
     * Receives raw data from the remote client
     * @param JSONData the raw JSON data received
     * @return Response to the client
     * @throws RemoteException
     */
    Map<String, String> sendData(Map<String, String> JSONData) throws RemoteException;

}
