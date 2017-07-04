package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
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
     * @throws IOException
     * @throws NoResourceMatch
     * @throws InterruptedException
     */
    void joinServer(GameServer s, String playerName) throws IOException, NoResourceMatch, InterruptedException, ClassNotFoundException;

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
    String getGameID() throws RemoteException;

    /**
     * Getter for "playerID" attribute.
     * @return String
     * @throws RemoteException
     */
    String getPlayerID() throws RemoteException;

    String getSocketClientID() throws RemoteException;

    void setPlayerID(String playerID) throws RemoteException;
}
