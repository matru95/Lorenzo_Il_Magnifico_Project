package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameActionController;
import it.polimi.ingsw.gc31.controller.GameInstanceController;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.model.FamilyMember;
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
    private Map<UUID, List<Client>> clients;
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
        this.clients = new HashMap<>();
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
    public void join(UUID playerID, String playerName, PlayerColor color) throws RemoteException, NoResourceMatch {
        Player player = new Player(playerID, playerName, color);
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
    public UUID register(Client client, String playerName, PlayerColor playerColor) throws RemoteException, NoResourceMatch {
        UUID playerID = UUID.randomUUID();
        List<Client> gameClients = this.clients.get(openGameID);

        this.join(playerID, playerName, playerColor);
        gameClients.add(client);

        client.ping();
        return playerID;
    }

    @Override
    public Map<String, String> sendData(Map<String, String> JSONData) throws RemoteException {
        String requestType = JSONData.get("requestType");

        switch (requestType) {
            case "getGameStateData":
                String gameID = JSONData.get("gameID");
                return getGameState(gameID);
            case "movementAction":
                return processMovementAction(JSONData);
            default:
                return null;
        }
    }


    private Map<String, String> processMovementAction(Map<String, String> JSONData) throws NoResourceMatch {
        String gameID = JSONData.get("gameID");

        GameInstance gameInstance = games.get(UUID.fromString(gameID));
        List<Client> gameClients = clients.get(gameInstance.getInstanceID());


        Controller controller = new GameActionController(gameInstance, gameClients);
        ((GameActionController) controller).movementAction(JSONData);

    }

    private Map<String, String> getGameState(String gameID) {
        GameInstance gameInstance = games.get(UUID.fromString(gameID));
        Map<String, String> response = new HashMap<>();

        response.put("gameState", gameInstance.toString());
        response.put("gameBoard", gameInstance.getGameBoard().toString());

        return response;
    }


    @Override
    public void leave(UUID playerID) throws RemoteException {

    }
}
