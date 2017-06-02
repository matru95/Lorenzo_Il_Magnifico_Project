package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.server.rmiserver.GameServer;
import it.polimi.ingsw.gc31.server.rmiserver.GameServerImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote{
    void joinServer(GameServer s) throws RemoteException;
}
