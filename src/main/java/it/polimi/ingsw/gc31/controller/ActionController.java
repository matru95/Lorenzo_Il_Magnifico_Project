package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.exceptions.MovementInvalidException;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.messages.ClientMessageEnum;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.view.client.Client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ActionController extends Controller implements Runnable {
    private GameController gameController;
    private Player player;
    private ClientMessageEnum waitingMessageType;
    private String playerWaitingFromID;
    private long waitingTime;
    private long startTime;
    private boolean movementReceived;
    private long endTime;
    private Thread messageThread;

    public ActionController(GameInstance model, List<Client> clients, GameController gameController) {
        super(model, clients);
        this.waitingTime = 60000;
        this.movementReceived = false;
        this.gameController = gameController;
    }

    public void movementAction(String playerID, Map<String, String> movementData) throws NoResourceMatch, IOException, InterruptedException {
//      this.movementReceived is true at this point

        GameInstance game = super.getModel();
        Player player = game.getPlayerFromId(UUID.fromString(playerID));
        String familyMemberColor = movementData.get("diceColor");
        String positionID = movementData.get("positionID");

        DiceColor realFamilyMemberColor = DiceColor.valueOf(familyMemberColor);
        FamilyMember familyMember = player.getSpecificFamilyMember(realFamilyMemberColor);

        SpaceWrapper position = game.getGameBoard().getSpaceById(Integer.valueOf(positionID));
        Integer servantsToPay = Integer.valueOf(movementData.get("servantsToPay"));

        try {
            ServerMessage message = familyMember.moveToPosition(position, servantsToPay);
            this.movementReceived = false;
            updateClients();

            synchronized (gameController) {
                gameController.notify();
            }

        } catch (MovementInvalidException e) {
            ServerMessage request = new ServerMessage();
            Client client = getClientFromPlayerID(UUID.fromString(playerID));

            this.endTime = System.currentTimeMillis();
            this.waitingTime = waitingTime - (endTime - startTime);

            request.setMessageType(ServerMessageEnum.MOVEMENTFAIL);
            sendMessage(request, client);

            waitForMove(UUID.fromString(playerID), getClientFromPlayerID(UUID.fromString(playerID)));
        }

        updateClients();
    }

    protected void sendMessage(ServerMessage request, Client client) {
//      Destroy thread if exists
        if(this.messageThread != null){
            this.messageThread.interrupt();
        }

        messageThread = new Thread(() -> {
            try {
                client.send(request);
            } catch (NoResourceMatch noResourceMatch) {
                noResourceMatch.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        messageThread.start();
    }
    @Override
    protected void updateClients() throws NoResourceMatch, IOException, InterruptedException {
        List<Client> clients = super.getViews();

        for(Client client: clients) {
            updateClient(client);
        }
    }

    private Client getClientFromPlayerID(UUID playerID) throws RemoteException {

        for(Client client: super.getViews()) {
            if(client.getPlayerID().equals(playerID)) {
                return client;
            }
        }

        return null;
    }

    private void updateClient(Client client) throws NoResourceMatch, IOException, InterruptedException {
        Map<String, String> payload = super.getGameState();
        ServerMessage request = new ServerMessage(ServerMessageEnum.UPDATE, payload);

        client.send(request);
    }

    @Override
    public void run() {
        UUID playerID = player.getPlayerID();

        ServerMessage request = new ServerMessage();
        request.setMessageType(ServerMessageEnum.MOVEREQUEST);
        Client client = null;
        try {
            client = getClientFromPlayerID(playerID);
        } catch (RemoteException e) {
        }

        sendMessage(request, client);

        this.waitingMessageType = ClientMessageEnum.MOVE;
        this.playerWaitingFromID = playerID.toString();

        this.startTime = System.currentTimeMillis();
        try {
            waitForMove(playerID, client);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoResourceMatch noResourceMatch) {
            noResourceMatch.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void waitForMove(UUID playerID, Client client) throws InterruptedException, NoResourceMatch, IOException {
        Map<String, String> payload = new HashMap<>();

        synchronized (this) {
            this.wait(waitingTime);
        }

        System.out.println("stopped waiting " + (System.currentTimeMillis() - startTime));

        //      Check if a movement was made
        if(!this.movementReceived) {
            System.out.println("no movement made");

            payload.put("playerID", playerID.toString());
            ServerMessage timeOutRequest = new ServerMessage(ServerMessageEnum.TIMEOUT, payload);
            messageThread.interrupt();
            client.send(timeOutRequest);
            updateClients();

            synchronized (gameController) {
                System.out.println("notifying game controller");
                gameController.notify();
            }
        }

        this.endTime = System.currentTimeMillis();
    }

    public void setMovementReceived(boolean movementReceived) {
        this.movementReceived = movementReceived;
    }

    protected void setPlayer(Player player) {
        this.player = player;
    }
}
