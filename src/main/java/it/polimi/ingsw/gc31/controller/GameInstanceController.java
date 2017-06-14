package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.states.GameEndState;
import it.polimi.ingsw.gc31.model.states.GamePrepState;
import it.polimi.ingsw.gc31.model.states.TurnState;

import java.util.Map;

public class GameInstanceController implements GameInstanceControllerInterface {

    private GameInstance model;

    public GameInstanceController(GameInstance model) {
        this.model = model;
    }

    @Override
    public void getDataFromView(Map data) {

        return;
    }

    @Override
    public void sendDataToView(Map data) {

        return;
    }

    @Override
    public void playGame() {

        model.generatePlayerOrders();

        GamePrepState gamePrepState = new GamePrepState();
        gamePrepState.doAction(model);

        TurnState turnState = new TurnState();
        turnState.doAction(model);

        GameEndState gameEndState = new GameEndState();
        gameEndState.doAction(model);
    }

}
