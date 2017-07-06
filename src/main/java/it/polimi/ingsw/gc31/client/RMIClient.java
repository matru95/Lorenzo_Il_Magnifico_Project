package it.polimi.ingsw.gc31.client;

import it.polimi.ingsw.gc31.messages.*;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.server.GameServer;
import it.polimi.ingsw.gc31.view.GameViewCtrl;
import it.polimi.ingsw.gc31.view.GameViewCtrl;
import it.polimi.ingsw.gc31.view.cli.GameViewCLI;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.UUID;

public class RMIClient extends UnicastRemoteObject implements Client, Serializable {

    private transient UUID playerID;
    private transient GameServer server;
    private transient GameViewCtrl view;
    private UUID gameID;

    public RMIClient() throws RemoteException {
    }

    public static void main(String[] args) throws IOException, NotBoundException, NoResourceMatch, InterruptedException, ClassNotFoundException {

        String serverIP = "127.0.0.1";
        Registry registry = LocateRegistry.getRegistry(serverIP, 8080);
        GameServer gameServer = (GameServer) registry.lookup("game_server");

        Client client = new RMIClient();

        client.joinServer(gameServer, "Matteo");
    }

    @Override
    public void joinServer(GameServer s, String playerName) throws IOException, NoResourceMatch, InterruptedException {
        s.register(this, playerName);
        this.server = s;
    }

    @Override
    public void ping() {
        System.out.println("YAY! Joined!");
    }

    private void openView(Map<String, String> payload) {
        this.playerID = UUID.fromString(payload.get("playerID"));
        this.gameID = UUID.fromString(payload.get("gameID"));

        this.view = new GameViewCLI(playerID);
    }

    @Override
    public void send(ServerMessage request) throws NoResourceMatch, IOException, InterruptedException {

        ServerMessageEnum requestType = request.getMessageType();
        Map<String, String> payload = request.getPayload();

        switch (requestType) {
            case REGISTERSUCCESS:
                openView(payload);
                break;
            case UPDATE:
                view.update(payload);
                break;
            case MOVEMENTFAIL:
                view.movementFail(payload);
                server.send(new ClientMessage(ClientMessageEnum.MOVE, view.movementQuery(), playerID.toString(), gameID.toString()));
                break;
            case MOVEREQUEST:
                server.send(new ClientMessage(ClientMessageEnum.MOVE, view.movementQuery(), playerID.toString(), gameID.toString()));
                break;
            case PARCHMENTREQUEST:
                server.send(new ClientMessage(ClientMessageEnum.PARCHMENTCHOICE, view.parchmentQuery(payload), playerID.toString(), gameID.toString()));
                break;
            case EXCOMMUNICATIONREQUEST:
                server.send(new ClientMessage(ClientMessageEnum.EXCOMMUNICATIONCHOICE, view.faithQuery(), playerID.toString(), gameID.toString()));
                break;
            case COSTREQUEST:
                server.send(new ClientMessage(ClientMessageEnum.COSTCHOICE, view.costQuery(payload), playerID.toString(), gameID.toString()));
                break;
            case EXCHANGEREQUEST:
                server.send(new ClientMessage(ClientMessageEnum.EXCHANGECHOICES, view.exchangeQuery(payload), playerID.toString(), gameID.toString()));
                break;
            case FREECARDREQUEST:
                server.send(new ClientMessage(ClientMessageEnum.FREECARDCHOICE, view.freeCardQuery(payload), playerID.toString(), gameID.toString()));
                break;
            default:
                break;
        }

    }

    @Override
    public String getGameID() throws RemoteException {
        return gameID.toString();
    }

    @Override
    public String getPlayerID() throws RemoteException {
        return playerID.toString();
    }

    @Override
    public String getSocketClientID() throws RemoteException {
        return null;
    }

    @Override
    public void setPlayerID(String playerID) throws RemoteException {
        this.playerID = UUID.fromString(playerID);
    }

}
