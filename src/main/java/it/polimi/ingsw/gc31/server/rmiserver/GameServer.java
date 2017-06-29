package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.messages.ClientMessage;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.view.client.Client;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;

public interface GameServer extends Remote {
    Map<UUID, GameController> getGames() throws RemoteException;

    void join(UUID playerID, String playerName, PlayerColor color, Client client) throws IOException, NoResourceMatch, InterruptedException;

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
    Map<String, UUID> register(Client client, String playerName, PlayerColor playerColor) throws IOException, NoResourceMatch, InterruptedException;

    /**
     * Receives raw data from the remote client
     * @param request the message received
     * @return Response to the client
     * @throws RemoteException
     */
    Map<String, String> send(ClientMessage request) throws IOException, NoResourceMatch, InterruptedException;

}
