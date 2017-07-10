package it.polimi.ingsw.gc31.server;

import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.messages.ClientMessage;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.client.Client;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;

public interface Server extends Remote {

    Map<UUID, GameController> getGames() throws RemoteException;

    void join(UUID playerID, String playerName, Client client) throws IOException, InterruptedException;

    /**
     * Registers client to the server
     * @param client the Client that is being registered
     * @param playerName
     * @throws RemoteException
     */
    void register(Client client, String playerName) throws IOException, InterruptedException;

    /**
     * Receives raw data from the remote client
     * @param request the message received
     * @return Response to the client
     * @throws RemoteException
     */
    void send(ClientMessage request) throws IOException, InterruptedException;

    void sendMessageToClient(Client client, ServerMessage request) throws InterruptedException, IOException;
}
