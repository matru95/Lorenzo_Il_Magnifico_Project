package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.server.Server;
import it.polimi.ingsw.gc31.server.GameServer;
import it.polimi.ingsw.gc31.server.SocketThread;
import it.polimi.ingsw.gc31.client.Client;
import it.polimi.ingsw.gc31.client.SocketClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Controller {
    private GameInstance model;
    private List<Client> views;
    private Server server;
    //TODO DOCUMENTAZIONE
    /**
     * Constructor of Controller
     * @param model
     * @param views
     * @param server
     */
    public Controller(GameInstance model, List<Client> views, Server server) {
        this.model = model;
        this.views = views;
        this.server = server;
    }

    /**
     * Returning the game state.
     * @return Map
     */
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

    /**
     * Update a client.
     * @param client The client to update.
     * @throws IOException
     * @throws InterruptedException
     */
    protected void updateClient(Client client) throws IOException, InterruptedException {
        Map<String, String> payload = getGameState();
        ServerMessage request = new ServerMessage(ServerMessageEnum.UPDATE, payload);

        if(client.getClass() == SocketClient.class) {
            GameServer gameServer = (GameServer) getServer();
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

    /**
     * Update the Clients for this controller.
     * @throws IOException
     * @throws InterruptedException
     */
    protected abstract void updateClients() throws IOException, InterruptedException;

    /**
     * Get the server this controller is connected to.
     * @return Server
     */
    public Server getServer() {
        return server;
    }

}
