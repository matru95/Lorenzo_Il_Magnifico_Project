package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.messages.*;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.server.rmiserver.GameServer;
import it.polimi.ingsw.gc31.view.GameView;
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
    private transient GameView view;
    private UUID gameID;

    public static void main(String[] args) throws IOException, NotBoundException, NoResourceMatch, InterruptedException, ClassNotFoundException {

        Registry registry = LocateRegistry.getRegistry(8080);
        GameServer gameServer = (GameServer) registry.lookup("game_server");

        Client client = new RMIClient();

        client.joinServer(gameServer, "Matteo", PlayerColor.BLUE);
    }

    public RMIClient() throws RemoteException {
        super();
    }

    @Override
    public void joinServer(GameServer s, String playerName, PlayerColor playerColor) throws IOException, NoResourceMatch, InterruptedException {
        s.register(this, playerName, playerColor);
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
    public String getSocketClientID() {
        return null;
    }

    @Override
    public void setPlayerID(String playerID) {
        this.playerID = UUID.fromString(playerID);
    }

}
