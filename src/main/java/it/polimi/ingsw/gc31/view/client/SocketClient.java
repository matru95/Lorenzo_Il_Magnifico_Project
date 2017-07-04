package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.messages.ClientMessage;
import it.polimi.ingsw.gc31.messages.ClientMessageEnum;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.server.rmiserver.GameServer;
import it.polimi.ingsw.gc31.view.GameView;
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
    private transient GameView view;
    private String socketClientID;

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, NoResourceMatch {
        socket = new Socket("localhost", 29999);
        SocketClient socketClient = new SocketClient();
    }

    public SocketClient() throws IOException, InterruptedException, ClassNotFoundException, NoResourceMatch {
        this.socketClientID = UUID.randomUUID().toString();
        this.joinServer(null, "Endi");
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

        System.out.println("joined");
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
        this.view = new GameViewCLI(UUID.fromString(playerID));

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
            default:
                break;
        }
    }

    private void sendMessageToServer(ClientMessage clientMessage) throws IOException {
        objOut.writeObject(clientMessage);
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
