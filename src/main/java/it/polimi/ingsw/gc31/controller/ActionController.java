package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.client.SocketClient;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.exceptions.MovementInvalidException;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.*;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.board.TowerSpaceWrapper;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.Exchange;
import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.effects.ExchangeEffect;
import it.polimi.ingsw.gc31.model.effects.ParchmentEffect;
import it.polimi.ingsw.gc31.model.effects.boardeffects.HarvestEffect;
import it.polimi.ingsw.gc31.model.effects.boardeffects.ProductionEffect;
import it.polimi.ingsw.gc31.model.parser.SettingsParser;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.states.GameAgeState;
import it.polimi.ingsw.gc31.server.Server;
import it.polimi.ingsw.gc31.client.Client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class ActionController extends Controller implements Runnable {
    private GameController gameController;
    private Player player;
    private long waitingTime;
    private long startTime;
    private boolean movementReceived;
    private long endTime;
    private Thread messageThread;

    /**
     *
     * @param model
     * @param clients
     * @param gameController
     * @param server
     */
    public ActionController(GameInstance model, List<Client> clients, GameController gameController, Server server) {
        super(model, clients, server);
        SettingsParser parser = new SettingsParser("src/config/Settings.json");

        this.waitingTime = parser.getPlayerWaitTime();
        this.movementReceived = false;
        this.gameController = gameController;
    }

    /**
     *
     * @param playerID
     * @param movementData
     * @throws IOException
     * @throws InterruptedException
     */
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

    /**
     *
     * @param request
     * @param client
     * @throws InterruptedException
     */
    protected void sendMessage(ServerMessage request, Client client) throws InterruptedException {
//      Destroy thread if exists
        if(this.messageThread != null){
            this.messageThread.interrupt();
        }

        messageThread = new Thread(() -> {
            try {
                super.getServer().sendMessageToClient(client, request);
            }  catch (IOException e) {
            } catch (InterruptedException e) {
            }
        });

        messageThread.start();

        if(request.getMessageType() != ServerMessageEnum.MOVEREQUEST && request.getMessageType() != ServerMessageEnum.MOVEMENTFAIL && request.getMessageType() != ServerMessageEnum.TIMEOUT) {
            synchronized (this) {
                this.wait();
            }
        }
    }

    @Override
    protected void updateClients() throws IOException, InterruptedException {
        List<Client> clients = super.getViews();

        for(Client client: clients) {
            super.updateClient(client);
        }
    }

    /**
     *
     * @param playerID
     * @return
     * @throws RemoteException
     */
    protected Client getClientFromPlayerID(String playerID) throws RemoteException {

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
            Thread.currentThread().interrupt();
        }

        this.startTime = System.currentTimeMillis();
        try {
            waitForMove(playerID, client);
        } catch (InterruptedException e) {
        }  catch (IOException e) {
        }
    }


    private void waitForMove(UUID playerID, Client client) throws InterruptedException, IOException {
        Map<String, String> payload = new HashMap<>();

        synchronized (this) {
            this.wait(waitingTime);
        }

        //      Check if a movement was made
        if(!this.movementReceived) {

            payload.put("playerID", playerID.toString());
            ServerMessage timeOutRequest = new ServerMessage(ServerMessageEnum.TIMEOUT, payload);
            sendMessage(timeOutRequest, client);

            synchronized (gameController) {
                gameController.notify();
            }
        }

        this.endTime = System.currentTimeMillis();
    }

    /**
     *
     * @param movementReceived
     */
    public void setMovementReceived(boolean movementReceived) {
        this.movementReceived = movementReceived;
    }

    protected void setPlayer(Player player) {
        this.player = player;
    }

    public void parchmentAction(Map<String, String> payload) {
        List<Parchment> parchmentsToExecute = new ArrayList<>();
        GameInstance gameInstance = super.getModel();

        for(Map.Entry<String, String> singleParchmentEntry: payload.entrySet()) {
            parchmentsToExecute.add(gameInstance.getParchmentByID(singleParchmentEntry.getValue()));
        }

        for(Parchment parchment: parchmentsToExecute) {
            parchment.execParchment(player);
        }

    }

    /**
     *
     * @param payload
     */
    public void costChoiceAction(Map<String, String> payload) {
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

    /**
     *
     * @param playerID
     * @param payload
     */
    public void freeCardChoiceAction(String playerID, Map<String, String> payload) {
        String cardID = payload.get("cardID");
        List<Card> cards = super.getModel().getGameBoard().getCards();

        SpaceWrapper position = getModel().getGameBoard().getPositionByCardID(Integer.valueOf(cardID));

        ((TowerSpaceWrapper) position).setCard(new Card(null, null, 0, 0));

        for(Card card: cards) {
            if(card.getCardID() == Integer.valueOf(cardID)) {
                player.addCard(card);

                if(card.getCost().size() > 0) {

                    card.payCosts(player.getRes());
                }

                List<ServerMessage> requests = card.execInstantEffect(player);

                sendMessages(playerID, requests);
            }
        }
    }

    private void sendMessages(String playerID, List<ServerMessage> requests) {
        try {
            Client client = getClientFromPlayerID(playerID);

            for(ServerMessage message: requests) {
                if(message != null) {

                    sendMessage(message, client);
                }
            }
        } catch (RemoteException e) {
        } catch (InterruptedException e) {
        }
    }

    /**
     *
     * @param playerID
     * @param payload
     */
    public void exchangeChoiceAction(String playerID, Map<String, String> payload) {
        List<Card> cards = player.getAllCardsAsList();
        int cardID = Integer.parseInt(payload.get("cardID"));
        int choice = Integer.parseInt(payload.get("choice"));

        if(choice == 0) {
            return;
        } else {

            for(Card card: cards) {

                if(card.getCardID() == cardID) {

                    for(Effect effect: card.getNormalEffects()) {

                        if(effect.getClass() == ExchangeEffect.class) {
                            Exchange exchange = ((ExchangeEffect) effect).getExchanges().get(choice-1);
                            List<Resource> resourcesToPay = exchange.getResourcesToGive();
                            List<Resource> resourcesToReceive = exchange.getResourcesToReceive();
                            int numOfParchments = exchange.getNumOfParchmentsToReceive();

                            player.payResources(resourcesToPay);

                            if(resourcesToReceive.size() > 0) {
                                AddResEffect addResEffect = new AddResEffect(resourcesToReceive);
                                addResEffect.exec(player);
                            }

                            if(numOfParchments > 0) {
                                ParchmentEffect parchmentEffect = new ParchmentEffect(numOfParchments);
                                ServerMessage message = parchmentEffect.exec(player);

                                try {
                                    sendMessage(message, getClientFromPlayerID(playerID));
                                } catch (InterruptedException e) {
                                } catch (RemoteException e) {
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param playerID
     * @param payload
     * @param client
     */
    public void excommunicationChoiceAction(String playerID, Map<String, String> payload, Client client) {

        String choice = payload.get("applyExcommunication");
        Player myPlayer = getModel().getPlayerFromId(UUID.fromString(playerID));
        GameAgeState gameAgeState = (GameAgeState) getModel().getState();
        FaithTile ageFaithTile = gameAgeState.getAgeFaithTile();

        if(choice.equals("YES")) {
            ageFaithTile.execute(myPlayer);
        } else {
            gameAgeState.payFaithPointsForVictoryPoints(myPlayer);
        }

        if(client.getClass() == SocketClient.class) {

            synchronized (gameController) {
                gameController.notify();
            }
        }

        return;
    }

    /**
     *
     * @param playerID
     * @param payload
     */
    public void servantsChoice(String playerID, Map<String, String> payload) {
        String positionType = payload.get("positionType");
        int servantsToPay = Integer.parseInt(payload.get("servantsToPay"));
        int cardValue = Integer.parseInt(payload.get("cardValue"));
        int familyMemberValue = Integer.parseInt(payload.get("familyMemberValue"));
        int movementValue = servantsToPay+cardValue+familyMemberValue;


        List<ServerMessage> messages;

        if(positionType.equals("harvest")) {
            HarvestEffect harvestEffect = new HarvestEffect();
            messages = harvestEffect.exec(player, movementValue, servantsToPay);
        } else {
            ProductionEffect productionEffect = new ProductionEffect();
            messages = productionEffect.exec(player, movementValue, servantsToPay);
        }

        sendMessages(playerID, messages);

        try {
            updateClients();
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }
    }
}
