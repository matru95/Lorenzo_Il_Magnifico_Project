package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.messages.Message;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.view.client.Client;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;

public interface GameServer extends Remote {
    Map<UUID, GameInstance> getGames() throws RemoteException;

    GameInstance getGame(UUID instanceID) throws RemoteException;

    void join(UUID playerID, String playerName, PlayerColor color) throws RemoteException, NoResourceMatch;

    void leave(UUID playerID) throws RemoteException;

    /**
     *
     * @param client the Client that is being registered
     * @param playerName
     * @param playerColor
     * @return
     * @throws RemoteException
     * @throws NoResourceMatch
     */
    UUID register(Client client, String playerName, PlayerColor playerColor) throws RemoteException, NoResourceMatch;

    /**
     * Receives raw data from the remote client
     * @param request the message received
     * @return Response to the client
     * @throws RemoteException
     */
    Map<String, String> sendData(Message request) throws IOException, NoResourceMatch;

}
