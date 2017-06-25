package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.messages.*;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.server.rmiserver.GameServer;
import it.polimi.ingsw.gc31.view.GameView;
import it.polimi.ingsw.gc31.view.cli.GameViewCLI;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.UUID;

public class ClientImplementation implements Client, Serializable{
    private transient UUID playerID;
    private GameServer server;
    private GameView view;
    private UUID gameID;

    public static void main(String[] args) throws IOException, NotBoundException, NoResourceMatch {

        Registry registry = LocateRegistry.getRegistry(8080);
        GameServer gameServer = (GameServer) registry.lookup("game_server");

        Client client = new ClientImplementation();
        UnicastRemoteObject.exportObject(client, 8081);
        registry.rebind("game_client", client);

        client.joinServer(gameServer, "Endi", PlayerColor.BLUE);
    }

    public ClientImplementation() {
    }

    @Override
    public void joinServer(GameServer s, String playerName, PlayerColor playerColor) throws IOException, NoResourceMatch {
        Map<String, UUID> payload = s.register(this, playerName, playerColor);

        this.server = s;
        this.playerID = payload.get("playerID");
        this.gameID = payload.get("gameID");


        view = new GameViewCLI(playerID);
    }

    @Override
    public void ping() {
        System.out.println("YAY! Joined!");
    }


    @Override
    public void send(Message request) throws NoResourceMatch, IOException {
        RequestType requestType = request.getRequestType();

        if(requestType == RequestType.ACTION) {
            ActionMessage actionMessage = (ActionMessage) request;
            ActionType actionType = actionMessage.getActionType();

            processAction(actionMessage);
        } else if(requestType == RequestType.FAIL) {
            FailMessage failMessage = (FailMessage) request;
            FailType failType = failMessage.getFailType();

            processFail(failMessage);
        }

    }

    private void processFail(FailMessage message) {
        String failMessage = message.getMessage();

    }

    private void processAction(ActionMessage message) throws NoResourceMatch, IOException {
        ActionType actionType = message.getActionType();

        switch (actionType) {
            case UPDATE:
                requestUpdate();
                break;
            default:
                break;
        }

    }

    private void requestUpdate() throws IOException, NoResourceMatch {
        BasicMessage basicRequest = new BasicMessage(RequestType.ACTION);
        Message request = new ActionMessage(basicRequest, ActionType.UPDATE);
        ActionMessage message = (ActionMessage) request;
        message.setGameID(gameID.toString());
        Map<String, String> gameState = server.sendData(message);

        view.update(gameState);
    }

}
