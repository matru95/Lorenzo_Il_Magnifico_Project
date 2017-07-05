package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.model.states.State;
import it.polimi.ingsw.gc31.model.states.TurnEndState;
import it.polimi.ingsw.gc31.model.states.TurnState;
import it.polimi.ingsw.gc31.server.GameServer;
import it.polimi.ingsw.gc31.view.client.Client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class GameController extends Controller implements Runnable{
    private ActionController actionController;

    public GameController(GameInstance model, List<Client> views, GameServer server) {
        super(model, views, server);
        this.actionController = new ActionController(model, views, this, server);
    }

    @Override
    public void run() {
//      Start the game
        GameInstance gameInstance = super.getModel();
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

                System.out.println("Turn ended");

                try {
                    endTurn();
                } catch (NoResourceMatch noResourceMatch) {
                }
            }
            turn = 1;
        }


    }

    private void endTurn() throws NoResourceMatch {
        GameInstance gameInstance = super.getModel();

        State turnEndState = new TurnEndState();
        gameInstance.setState(turnEndState);

        turnEndState.doAction(gameInstance);
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

                actionThread.interrupt();
                updateClients();
            }
        }

    }


    public void addPlayer(Player player, Client client) throws RemoteException {
        client.setPlayerID(player.getPlayerID().toString());
        super.getModel().addPlayer(player);
        super.getViews().add(client);
    }

    @Override
    protected void updateClients() throws NoResourceMatch, IOException, InterruptedException {
        List<Client> clients = super.getViews();

        for(Client client: clients) {
            super.updateClient(client);
        }
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
