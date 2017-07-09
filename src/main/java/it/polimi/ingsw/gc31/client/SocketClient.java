package it.polimi.ingsw.gc31.client;

import it.polimi.ingsw.gc31.messages.ClientMessage;
import it.polimi.ingsw.gc31.messages.ClientMessageEnum;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.server.Server;
import it.polimi.ingsw.gc31.view.GameViewCtrl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient implements Client, Serializable {

    private transient Logger logger = Logger.getLogger(SocketClient.class.getName());
    private static Socket socket;
    private transient ObjectInputStream objIn;
    private transient ObjectOutputStream objOut;
    private String playerID;
    private transient String gameID;
    private transient GameViewCtrl view;
    private String socketClientID;
    private String playerName;
    private String serverIP;
    private transient Thread inputThread;

    public SocketClient(String serverIP, String playerName, GameViewCtrl view) throws IOException {
        this.socketClientID = UUID.randomUUID().toString();
        this.serverIP = serverIP;
        this.playerName = playerName;
        this.view = view;

        socket = new Socket(serverIP, 29999);

        new Thread(() -> joinServer(null, playerName)).start();
    }


    @Override
    public void joinServer(Server s, String playerName)  {
        try {
            objOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> payload = new HashMap<>();
        payload.put("playerName", playerName);
        payload.put("socketClientID", socketClientID);
        ClientMessage request = new ClientMessage();
        request.setClient(this);
        request.setClientMessageType(ClientMessageEnum.REGISTER);
        request.setPayLoad(payload);


        try {
            objOut.writeObject(request);
            objOut.flush();
            getResponses();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getResponses() throws ClassNotFoundException, InterruptedException, IOException {
        logger.info("Getting responses");
        objIn = new ObjectInputStream(socket.getInputStream());
        boolean condition = true;
        while (condition) {
            try {
                ServerMessage response = (ServerMessage) objIn.readObject();
                logger.info("Received:"+ response.getMessageType());

                send(response);

                if(response.getMessageType() == ServerMessageEnum.ENDGAME) {
                    condition = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                condition = false;
            }
        }

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

    private void threadCloser() {
        inputThread = null;
    }

    @Override
    public void send(ServerMessage request) throws IOException, InterruptedException {
        ServerMessageEnum requestType = request.getMessageType();
        Map<String, String> payload = request.getPayload();

        switch (requestType) {
            case TIMEOUT:
                view.timeoutAlert();
                break;
            case REGISTERSUCCESS:
                createView(payload);
                break;
            case UPDATE:
                view.update(payload);
                break;
            case MOVEREQUEST:
                inputThread = new Thread(() -> {
                    try {
                        sendMessageToServer(new ClientMessage(ClientMessageEnum.MOVE, view.movementQuery(), playerID, gameID));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                inputThread.start();
                break;
            case MOVEMENTFAIL:
                view.movementFail(payload);
                inputThread = new Thread(() -> {
                    try {
                        sendMessageToServer(new ClientMessage(ClientMessageEnum.MOVE, view.movementQuery(), playerID, gameID));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                inputThread.start();
                break;
            case PARCHMENTREQUEST:
                inputThread = new Thread(() -> {
                    try {
                        sendMessageToServer(new ClientMessage(ClientMessageEnum.PARCHMENTCHOICE, view.parchmentQuery(payload), playerID, gameID));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                inputThread.start();
                break;
            case EXCOMMUNICATIONREQUEST:
                inputThread = new Thread(() -> {
                    try {
                        sendMessageToServer(new ClientMessage(ClientMessageEnum.EXCOMMUNICATIONCHOICE, view.faithQuery(), playerID, gameID, this));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                inputThread.start();
                break;
            case COSTREQUEST:
                inputThread = new Thread(() -> {
                    try {
                        sendMessageToServer(new ClientMessage(ClientMessageEnum.COSTCHOICE, view.costQuery(payload), playerID, gameID));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                inputThread.start();
                break;
            case EXCHANGEREQUEST:
                inputThread = new Thread(() -> {
                    try {
                        sendMessageToServer(new ClientMessage(ClientMessageEnum.EXCHANGECHOICES, view.exchangeQuery(payload), playerID, gameID));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                inputThread.start();
                break;
            case FREECARDREQUEST:
                inputThread = new Thread(() -> {
                    try {
                        sendMessageToServer(new ClientMessage(ClientMessageEnum.FREECARDCHOICE, view.freeCardQuery(payload), playerID, gameID));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                inputThread.start();
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
