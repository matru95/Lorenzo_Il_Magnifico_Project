package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.exceptions.MovementInvalidException;
import it.polimi.ingsw.gc31.messages.ClientMessageEnum;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Parchment;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.board.TowerSpaceWrapper;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.Exchange;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.effects.ExchangeEffect;
import it.polimi.ingsw.gc31.model.parser.SettingsParser;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.server.GameServer;
import it.polimi.ingsw.gc31.client.Client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

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

    public ActionController(GameInstance model, List<Client> clients, GameController gameController, GameServer server) {
        super(model, clients, server);
        SettingsParser parser = new SettingsParser("src/config/Settings.json");

        this.waitingTime = parser.getPlayerWaitTime();
        this.movementReceived = false;
        this.gameController = gameController;
    }

    public void movementAction(String playerID, Map<String, String> movementData) throws IOException, InterruptedException {
//      this.movementReceived is true at this point

        GameInstance game = super.getModel();
        Client client = getClientFromPlayerID(playerID);
        Player player = game.getPlayerFromId(UUID.fromString(playerID));
        String familyMemberColor = movementData.get("diceColor");
        String positionID = movementData.get("positionID");

        DiceColor realFamilyMemberColor = DiceColor.valueOf(familyMemberColor);
        FamilyMember familyMember = player.getSpecificFamilyMember(realFamilyMemberColor);

        SpaceWrapper position = game.getGameBoard().getSpaceById(Integer.valueOf(positionID));
        Integer servantsToPay = Integer.valueOf(movementData.get("servantsToPay"));

        if(servantsToPay < player.getRes().get(ResourceName.SERVANTS).getNumOf()) {
            servantsToPay = player.getRes().get(ResourceName.SERVANTS).getNumOf();
        }

        try {
            List<ServerMessage> messages = familyMember.moveToPosition(position, servantsToPay);
            Card nullCard = new Card(null, null, 0, super.getModel().getAge());

            if(position.getClass() == TowerSpaceWrapper.class) {

                ((TowerSpaceWrapper) position).setCard(nullCard);
            }

            for(ServerMessage message: messages) {

                if(message != null) {

                    synchronized (this) {
                        sendMessage(message, client);
                    }
                }
            }


            this.movementReceived = false;

            synchronized (gameController) {
                gameController.notify();
            }

        } catch (MovementInvalidException e) {
            ServerMessage request = new ServerMessage();

            this.endTime = System.currentTimeMillis();
            this.waitingTime = waitingTime - (endTime - startTime);

            request.setMessageType(ServerMessageEnum.MOVEMENTFAIL);
            sendMessage(request, client);

            waitForMove(UUID.fromString(playerID), getClientFromPlayerID(playerID));

        }

    }

    protected void sendMessage(ServerMessage request, Client client) throws InterruptedException {
//      Destroy thread if exists
        if(this.messageThread != null){
            this.messageThread.interrupt();
        }

        messageThread = new Thread(() -> {
            try {
                System.out.println("Sending message: " + request.getMessageType());
                super.getServer().sendMessageToClient(client, request);
            }  catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        messageThread.start();

        if(request.getMessageType() != ServerMessageEnum.MOVEREQUEST && request.getMessageType() != ServerMessageEnum.MOVEMENTFAIL && request.getMessageType() != ServerMessageEnum.TIMEOUT) {
            synchronized (this) {
                this.wait();
            }
            System.out.println("Releasing thread");
        }
    }

    @Override
    protected void updateClients() throws IOException, InterruptedException {
        List<Client> clients = super.getViews();

        for(Client client: clients) {
            super.updateClient(client);
        }
    }

    private Client getClientFromPlayerID(String playerID) throws RemoteException {

        for(Client client: super.getViews()) {
            String clientID = client.getPlayerID();
            if(clientID.equals(playerID)) {
                return client;
            }
        }

        return null;
    }


    @Override
    public void run() {
        UUID playerID = player.getPlayerID();

        ServerMessage request = new ServerMessage();
        request.setMessageType(ServerMessageEnum.MOVEREQUEST);
        Client client = null;

        try {
            client = getClientFromPlayerID(playerID.toString());
        } catch (RemoteException e) {
        }

        try {
            sendMessage(request, client);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.waitingMessageType = ClientMessageEnum.MOVE;
        this.playerWaitingFromID = playerID.toString();

        this.startTime = System.currentTimeMillis();
        try {
            waitForMove(playerID, client);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void waitForMove(UUID playerID, Client client) throws InterruptedException, IOException {
        Map<String, String> payload = new HashMap<>();

        synchronized (this) {
            this.wait(waitingTime);
        }

        //      Check if a movement was made
        if(!this.movementReceived) {
            System.out.println("no movement made");

            payload.put("playerID", playerID.toString());
            ServerMessage timeOutRequest = new ServerMessage(ServerMessageEnum.TIMEOUT, payload);
            sendMessage(timeOutRequest, client);

            synchronized (gameController) {
                System.out.println("Notifying game controller");
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

    public void parchmentAction(String playerID, Map<String, String> payload) {
        List<Parchment> parchmentsToExecute = new ArrayList<>();
        GameInstance gameInstance = super.getModel();

        for(Map.Entry<String, String> singleParchmentEntry: payload.entrySet()) {
            parchmentsToExecute.add(gameInstance.getParchmentByID(singleParchmentEntry.getValue()));
        }

        for(Parchment parchment: parchmentsToExecute) {
            parchment.execParchment(player);
        }


    }

    public void costChoiceAction(String playerID, Map<String, String> payload) {
        String cardID = payload.get("cardID");
        int cardCostChoice = Integer.valueOf(payload.get("cardCostChoice"));
        Card myCard = player.getCardByID(Integer.valueOf(cardID));
        Map<ResourceName, Resource> costToPay = myCard.getCost().get(cardCostChoice - 1);
        Map<ResourceName, Resource> playerResources = player.getRes();

        for(Map.Entry<ResourceName, Resource> singleResourceEntry: costToPay.entrySet()) {
            ResourceName singleResourceName = singleResourceEntry.getKey();
            int amount = singleResourceEntry.getValue().getNumOf();
            playerResources.get(singleResourceName).subNumOf(amount);
        }
    }

    public void freeCardChoiceAction(String playerID, Map<String, String> payload) {
        String cardID = payload.get("cardID");
        List<Card> cards = super.getModel().getGameBoard().getCards();

        for(Card card: cards) {
            if(card.getCardID() == Integer.valueOf(cardID)) {
                card.execInstantEffect(player);
                player.addCard(card);
            }
        }
    }

    public void exchangeChoiceAction(String playerID, Map<String, String> payload) {
        List<Card> cards = player.getAllCardsAsList();

        for(Map.Entry<String, String> singleChoiceEntry: payload.entrySet()) {
            int cardID = Integer.valueOf(singleChoiceEntry.getKey());
            int choice = Integer.valueOf(singleChoiceEntry.getValue());

            for(Card card: cards) {
                if(card.getCardID() == cardID) {
                    List<Effect> normalEffects = card.getNormalEffects();

                    for(Effect effect: normalEffects) {
                        if(effect.getClass() == ExchangeEffect.class) {

                            ServerMessage message = processExchange(((ExchangeEffect) effect).getExchanges(), choice);

                            try {
                                sendMessage(message, getClientFromPlayerID(playerID));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private ServerMessage processExchange(List<Exchange> exchanges, int choice) {
        Exchange exchange = exchanges.get(choice+1);

        ServerMessage message = exchange.exec(player);

        return message;
    }

}
