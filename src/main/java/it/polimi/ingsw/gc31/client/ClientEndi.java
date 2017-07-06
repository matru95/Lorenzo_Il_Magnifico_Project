package it.polimi.ingsw.gc31.client;

import it.polimi.ingsw.gc31.view.GameViewCtrl;
import it.polimi.ingsw.gc31.view.cli.GameViewCLI;

import java.io.IOException;
import java.rmi.NotBoundException;

public class ClientEndi {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, NotBoundException {
        GameViewCtrl ctrl = new GameViewCLI("ENDI", "localhost");
    }
}
