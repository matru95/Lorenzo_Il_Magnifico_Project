package it.polimi.ingsw.gc31.view.client;

import it.polimi.ingsw.gc31.enumerations.PlayerColor;
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

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, NoResourceMatch {
        socket = new Socket("localhost", 29999);
        SocketClient socketClient = new SocketClient();

    }

    public SocketClient() throws IOException, InterruptedException, ClassNotFoundException, NoResourceMatch {
        System.out.println("Connected: "+ socket);
        this.joinServer(null, "Endi", PlayerColor.RED);
    }


    @Override
    public void joinServer(GameServer s, String playerName, PlayerColor playerColor) throws IOException, NoResourceMatch, InterruptedException, ClassNotFoundException {
        objOut = new ObjectOutputStream(socket.getOutputStream());

        Map<String, String> payload = new HashMap<>();
        payload.put("playerName", playerName);
        payload.put("playerColor", playerColor.toString());
        ClientMessage request = new ClientMessage();
        request.setClient(this);
        request.setClientMessageType(ClientMessageEnum.REGISTER);
        request.setPayLoad(payload);

        objOut.writeObject(request);
        objOut.flush();


        objIn = new ObjectInputStream(socket.getInputStream());

        Map<String, String> response = (Map<String, String>) objIn.readObject();
        this.playerID = response.get("playerID");
        this.gameID = response.get("playerID");
        this.view = new GameViewCLI(UUID.fromString(playerID));

//      Register was a success, tell the server
        ClientMessage successMessage = new ClientMessage(ClientMessageEnum.REGISTERSUCCESS, null, playerID, gameID);
        objOut.writeObject(successMessage);
        objOut.flush();

        getResponses();
    }

    private void getResponses() throws ClassNotFoundException, NoResourceMatch, InterruptedException {
        boolean condition = true;
        while (condition) {
            try {
                objIn = new ObjectInputStream(socket.getInputStream());
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

    @Override
    public void send(ServerMessage request) throws NoResourceMatch, IOException, InterruptedException {
        ServerMessageEnum requestType = request.getMessageType();
        Map<String, String> payload = request.getPayload();
        System.out.println(payload);
        System.out.println(view);

        switch (requestType) {
            case UPDATE:
                view.update(payload);
                break;
            default:
                break;
        }
    }

    @Override
    public UUID getGameID() throws RemoteException {
        return UUID.fromString(gameID);
    }

    @Override
    public UUID getPlayerID() throws RemoteException {
        return UUID.fromString(playerID);
    }

    public String getPlayerIDString() {
        return playerID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
}
