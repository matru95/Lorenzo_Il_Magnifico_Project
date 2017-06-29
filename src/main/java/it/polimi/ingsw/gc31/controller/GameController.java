package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.messages.*;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.states.State;
import it.polimi.ingsw.gc31.model.states.TurnState;
import it.polimi.ingsw.gc31.view.client.Client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class GameController extends Controller implements Runnable{
    private boolean movementReceived;
    private long startTime;
    private long endTime;
    private ClientMessageEnum waitingMessageType;
    private String playerWaitingFromID;
    private long waitingTime;

    public GameController(GameInstance model, List<Client> views) {
        super(model, views);
        this.waitingTime = 60000;
        this.movementReceived = false;
    }

    @Override
    public void updateClients(Message request) {
        return;
    }

    public void movementAction(String playerID, Map<String, String> movementData) throws NoResourceMatch, IOException, InterruptedException {
//      this.movementReceived is true at this point

        ServerMessage request = new ServerMessage();

        String familyMemberColor = movementData.get("diceColor");
        String positionID = movementData.get("positionID");

        GameInstance game = super.getModel();

        Player player = game.getPlayerFromId(UUID.fromString(playerID));

        DiceColor realFamilyMemberColor = DiceColor.valueOf(familyMemberColor);
        FamilyMember familyMember = player.getSpecificFamilyMember(realFamilyMemberColor);

        List<SpaceWrapper> possibleMovements = familyMember.checkPossibleMovements();

        if(isMovementValid(positionID, possibleMovements)) {
            SpaceWrapper position = game.getGameBoard().getSpaceById(Integer.valueOf(positionID));
            Integer servantsToPay = Integer.valueOf(movementData.get("servantsToPay"));

            familyMember.moveToPosition(position, servantsToPay);

            this.movementReceived = false;
        } else {
            this.endTime = System.currentTimeMillis() ;
            //this.waitingTime = waitingTime - (endTime - startTime);

            request.setMessageType(ServerMessageEnum.MOVEMENTFAIL);
            waitForMove(UUID.fromString(playerID), getClientFromPlayerID(UUID.fromString(playerID)));
        }

        updateClients(request);
    }

    private boolean isMovementValid(String positionID, List<SpaceWrapper> possibleMovements) {
        for(SpaceWrapper possibleMovement: possibleMovements) {
            System.out.println(positionID);
            if(possibleMovement.getPositionID() == Integer.valueOf(positionID)) {
                return true;
            }
        }

        return false;
    }

    public void addPlayer(Player player, Client client) {
        super.getModel().addPlayer(player);
        super.getViews().add(client);
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
                    doTurn();
                } catch (NoResourceMatch noResourceMatch) {
                    noResourceMatch.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private void doTurn() throws NoResourceMatch, IOException, InterruptedException {
        State turnState = new TurnState();
        GameInstance gameInstance = super.getModel();
        int index;

        gameInstance.setState(turnState);

        try {
            turnState.doAction(gameInstance);
        } catch (NoResourceMatch noResourceMatch) {
            noResourceMatch.printStackTrace();
        }

        Player[] orderedPlayers = ((TurnState) turnState).getOrderedPlayers();

//      Do player actions here
        for(index=0; index<=3; index++) {

            for(Player player: orderedPlayers) {

                singleAction(player);
            }
        }

    }

    private void singleAction(Player player) throws NoResourceMatch, IOException, InterruptedException {
        UUID playerID = player.getPlayerID();

        ServerMessage request = new ServerMessage();
        request.setMessageType(ServerMessageEnum.MOVEREQUEST);
        Client client = getClientFromPlayerID(playerID);
        client.send(request);

        this.waitingMessageType = ClientMessageEnum.MOVE;
        this.playerWaitingFromID = playerID.toString();


        this.startTime = System.currentTimeMillis();
        waitForMove(playerID, client);
    }

    private synchronized void waitForMove(UUID playerID, Client client) throws InterruptedException, NoResourceMatch, IOException {
        Map<String, String> payload = new HashMap<>();

        this.wait(waitingTime);

        //      Check if a movement was made
        if(!this.movementReceived) {

            System.out.println(waitingTime);
            payload.put("playerID", playerID.toString());
            ServerMessage timeOutRequest = new ServerMessage(ServerMessageEnum.TIMEOUT, payload);

            client.send(timeOutRequest);
        }

        this.endTime = System.currentTimeMillis();
    }

    private Client getClientFromPlayerID(UUID playerID) throws RemoteException {

        for(Client client: super.getViews()) {
            if(client.getPlayerID().equals(playerID)) {
                return client;
            }
        }

        return null;
    }

    private void executeState(State state) {
        GameInstance context = super.getModel();

        try {
            state.doAction(context);
        } catch (NoResourceMatch noResourceMatch) {
            noResourceMatch.printStackTrace();
        }
    }

    public void setMovementReceived(boolean movementReceived) {
        this.movementReceived = movementReceived;
    }
}
