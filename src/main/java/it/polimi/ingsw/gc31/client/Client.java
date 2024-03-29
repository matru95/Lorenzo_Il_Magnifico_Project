package it.polimi.ingsw.gc31.client;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.server.Server;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    /**
     * Method to make the client join a server
     * @param s the game server this client is connected to.
     * @param playerName the player name
     * @throws IOException
     * @throws InterruptedException
     */
    void joinServer(Server s, String playerName) throws IOException, InterruptedException, ClassNotFoundException;

    /**
     * Method used by the controller and RMI server to send a response to the client
     * @param request The Message request
     */
    void send(ServerMessage request) throws IOException, InterruptedException;

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

    /**
     * Get the ID of the socket client in case it's a socket connection
     * @return String socketClientID;
     * @throws RemoteException
     */
    String getSocketClientID() throws RemoteException;

    /**
     * Set the player id
     * @param playerID
     * @throws RemoteException
     */
    void setPlayerID(String playerID) throws RemoteException;
}
