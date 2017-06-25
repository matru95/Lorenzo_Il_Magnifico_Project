package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.messages.*;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.server.rmiserver.GameServer;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientImplementation implements Client, Serializable{
    private UUID playerID;
    private GameServer server;

    public static void main(String[] args) throws RemoteException, NotBoundException, NoResourceMatch {
        for(int i=8081; i<8085; i++) {

            Registry registry = LocateRegistry.getRegistry(8080);
            GameServer gameServer = (GameServer) registry.lookup("game_server");

            Client client = new ClientImplementation();
            UnicastRemoteObject.exportObject(client, i);
            registry.rebind("game_client", client);

            client.joinServer(gameServer, "Endi", PlayerColor.BLUE);
        }
    }

    public ClientImplementation() {
    }

    @Override
    public void joinServer(GameServer s, String playerName, PlayerColor playerColor) throws RemoteException, NoResourceMatch {
        UUID playerID = s.register(this, playerName, playerColor);

        this.server = s;
        this.playerID = playerID;
    }

    @Override
    public void ping() {
        System.out.println("YAY! Joined!");
    }

    @Override
    public UUID getPlayerID() {
        return playerID;
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

    @Override
    public void setPlayerID(UUID playerID) {
        this.playerID = playerID;
    }

    private void processAction(ActionMessage message) throws NoResourceMatch, IOException {
        ActionType actionType = message.getActionType();
        BasicMessage basicRequest = new BasicMessage(RequestType.ACTION);

        switch (actionType) {
            case UPDATE:
                Message request = new ActionMessage(basicRequest, ActionType.UPDATE);
                Map<String, String> gameStateData =  server.sendData(request);
                break;
            default:
                break;
//              TODO handle updating the view
        }

    }

}
