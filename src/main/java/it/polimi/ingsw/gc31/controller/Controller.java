package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.server.GameServer;
import it.polimi.ingsw.gc31.server.GameServerImpl;
import it.polimi.ingsw.gc31.server.SocketThread;
import it.polimi.ingsw.gc31.view.client.Client;
import it.polimi.ingsw.gc31.view.client.SocketClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Controller {
    private GameInstance model;
    private List<Client> views;
    private GameServer server;

    public Controller(GameInstance model, List<Client> views, GameServer server) {
        this.model = model;
        this.views = views;
        this.server = server;
    }

    protected Map<String,String> getGameState() {
        GameInstance gameInstance = getModel();
        Map<String, String> gameState = new HashMap<>();

        gameState.put("GameInstance", gameInstance.toString());
        gameState.put("GameBoard", gameInstance.getGameBoard().toString());

        return gameState;
    }
    public GameInstance getModel() {
        return model;
    }

    protected void updateClient(Client client) throws NoResourceMatch, IOException, InterruptedException {
        Map<String, String> payload = getGameState();
        ServerMessage request = new ServerMessage(ServerMessageEnum.UPDATE, payload);

        if(client.getClass() == SocketClient.class) {
            GameServerImpl gameServer = (GameServerImpl) getServer();
            SocketThread socketThread = gameServer.getSocketByID(client.getSocketClientID());

            if(socketThread != null)
                socketThread.updateClient(payload);
        } else {

            client.send(request);
        }
    }
    public List<Client> getViews() {
        return views;
    }

    protected abstract void updateClients() throws NoResourceMatch, IOException, InterruptedException;

    public GameServer getServer() {
        return server;
    }
}
