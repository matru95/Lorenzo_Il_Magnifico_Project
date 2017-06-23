package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.server.rmiserver.GameServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote{
    void joinServer(GameServer s, String playerName, PlayerColor playerColor) throws RemoteException, NoResourceMatch;

    void ping() throws RemoteException;
}
