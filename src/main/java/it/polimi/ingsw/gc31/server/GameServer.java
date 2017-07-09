package it.polimi.ingsw.gc31.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.controller.ActionController;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.messages.*;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.client.Client;
import it.polimi.ingsw.gc31.client.SocketClient;
import it.polimi.ingsw.gc31.model.parser.SettingsParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class GameServer extends UnicastRemoteObject implements Server {
    private transient Map<UUID, GameController> games;
    private UUID openGameID;
    private transient Map<UUID, List<Client>> clients;
    private transient List<SocketThread> socketThreads;
    private transient Timer timer;
    private int waitTime;
    ObjectMapper mapper;

    public static void main(String[] args) throws IOException {
        System.out.println("Constructing server implementation");
        GameServer gameServer = new GameServer();
        gameServer.startRMI();
        gameServer.startSocket();


    }

    public GameServer() throws RemoteException {
        this.games = new HashMap<>();
        this.clients = new HashMap<>();
        this.openGameID = null;
        this.timer = null;
        this.mapper = new ObjectMapper();
        this.socketThreads = new ArrayList<>();

        SettingsParser parser = new SettingsParser("src/config/Settings.json");
        this.waitTime = parser.getServerWaitTime();
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
    public void join(UUID playerID, String playerName, Client client) throws IOException, InterruptedException {
        Player player = new Player(playerID, playerName);

        if(this.openGameID != null) {
            joinExistingGame(player, client);
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                    try {
                        startGame();
                    } catch (IOException e) {
                    } catch (InterruptedException e) {
                    }
                }
                };

            timer.schedule(timerTask, 10000);
        } else {
            System.out.println("Creating new game");
            createNewGame(player, client);
        }

    }

    private void createNewGame(Player player, Client client) throws RemoteException {
        GameInstance openGame = null;
        GameController game;

        try {
            openGame = new GameInstance(UUID.randomUUID());
        } catch (IOException e) {
        }
        this.openGameID = openGame.getInstanceID();

        game = new GameController(openGame, new ArrayList<>(), this);
        game.addPlayer(player, client);


        games.put(openGameID, game);
        clients.put(openGameID, game.getViews());
    }

    private void joinExistingGame(Player player, Client client) throws IOException, InterruptedException {
        GameController openGame = games.get(openGameID);
        openGame.addPlayer(player, client);

        if(openGame.getModel().getNumOfPlayers() == 4) {
            startGame();
        }
    }

    private void startGame() throws IOException, InterruptedException {
        GameController openGame = games.get(openGameID);
        openGameID = null;
        timer.cancel();

        (new Thread(openGame)).start();
    }

    @Override
    public void register(Client client, String playerName) throws IOException, InterruptedException {
        Map<String, String> payload = new HashMap<>();
        UUID playerID = UUID.randomUUID();

        this.join(playerID, playerName, client);

        payload.put("playerID", playerID.toString());
        payload.put("gameID", openGameID.toString());

        List<Client> gameClients = this.clients.get(openGameID);
        gameClients.add(client);

//      Tell the client register was a success
        ServerMessage request = new ServerMessage(ServerMessageEnum.REGISTERSUCCESS, payload);
        sendMessageToClient(client, request);

    }

    @Override
    public void send(ClientMessage request) throws IOException, InterruptedException {
        ClientMessageEnum requestType = request.getClientMessageType();
        Map<String, String> payload;
        String playerID;
        String gameID;

        switch (requestType) {
            case MOVE:
                payload = request.getPayload();
                gameID = request.getGameID();
                playerID = request.getPlayerID();

                processMovementAction(gameID, playerID, payload);
                break;

            case REGISTER:
                payload = request.getPayload();
                String playerName = payload.get("playerName");
                Client client = request.getClient();

                register(client, playerName);
                break;

            case PARCHMENTCHOICE:
                payload = request.getPayload();
                playerID = request.getPlayerID();
                gameID = request.getGameID();

                processParchmentChoice(gameID, playerID, payload);
                break;

            case COSTCHOICE:
                payload = request.getPayload();
                playerID = request.getPlayerID();
                gameID = request.getGameID();

                processCostChoice(gameID, playerID, payload);
                break;

            case FREECARDCHOICE:
                payload = request.getPayload();
                playerID = request.getPlayerID();
                gameID = request.getGameID();

                processFreeCardChoice(gameID, playerID, payload);
                break;

            case EXCHANGECHOICES:
                payload = request.getPayload();
                playerID = request.getPlayerID();
                gameID = request.getGameID();

                processExchangeChoices(gameID, playerID, payload);
                break;

            case EXCOMMUNICATIONCHOICE:
                payload = request.getPayload();
                playerID = request.getPlayerID();
                gameID = request.getGameID();
                client = request.getClient();

                processExcommunicationChoice(gameID, playerID, payload, client);
                break;

            case SERVANTSCHOICE:
                payload = request.getPayload();
                playerID = request.getPlayerID();
                gameID = request.getGameID();
                client = request.getClient();

                processServantsChoice(gameID, playerID, payload);
                break;

            default:
                break;
        }

    }

    private void processServantsChoice(String gameID, String playerID, Map<String, String> payload) {
        UUID gameInstanceID = UUID.fromString(gameID);

        GameController gameController = games.get(gameInstanceID);
        ActionController actionController = (ActionController) gameController.getActionController();

        actionController.servantsChoice(playerID, payload);

    }

    private void processExcommunicationChoice(String gameID, String playerID, Map<String, String> payload, Client client) {
        UUID gameInstanceID = UUID.fromString(gameID);

        GameController gameController = games.get(gameInstanceID);
        ActionController actionController = (ActionController) gameController.getActionController();

        actionController.excommunicationChoiceAction(playerID, payload, client);
    }

    private void processExchangeChoices(String gameID, String playerID, Map<String, String> payload) {
        UUID gameInstanceID = UUID.fromString(gameID);

        GameController gameController = games.get(gameInstanceID);
        ActionController actionController = (ActionController) gameController.getActionController();

        synchronized (actionController) {
            actionController.exchangeChoiceAction(playerID, payload);
            actionController.notify();
        }
    }

    private void processFreeCardChoice(String gameID, String playerID, Map<String, String> payload) {
        UUID gameInstanceID = UUID.fromString(gameID);

        GameController gameController = games.get(gameInstanceID);
        ActionController actionController = (ActionController) gameController.getActionController();

        synchronized (actionController) {
            actionController.freeCardChoiceAction(playerID, payload);
            actionController.notify();
        }
    }

    private void processCostChoice(String gameID, String playerID, Map<String, String> payload) {
        UUID gameInstanceID = UUID.fromString(gameID);

        GameController gameController = games.get(gameInstanceID);
        ActionController actionController = (ActionController) gameController.getActionController();

        synchronized (actionController) {
            actionController.costChoiceAction(playerID, payload);
            actionController.notify();
        }
    }

    private void processParchmentChoice(String gameID, String playerID, Map<String, String> payload) {
        UUID gameInstanceID = UUID.fromString(gameID);

        GameController gameController = games.get(gameInstanceID);
        ActionController actionController = (ActionController) gameController.getActionController();

        synchronized (actionController) {
            actionController.parchmentAction(playerID, payload);
            actionController.notify();
        }

    }


    private void processMovementAction(String gameID, String playerID, Map<String, String> payload) throws IOException, InterruptedException {
        UUID gameInstanceID = UUID.fromString(gameID);

        GameController game = games.get(gameInstanceID);
        ActionController actionController = (ActionController) game.getActionController();

        synchronized (actionController) {
            actionController.setMovementReceived(true);
            actionController.notify();
        }

        actionController.movementAction(playerID, payload);
    }

    @Override
    public void sendMessageToClient(Client client, ServerMessage request) throws InterruptedException, IOException {

        if(client.getClass() == SocketClient.class) {

            SocketThread socketThread = getSocketByID(client.getSocketClientID());
            socketThread.send(request);
        } else {

            client.send(request);
        }

    }

    public SocketThread getSocketByID(String socketClientID) {
        for(SocketThread socketThread: socketThreads) {
            if(socketThread.getSocketClientID().equals(socketClientID)) {

                return socketThread;
            }
        }

        return null;
    }
}
