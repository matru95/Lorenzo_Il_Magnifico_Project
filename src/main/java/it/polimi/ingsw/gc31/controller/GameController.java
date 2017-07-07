package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.states.State;
import it.polimi.ingsw.gc31.model.states.TurnEndState;
import it.polimi.ingsw.gc31.model.states.TurnState;
import it.polimi.ingsw.gc31.server.GameServer;
import it.polimi.ingsw.gc31.client.Client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class GameController extends Controller implements Runnable{
    private ActionController actionController;
    private boolean isFirstUpdate;

    public GameController(GameInstance model, List<Client> views, GameServer server) {
        super(model, views, server);
        this.actionController = new ActionController(model, views, this, server);
        this.isFirstUpdate = true;
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
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Turn ended");

                endTurn();
            }
            turn = 1;
        }


    }

    private void endTurn() {
        GameInstance gameInstance = super.getModel();

        State turnEndState = new TurnEndState();
        gameInstance.setState(turnEndState);

        turnEndState.doAction(gameInstance);
    }

    private void doTurn() throws IOException, InterruptedException {
        State turnState = new TurnState();
        GameInstance gameInstance = super.getModel();
        int index;

        gameInstance.setState(turnState);

        turnState.doAction(gameInstance);
        if(isFirstUpdate) {
            updateClients();
            isFirstUpdate = false;
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

//                actionThread.interrupt();
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
    protected void updateClients() throws IOException, InterruptedException {
        List<Client> clients = super.getViews();

        for(Client client: clients) {
            System.out.println("Updating: "+client.getPlayerID());
            super.updateClient(client);
        }
    }

    private void executeState(State state) {
        GameInstance context = super.getModel();

        state.doAction(context);
    }

    public Controller getActionController() {
        return actionController;
    }

}
