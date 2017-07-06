package it.polimi.ingsw.gc31.client;

import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.messages.ClientMessage;
import it.polimi.ingsw.gc31.messages.ClientMessageEnum;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.server.GameServer;
import it.polimi.ingsw.gc31.view.GameViewCtrl;
import it.polimi.ingsw.gc31.view.cli.GameViewCLI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SocketClient implements Client, Serializable{
    private static Socket socket;
    private transient ObjectInputStream objIn;
    private transient ObjectOutputStream objOut;
    private String playerID;
    private transient String gameID;
    private transient GameViewCtrl view;
    private String socketClientID;
    private String playerName;
    private String serverIP;

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, NoResourceMatch {

    }

    public SocketClient(String serverIP, String playerName, GameViewCtrl view) throws IOException, InterruptedException, ClassNotFoundException, NoResourceMatch {
        this.socketClientID = UUID.randomUUID().toString();
        this.serverIP = serverIP;
        this.playerName = playerName;

        socket = new Socket(serverIP, 29999);
        this.joinServer(null, playerName);
    }


    @Override
    public void joinServer(GameServer s, String playerName) throws IOException, NoResourceMatch, InterruptedException, ClassNotFoundException {
        objOut = new ObjectOutputStream(socket.getOutputStream());

        Map<String, String> payload = new HashMap<>();
        payload.put("playerName", playerName);
        payload.put("socketClientID", socketClientID);
        ClientMessage request = new ClientMessage();
        request.setClient(this);
        request.setClientMessageType(ClientMessageEnum.REGISTER);
        request.setPayLoad(payload);

        objOut.writeObject(request);
        objOut.flush();

        getResponses();
        socket.close();
    }

    private void getResponses() throws ClassNotFoundException, NoResourceMatch, InterruptedException, IOException {
        objIn = new ObjectInputStream(socket.getInputStream());
        boolean condition = true;
        while (condition) {
            try {
                ServerMessage response = (ServerMessage) objIn.readObject();
                send(response);
            } catch (IOException e) {
                e.printStackTrace();
                condition = false;
            }
        }

    }

    @Override
    public void ping() throws RemoteException {

    }

    private void createView(Map<String, String> payload) throws IOException {
        this.playerID = payload.get("playerID");
        this.gameID = payload.get("gameID");
        this.view.setPlayerID(playerID);

//      Register was a success, tell the server
        ClientMessage successMessage = new ClientMessage(ClientMessageEnum.REGISTERSUCCESS, null, playerID, gameID);
        objOut.writeObject(successMessage);
        objOut.flush();
    }

    @Override
    public void send(ServerMessage request) throws NoResourceMatch, IOException, InterruptedException {
        ServerMessageEnum requestType = request.getMessageType();
        Map<String, String> payload = request.getPayload();

        switch (requestType) {
            case REGISTERSUCCESS:
                createView(payload);
                break;
            case UPDATE:
                view.update(payload);
                break;
            case MOVEREQUEST:
                sendMessageToServer(new ClientMessage(ClientMessageEnum.MOVE, view.movementQuery(), playerID, gameID));
                break;
            case MOVEMENTFAIL:
                view.movementFail(payload);
                sendMessageToServer(new ClientMessage(ClientMessageEnum.MOVE, view.movementQuery(), playerID, gameID));
                break;
            case PARCHMENTREQUEST:
                sendMessageToServer(new ClientMessage(ClientMessageEnum.PARCHMENTCHOICE, view.parchmentQuery(payload), playerID, gameID));
                break;
            case EXCOMMUNICATIONREQUEST:
                sendMessageToServer(new ClientMessage(ClientMessageEnum.EXCOMMUNICATIONCHOICE, view.faithQuery(), playerID, gameID));
                break;
            case COSTREQUEST:
                sendMessageToServer(new ClientMessage(ClientMessageEnum.COSTCHOICE, view.costQuery(payload), playerID, gameID));
                break;
            case EXCHANGEREQUEST:
                sendMessageToServer(new ClientMessage(ClientMessageEnum.EXCHANGECHOICES, view.exchangeQuery(payload), playerID, gameID));
                break;
            case FREECARDREQUEST:
                sendMessageToServer(new ClientMessage(ClientMessageEnum.FREECARDCHOICE, view.freeCardQuery(payload), playerID, gameID));
                break;
            default:
                break;
        }
    }

    private void sendMessageToServer(ClientMessage clientMessage) {
        try {
            objOut.writeObject(clientMessage);
            objOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getGameID() throws RemoteException {
        return gameID;
    }

    @Override
    public String getPlayerID() throws RemoteException {
        return playerID;
    }

    @Override
    public String getSocketClientID() {
        return socketClientID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
}
