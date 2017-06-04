package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.server.rmiserver.GameServer;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientImplementation implements Client, Serializable{

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Client client = new ClientImplementation();
        Registry registry = LocateRegistry.getRegistry(8080);
        GameServer gameServer = (GameServer) registry.lookup("game_server");
        gameServer.register(client);
    }

    public ClientImplementation() {

    }

    @Override
    public void joinServer(GameServer s, String playerName, PlayerColor playerColor) throws RemoteException, NoResourceMatch {
        s.join(playerName, playerColor);
        return;
    }

}
