package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.view.client.Client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class GameServerImpl extends UnicastRemoteObject implements GameServer{
    private Map<UUID, GameInstance> games;
    private UUID openGameID;
    private ArrayList<Client> clients;
    private Timer timer;

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
        this.timer = null;
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
    public void join(String playerName, PlayerColor color) throws RemoteException, NoResourceMatch {
        Player player = new Player(UUID.randomUUID(), playerName, color);
        GameInstance openGame;

        if(this.openGameID != null) {
            System.out.println("Joining existing game");
            joinExistingGame(player);

            if(timer == null) {
                timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        startGame();
                    }
                };

                timer.schedule(timerTask, 5000);
            }
        } else {
            System.out.println("Creating new game");
            createNewGame(player);
        }

    }

    private void createNewGame(Player player) {
        GameInstance openGame;

        openGame = new GameInstance(UUID.randomUUID());
        this.openGameID = openGame.getInstanceID();

        openGame.addPlayer(player);
        GameBoard gameBoard = new GameBoard(openGame);
        openGame.setGameBoard(gameBoard);
        player.setGameBoard(gameBoard);

        games.put(openGameID, openGame);
    }

    private void joinExistingGame(Player player) {
        GameInstance openGame = games.get(openGameID);
        openGame.addPlayer(player);

        GameBoard gameBoard = openGame.getGameBoard();
        player.setGameBoard(gameBoard);

        if(openGame.getNumOfPlayers() == 4) {
            startGame();
        }
    }

    private void startGame() {
        System.out.println("starting game!");
        GameInstance openGame = games.get(openGameID);
        openGameID = null;
        timer.cancel();
        timer.purge();

        (new Thread(openGame)).start();
    }

    @Override
    public void register(Client client, String playerName, PlayerColor playerColor) throws RemoteException, NoResourceMatch {
        this.clients.add(client);
        this.join(playerName, playerColor);
        client.ping();
        return;
    }

    @Override
    public void getData() {
        System.out.println("Pinging server!");
    }

    @Override
    public void leave(UUID playerID) throws RemoteException {

    }
}
