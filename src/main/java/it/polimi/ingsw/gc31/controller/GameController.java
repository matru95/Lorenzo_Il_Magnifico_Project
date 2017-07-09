package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.client.SocketClient;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.permanent.MalusEnum;
import it.polimi.ingsw.gc31.model.states.GameAgeState;
import it.polimi.ingsw.gc31.model.states.State;
import it.polimi.ingsw.gc31.model.states.TurnEndState;
import it.polimi.ingsw.gc31.model.states.TurnState;
import it.polimi.ingsw.gc31.server.Server;
import it.polimi.ingsw.gc31.client.Client;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class GameController extends Controller implements Runnable{
    private ActionController actionController;
    private Thread messageThread;

    public GameController(GameInstance model, List<Client> views, Server server) {
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
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                endTurn();
            }
            age++;
            endAge();
            turn = 1;
        }


    }


    private void endAge() {
        GameInstance gameInstance = super.getModel();

        State gameAgeState = new GameAgeState();
        gameInstance.setState(gameAgeState);

        gameAgeState.doAction(gameInstance);

        Map<String, ServerMessage> messages = ((GameAgeState) gameAgeState).getServerMessages();

        for(Map.Entry<String, ServerMessage> messageEntry: messages.entrySet()) {

            System.out.println("Sending excommunication message");
            try {
                Client client = actionController.getClientFromPlayerID(messageEntry.getKey());
                getServer().sendMessageToClient(client, messageEntry.getValue());

                if(client.getClass() == SocketClient.class) {

                    synchronized (this) {
                        this.wait();
                    }
                }
            }  catch (RemoteException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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

        updateClients();

        Player[] orderedPlayers = ((TurnState) turnState).getOrderedPlayers();
        Set<Player> playersWithMalus = new LinkedHashSet<>();

//      Do player actions here
        for(index=0; index<=3; index++) {
            System.out.println("Starting mini-turn: "+index);

            for(Player player: orderedPlayers) {

                if(player.hasMalus(MalusEnum.FIRSTACTIONMALUS) && !playersWithMalus.contains(player)) {
                    playersWithMalus.add(player);
                } else {

                    actionController.setPlayer(player);
                    Thread actionThread = new Thread(actionController);
                    actionThread.start();

                    synchronized (this) {
                        this.wait();
                    }
                }

                updateClients();
            }
        }

        for(Player player: playersWithMalus) {
            actionController.setPlayer(player);
            Thread actionThread = new Thread(actionController);
            actionThread.start();

            synchronized (this) {
                this.wait();
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
