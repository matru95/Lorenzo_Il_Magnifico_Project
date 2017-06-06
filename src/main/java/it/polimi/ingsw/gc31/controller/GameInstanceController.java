package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;

import java.util.ArrayList;
import java.util.Map;

public class GameInstanceController implements GameInstanceControllerInterface {
    private GameInstance model;

    public GameInstanceController(GameInstance model) {
        this.model = model;
    }

    public void startGame() {
        this.model.playGame();
    }

    public int getGameTurn() {

        return this.model.getTurn();
    }

    public int getGameAge() {

        return this.model.getAge();
    }

    public ArrayList<Player> getPlayers() {

        return this.model.getPlayers();
    }

    @Override
    public void getDataFromView(Map data) {

        return;
    }

    @Override
    public void sendDataToView(Map data) {

        return;
    }
}
