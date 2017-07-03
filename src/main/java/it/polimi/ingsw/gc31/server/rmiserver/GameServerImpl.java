package it.polimi.ingsw.gc31.server.rmiserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.controller.ActionController;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.messages.*;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.view.client.Client;
import it.polimi.ingsw.gc31.view.client.SocketClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServerImpl extends UnicastRemoteObject implements GameServer{
    private transient Map<UUID, GameController> games;
    private UUID openGameID;
    private Map<UUID, List<Client>> clients;
    private List<SocketThread> socketThreads;
    private transient Timer timer;
    ObjectMapper mapper;

    public static void main(String[] args) throws IOException {
        System.out.println("Constructing server implementation");
        GameServerImpl gameServer = new GameServerImpl();
        gameServer.startRMI();
        gameServer.startSocket();


    }

    public GameServerImpl() throws RemoteException {
        this.games = new HashMap<>();
        this.clients = new HashMap<>();
        this.openGameID = null;
        this.timer = null;
        this.mapper = new ObjectMapper();
        this.socketThreads = new ArrayList<>();
    }

    private void startSocket() throws IOException {
        ServerSocket server = null;
        Socket socket = null;
        boolean condition = true;

        try {
            server = new ServerSocket(29999);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("SOC Waiting for connection ");

        while (condition) {
            try {
                socket = server.accept();

                SocketThread socketThread = new SocketThread(socket, this);
                Thread clientSocketThread = new Thread(socketThread);
                clientSocketThread.start();
                socketThreads.add(socketThread);
            } catch (IOException e) {
                e.printStackTrace();
                condition = false;
            }
        }

        socketThreads = null;
        server.close();
        System.out.println("Socket connection closed");

    }

    private void startRMI() throws RemoteException {
        System.out.println("Binding server implementation to registry...");
        LocateRegistry.createRegistry(8080);
        Registry registry = LocateRegistry.getRegistry(8080);
        registry.rebind("game_server", this);

        System.out.println("Waiting for invocation from clients");
    }

    @Override
    public Map<UUID, GameController> getGames() throws RemoteException {

        return games;
    }

    @Override
    public void join(UUID playerID, String playerName, PlayerColor color, Client client) throws IOException, NoResourceMatch, InterruptedException {
        System.out.println("Player id: " + playerID);
        Player player = new Player(playerID, playerName, color);
        GameController openGame;

        if(this.openGameID != null) {
            joinExistingGame(player, client);
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                    try {
                        startGame();
                    } catch (NoResourceMatch noResourceMatch) {
                        noResourceMatch.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                };

            timer.schedule(timerTask, 5000);
        } else {
            System.out.println("Creating new game");
            createNewGame(player);
        }

    }

    private void createNewGame(Player player) {
        GameInstance openGame;
        GameController game;

        openGame = new GameInstance(UUID.randomUUID());
        this.openGameID = openGame.getInstanceID();

        game = new GameController(openGame, new ArrayList<>(), this);
        openGame.addPlayer(player);

        GameBoard gameBoard = new GameBoard(openGame);
        openGame.setGameBoard(gameBoard);
        player.setGameBoard(gameBoard);

        games.put(openGameID, game);
        clients.put(openGameID, game.getViews());
    }

    private void joinExistingGame(Player player, Client client) throws NoResourceMatch, IOException, InterruptedException {
        GameController openGame = games.get(openGameID);
        openGame.addPlayer(player, client);

        GameBoard gameBoard = openGame.getModel().getGameBoard();
        player.setGameBoard(gameBoard);

        if(openGame.getModel().getNumOfPlayers() == 4) {
            startGame();
        }
    }

    private void startGame() throws NoResourceMatch, IOException, InterruptedException {
        System.out.println("starting game");
        GameController openGame = games.get(openGameID);
        openGameID = null;
        timer.cancel();

        (new Thread(openGame)).start();
    }

    @Override
    public Map<String, String> register(Client client, String playerName, PlayerColor playerColor) throws IOException, NoResourceMatch, InterruptedException {
        Map<String, String> payload = new HashMap<>();
        UUID playerID = UUID.randomUUID();


        this.join(playerID, playerName, playerColor, client);

        List<Client> gameClients = this.clients.get(openGameID);

        if(client.getClass() == SocketClient.class) {
            ((SocketClient) client).setPlayerID(playerID.toString());
            ((SocketClient) client).setGameID(openGameID.toString());
        }

        payload.put("playerID", playerID.toString());
        payload.put("gameID", openGameID.toString());

        gameClients.add(client);

        client.ping();
        return payload;
    }

    public Map<String, String> send(ClientMessage request) throws IOException, NoResourceMatch, InterruptedException {
        ClientMessageEnum requestType = request.getClientMessageType();
        Map<String, String> payload = new HashMap<>();

        switch (requestType) {
            case MOVE:
                payload = request.getPayload();
                String gameID = request.getGameID();
                String playerID = request.getPlayerID();

                return processMovementAction(gameID, playerID, payload);
            case REGISTER:
                payload = request.getPayload();
                String playerName = payload.get("playerName");
                String playerColor = payload.get("playerColor");
                Client client = request.getClient();

                return register(client, playerName, PlayerColor.valueOf(playerColor.toUpperCase()));


        }
        return null;

    }


    private Map<String, String> processMovementAction(String gameID, String playerID, Map<String, String> payload) throws NoResourceMatch, IOException, InterruptedException {
        UUID gameInstanceID = UUID.fromString(gameID);

        GameController game = games.get(gameInstanceID);
        ActionController actionController = (ActionController) game.getActionController();

        synchronized (actionController) {
            actionController.setMovementReceived(true);
            System.out.println("notifying");

            actionController.notify();
        }

        actionController.movementAction(playerID, payload);

        return null;
    }

    @Override
    public void leave(UUID playerID) throws RemoteException {

    }

    public SocketThread getSocketByID(String playerID) {
        System.out.println(playerID);
        for(SocketThread socketThread: socketThreads) {
            if(socketThread.getPlayerID().equals(playerID)) {

                return socketThread;
            }
        }

        return null;
    }
}
