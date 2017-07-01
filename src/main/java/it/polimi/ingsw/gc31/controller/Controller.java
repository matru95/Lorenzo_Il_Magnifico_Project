package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.view.client.Client;

import java.io.IOException;
import java.util.List;


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

    protected abstract void updateClients() throws NoResourceMatch, IOException, InterruptedException;
}
