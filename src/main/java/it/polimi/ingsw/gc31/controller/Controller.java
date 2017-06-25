package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.messages.Message;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.view.client.Client;

import java.util.List;
import java.util.Map;


public abstract class Controller {
    private GameInstance model;
    private List<Client> views;

    public Controller(GameInstance model, List<Client> views) {
        this.model = model;
        this.views = views;
    }

    public GameInstance getModel() {
        return model;
    }

    public List<Client> getViews() {
        return views;
    }

    public abstract void updateClients(Message request);
}
