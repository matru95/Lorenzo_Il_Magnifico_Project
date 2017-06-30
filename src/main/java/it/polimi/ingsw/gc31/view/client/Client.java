package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.server.rmiserver.GameServer;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface Client extends Remote {

    /**
     * Method to make the client join a server
     * @param s
     * @param playerName
     * @param playerColor
     * @throws IOException
     * @throws NoResourceMatch
     * @throws InterruptedException
     */
    void joinServer(GameServer s, String playerName, PlayerColor playerColor) throws IOException, NoResourceMatch, InterruptedException;

    /**
     * Method to ping.
     * @throws RemoteException
     */
    void ping() throws RemoteException;


    /**
     * Method used by the controller and RMI server to send a response to the client
     * @param request The Message request
     */
    void send(ServerMessage request) throws NoResourceMatch, IOException, InterruptedException;

    /**
     * Getter for "gameID" attribute.
     * @return UUID
     * @throws RemoteException
     */
    UUID getGameID() throws RemoteException;

    /**
     * Getter for "playerID" attribute.
     * @return UUID
     * @throws RemoteException
     */
    UUID getPlayerID() throws RemoteException;
}
