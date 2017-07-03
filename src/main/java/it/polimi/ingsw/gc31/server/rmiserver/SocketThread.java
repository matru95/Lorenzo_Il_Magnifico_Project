package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.messages.ClientMessage;
import it.polimi.ingsw.gc31.messages.ClientMessageEnum;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class SocketThread implements Runnable{
    private Socket clientSocket;
    private GameServer gameServer;
    private String playerID;
    private String gameID;

    public SocketThread(Socket clientSocket, GameServer gameServer) {
        this.clientSocket = clientSocket;
        this.gameServer = gameServer;
    }


    @Override
    public void run() {
        ObjectOutputStream objOut = null;
        ObjectInputStream objIn = null;

        try {
            objOut = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            objIn = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            getMessages(objOut, objIn);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void getMessages(ObjectOutputStream objOut, ObjectInputStream objIn) throws IOException {
        boolean end = false;

        while (!end){
            try{
//              Receive message from client
                ClientMessage request = (ClientMessage) objIn.readObject();

                if(request.getClientMessageType() == ClientMessageEnum.REGISTERSUCCESS) {
                    this.playerID = request.getPlayerID();
                    this.gameID = request.getGameID();
                } else {
//                  Send to Server and get response
                    Map<String, String> response = gameServer.send(request);
//                  Send the response to the client
                    objOut.writeObject(response);
                    objOut.flush();
                }

                end = (request.getClientMessageType() == ClientMessageEnum.ENDCONNECTION);
            }catch (IOException ioe){
                end = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NoResourceMatch noResourceMatch) {
                noResourceMatch.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        clientSocket.close();
        objIn.close();
        objOut.close();
    }

    public void updateClient(Map<String, String> gameState) throws IOException {
        ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
        Map<String, String> payload = gameState;
        ServerMessage serverMessage = new ServerMessage(ServerMessageEnum.UPDATE, payload);
        objOut.writeObject(serverMessage);
        objOut.flush();
    }

    public String getGameID() {
        return gameID;
    }

    public String getPlayerID() {
        return playerID;
    }
}
