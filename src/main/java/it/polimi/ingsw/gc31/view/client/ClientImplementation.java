package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.server.rmiserver.GameServer;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientImplementation implements Client, Serializable{

    public static void main(String[] args) throws RemoteException, NotBoundException, NoResourceMatch {
        Client client = new ClientImplementation();
        UnicastRemoteObject.exportObject(client, 9090);
        Registry registry = LocateRegistry.getRegistry(8080);
        registry.rebind("game_client", client);
        GameServer gameServer = (GameServer) registry.lookup("game_server");
        gameServer.register(client);
        client.joinServer(gameServer, "Endi", PlayerColor.BLUE);
    }

    public ClientImplementation() {
    }

    @Override
    public void joinServer(GameServer s, String playerName, PlayerColor playerColor) throws RemoteException, NoResourceMatch {
        s.join(playerName, playerColor);
        return;
    }

    @Override
    public void ping() {
        System.out.println("YAY! Joined!");
    }

}
