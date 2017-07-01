package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.messages.*;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.model.states.State;
import it.polimi.ingsw.gc31.model.states.TurnState;
import it.polimi.ingsw.gc31.view.client.Client;

import java.io.IOException;
import java.util.*;

public class GameController extends Controller implements Runnable{
    private ActionController actionController;

    public GameController(GameInstance model, List<Client> views) {
        super(model, views);
        this.actionController = new ActionController(model, views, this);
    }

    @Override
    public void run() {
//      Start the game
        GameInstance gameInstance = super.getModel();

//      Initiate player order, start game preparation state
        gameInstance.run();

//      Prepare the game
        State gamePrepState = gameInstance.getState();
        executeState(gamePrepState);

        int age = gameInstance.getAge();
        int turn = gameInstance.getTurn();

        while(age <= 3) {

            while (turn <= 2) {
                try {
                    gameInstance.setTurn(turn);

                    doTurn();
                    turn += 1;
                } catch (NoResourceMatch noResourceMatch) {
                    noResourceMatch.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Turn ended");
            turn = 1;
        }


    }

    private void doTurn() throws NoResourceMatch, IOException, InterruptedException {
        State turnState = new TurnState();
        GameInstance gameInstance = super.getModel();
        int index;

        gameInstance.setState(turnState);

        try {
            turnState.doAction(gameInstance);
            updateClients();
        } catch (NoResourceMatch noResourceMatch) {
            noResourceMatch.printStackTrace();
        }

        Player[] orderedPlayers = ((TurnState) turnState).getOrderedPlayers();

//      Do player actions here
        for(index=0; index<=3; index++) {

            for(Player player: orderedPlayers) {

                actionController.setPlayer(player);
                Thread actionThread = new Thread(actionController);
                actionThread.start();

                synchronized (this) {
                    this.wait();
                }

                System.out.println("game controller stopped waiting");
                actionThread.interrupt();
                updateClients();
            }
            System.out.println("Finished mini turn");
        }

    }


    public void addPlayer(Player player, Client client) {
        super.getModel().addPlayer(player);
        super.getViews().add(client);
    }

    @Override
    protected void updateClients() throws NoResourceMatch, IOException, InterruptedException {
        List<Client> clients = super.getViews();

        for(Client client: clients) {
            updateClient(client);
        }
    }

    private void updateClient(Client client) throws NoResourceMatch, IOException, InterruptedException {
        Map<String, String> payload = super.getGameState();
        ServerMessage request = new ServerMessage(ServerMessageEnum.UPDATE, payload);

        client.send(request);
    }


    private void executeState(State state) {
        GameInstance context = super.getModel();

        try {
            state.doAction(context);
        } catch (NoResourceMatch noResourceMatch) {
            noResourceMatch.printStackTrace();
        }
    }

    public Controller getActionController() {
        return actionController;
    }

}
