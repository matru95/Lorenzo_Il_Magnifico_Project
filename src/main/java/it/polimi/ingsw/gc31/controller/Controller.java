package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.server.rmiserver.GameServer;
import it.polimi.ingsw.gc31.view.client.Client;

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

    public List<Client> getViews() {
        return views;
    }

    protected abstract void updateClients() throws NoResourceMatch, IOException, InterruptedException;

    public GameServer getServer() {
        return server;
    }
}
