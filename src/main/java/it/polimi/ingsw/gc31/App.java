package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.PlayerColor;
import java.util.*;
import java.util.logging.Logger;

public class App
{

    private static Scanner in = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(App.class.getName());
    private static List<PlayerColor> availableColors = new LinkedList<>(Arrays.asList(PlayerColor.values()));

    private App() {}
}
