package it.polimi.ingsw.gc31.client;

import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.view.cli.GameViewCLI;
import it.polimi.ingsw.gc31.view.jfx.GameViewFX;
import javafx.application.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;

public class ClientApp {

    public static void main(String[] args) throws IOException, InterruptedException, NoResourceMatch, ClassNotFoundException, NotBoundException {

        BufferedReader br;
        System.out.println("Choose between using \"CLI\" or \"JAVAFX\":");
        String choice;

        do {
            br = new BufferedReader(new InputStreamReader(System.in));
            choice = br.readLine();

            if (choice.equalsIgnoreCase("CLI")) {
                System.out.println("Hello, pls enter your name:");
                br = new BufferedReader(new InputStreamReader(System.in));
                String myPlayerName = br.readLine();
                System.out.println("Now enter the ip address for the server to which connect (\"127.0.0.1\" for localhost):");
                br = new BufferedReader(new InputStreamReader(System.in));
                String serverIP = br.readLine();
                new GameViewCLI(myPlayerName, serverIP);
                break;
            }

            if (choice.equalsIgnoreCase("JAVAFX")) {
                Application.launch(GameViewFX.class, args);
                break;
            }
        } while (true);

    }

}
