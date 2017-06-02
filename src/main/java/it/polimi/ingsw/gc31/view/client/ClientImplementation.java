package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.server.rmiserver.GameServer;
import it.polimi.ingsw.gc31.server.rmiserver.GameServerImpl;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class ClientImplementation implements Client{

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8080);
        GameServer gameServer = (GameServer) registry.lookup("game_server");
        Client client = new ClientImplementation();
        client.joinServer((GameServer) gameServer);
    }

    public ClientImplementation() {

    }

    @Override
    public void joinServer(GameServer s) throws RemoteException {
        s.join(UUID.randomUUID(), "Endi", PlayerColor.BLUE, UUID.randomUUID());
    }

}
