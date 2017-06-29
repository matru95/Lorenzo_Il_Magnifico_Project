package it.polimi.ingsw.gc31.server.rmiserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.messages.*;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.view.client.Client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class GameServerImpl extends UnicastRemoteObject implements GameServer{
    private Map<UUID, GameController> games;
    private UUID openGameID;
    private Map<UUID, List<Client>> clients;
    private Timer timer;
    ObjectMapper mapper;

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
        this.mapper = new ObjectMapper();
    }

    @Override
    public Map<UUID, GameController> getGames() throws RemoteException {

        return games;
    }

    @Override
    public void join(UUID playerID, String playerName, PlayerColor color, Client client) throws IOException, NoResourceMatch {
        Player player = new Player(playerID, playerName, color);
        GameController openGame;

        if(this.openGameID != null) {
            joinExistingGame(player, client);

            if(timer == null) {
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
                        }
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
        GameController game;

        openGame = new GameInstance(UUID.randomUUID());
        this.openGameID = openGame.getInstanceID();

        game = new GameController(openGame, new ArrayList<>());
        openGame.addPlayer(player);

        GameBoard gameBoard = new GameBoard(openGame);
        openGame.setGameBoard(gameBoard);
        player.setGameBoard(gameBoard);

        games.put(openGameID, game);
        clients.put(openGameID, game.getViews());
    }

    private void joinExistingGame(Player player, Client client) throws NoResourceMatch, IOException {
        GameController openGame = games.get(openGameID);
        openGame.addPlayer(player, client);

        GameBoard gameBoard = openGame.getModel().getGameBoard();
        player.setGameBoard(gameBoard);

        if(openGame.getModel().getNumOfPlayers() == 4) {
            startGame();
        }
    }

    private void startGame() throws NoResourceMatch, IOException {
        GameController openGame = games.get(openGameID);
        List<Client> clientsToUpdate = clients.get(openGameID);
        openGameID = null;
        timer.cancel();
        timer.purge();

        (new Thread(openGame)).start();

        updateClients(clientsToUpdate);

    }

    private void updateClients(List<Client> clients) throws NoResourceMatch, IOException {

        for(Client client: clients) {
            updateClient(client);
        }
    }

    private void updateClient(Client client) throws NoResourceMatch, IOException {
        UUID gameID = client.getGameID();
        Map<String, String> payload = getGameState(gameID.toString());
        ServerMessage request = new ServerMessage(ServerMessageEnum.UPDATE, payload);

        client.send(request);
    }

    private Map<String, String> getGameState(String gameID) {
        GameInstance gameInstance = games.get(UUID.fromString(gameID)).getModel();
        Map<String, String> response = new HashMap<>();

        response.put("GameInstance", gameInstance.toString());
        response.put("GameBoard", gameInstance.getGameBoard().toString());

        return response;
    }

    @Override
    public Map<String, UUID> register(Client client, String playerName, PlayerColor playerColor) throws IOException, NoResourceMatch {
        Map<String, UUID> payload = new HashMap<>();
        UUID playerID = UUID.randomUUID();

        this.join(playerID, playerName, playerColor, client);

        List<Client> gameClients = this.clients.get(openGameID);

        payload.put("playerID", playerID);
        payload.put("gameID", openGameID);

        gameClients.add(client);

        client.ping();
        return payload;
    }

    public Map<String, String> send(ClientMessage request) throws IOException, NoResourceMatch {
        ClientMessageEnum requestType = request.getClientMessageType();

        switch (requestType) {
            case MOVE:
                Map<String, String> payload = request.getPayload();
                String gameID = request.getGameID();
                String playerID = request.getPlayerID();

                return processMovementAction(gameID, playerID, payload);
        }
        return null;

    }


    private Map<String, String> processMovementAction(String gameID, String playerID, Map<String, String> payload) throws NoResourceMatch, IOException {
        UUID gameInstanceID = UUID.fromString(gameID);

        GameController game = games.get(gameInstanceID);
        synchronized (game) {
            game.setMovementReceived(true);
            game.notify();
        }

        game.movementAction(playerID, payload);

        return null;
    }



    @Override
    public void leave(UUID playerID) throws RemoteException {

    }
}
