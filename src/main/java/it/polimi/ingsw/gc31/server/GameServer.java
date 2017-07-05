package it.polimi.ingsw.gc31.server;

import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.messages.ClientMessage;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.client.Client;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;

public interface GameServer extends Remote {
    Map<UUID, GameController> getGames() throws RemoteException;


    void leave(UUID playerID) throws RemoteException;

    void join(UUID playerID, String playerName, Client client) throws IOException, NoResourceMatch, InterruptedException;

    /**
     *
     * @param client the Client that is being registered
     * @param playerName
     * @return
     * @throws RemoteException
     * @throws NoResourceMatch
     */
    void register(Client client, String playerName) throws IOException, NoResourceMatch, InterruptedException;

    /**
     * Receives raw data from the remote client
     * @param request the message received
     * @return Response to the client
     * @throws RemoteException
     */
    void send(ClientMessage request) throws IOException, NoResourceMatch, InterruptedException;

    void sendMessageToClient(Client client, ServerMessage request) throws InterruptedException, IOException, NoResourceMatch;
}
