package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.view.client.Client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameServerImpl extends UnicastRemoteObject implements GameServer{
    private Map<UUID, GameInstance> games;
    private UUID openGameID;
    private ArrayList<Client> clients;

    public static void main(String[] args) throws RemoteException {
        System.out.println("Constructing server implementation");
        GameServerImpl gameServer = new GameServerImpl();

        System.out.println("Binding server implementation to registry...");
        LocateRegistry.createRegistry(8080);
        Registry registry = LocateRegistry.getRegistry(8080);
        registry.rebind("game_server", gameServer);

        System.out.println("Waiting for invocation from clients");
    }

    public GameServerImpl() throws RemoteException {
        this.games = new HashMap<>();
        this.clients = new ArrayList<>();
        this.openGameID = null;
    }

    @Override
    public void createGame() throws NoResourceMatch, RemoteException {
        GameInstance gameInstance = new GameInstance(UUID.randomUUID());
        games.put(gameInstance.getInstanceID(), gameInstance);
    }

    @Override
    public Map<UUID, GameInstance> getGames() throws RemoteException {

        return games;
    }

    @Override
    public GameInstance getGame(UUID instanceID) throws RemoteException {

        return null;
    }

    @Override
    public GameInstance join(String playerName, PlayerColor color) throws RemoteException, NoResourceMatch {
        Player player = new Player(UUID.randomUUID(), playerName, color);
        GameInstance openGame;

        if(this.openGameID != null) {
            openGame = games.get(openGameID);
            openGame.addPlayer(player);
            player.setGameBoard(openGame.getGameBoard());
        } else {
            openGame = new GameInstance(UUID.randomUUID());
            this.openGameID = openGame.getInstanceID();
            openGame.addPlayer(player);
            GameBoard gameBoard = new GameBoard(openGame);
            openGame.setGameBoard(gameBoard);
            player.setGameBoard(gameBoard);
        }

        return openGame;
    }

    @Override
    public void register(Client client) throws RemoteException {
        this.clients.add(client);
        return;
    }

    @Override
    public void leave(UUID playerID) throws RemoteException {

    }
}
