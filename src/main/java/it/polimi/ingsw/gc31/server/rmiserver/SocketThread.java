package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.messages.ClientMessage;
import it.polimi.ingsw.gc31.messages.ClientMessageEnum;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class SocketThread implements Runnable{
    private Socket clientSocket;
    private GameServer gameServer;

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
                ClientMessage request = (ClientMessage) objIn.readObject();
                Map<String, String> response = gameServer.send(request);
                objOut.writeObject(response);
                objOut.flush();
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

}
