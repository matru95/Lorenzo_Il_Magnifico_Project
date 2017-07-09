package it.polimi.ingsw.gc31.server;

import it.polimi.ingsw.gc31.messages.ClientMessage;
import it.polimi.ingsw.gc31.messages.ClientMessageEnum;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class SocketThread implements Runnable{
    private Socket clientSocket;
    private Server gameServer;
    private String playerID;
    private String gameID;
    private String socketClientID;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;

    public SocketThread(Socket clientSocket, Server gameServer) {
        this.clientSocket = clientSocket;
        this.gameServer = gameServer;
    }


    @Override
    public void run() {
        try {
            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            objectInputStream = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            getMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void getMessages() throws IOException {
        boolean condition = true;

        while (condition){
            try{
//              Receive message from client
                ClientMessage request = (ClientMessage) objectInputStream.readObject();

                if(request.getClientMessageType() == ClientMessageEnum.REGISTERSUCCESS) {
                    this.playerID = request.getPlayerID();
                    this.gameID = request.getGameID();
                } else if (request.getClientMessageType() == ClientMessageEnum.REGISTER) {
                    this.socketClientID = request.getPayload().get("socketClientID");
                    sendToServer(request);
                } else {
//                  Send to Server for processing
                    sendToServer(request);
                }

                condition = (request.getClientMessageType() != ClientMessageEnum.ENDCONNECTION);
            }catch (IOException ioe){
                ioe.printStackTrace();
                condition = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("finished receiving");
        }

        System.out.println("closing socket");
        clientSocket.close();
        objectOutputStream.close();
        objectOutputStream.close();
    }

    private void sendToServer(ClientMessage request) {
        new Thread(() -> {
            try {
                gameServer.send(request);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void updateClient(Map<String, String> gameState) throws IOException {
        Map<String, String> payload = gameState;
        ServerMessage serverMessage = new ServerMessage(ServerMessageEnum.UPDATE, payload);
        send(serverMessage);
    }

    public void send(ServerMessage request) {
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.reset();
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getGameID() {
        return gameID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getSocketClientID() {
        return socketClientID;
    }
}
