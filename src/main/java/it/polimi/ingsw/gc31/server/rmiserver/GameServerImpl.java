package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class GameServerImpl extends UnicastRemoteObject implements GameServer{
    ArrayList<GameInstance> games;

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        System.out.println("Constructing server implementation");
        GameServerImpl gameServer = new GameServerImpl();

        System.out.println("Binding server implementation to registry...");
        LocateRegistry.createRegistry(8080);
        Registry registry = LocateRegistry.getRegistry(8080);
        registry.rebind("game_server", gameServer);

        System.out.println("Waiting for invocation from clients");
    }

    public GameServerImpl() throws RemoteException {
        games = new ArrayList<>();
    }

    @Override
    public void createGame() throws NoResourceMatch, RemoteException {
        GameInstance gameInstance = new GameInstance(UUID.randomUUID());
        games.add(gameInstance);
    }

    @Override
    public ArrayList<GameInstance> getGames() throws RemoteException {

        return games;
    }

    @Override
    public GameInstance getGame(UUID instanceID) throws RemoteException {
        return null;
    }

    @Override
    public void join(UUID playerID, String playerName, PlayerColor color, UUID instanceID)  throws RemoteException {

        System.out.println("Player "+playerName+" joined");
        return;
    }

    @Override
    public void leave(UUID playerID) throws RemoteException {

    }
}
