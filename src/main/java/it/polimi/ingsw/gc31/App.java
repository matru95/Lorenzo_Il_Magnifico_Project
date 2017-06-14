package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.controller.GameInstanceController;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{

    private static Scanner in = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(App.class.getName());
    private static List<PlayerColor> availableColors = new LinkedList<>(Arrays.asList(PlayerColor.values()));

    private App() {}
}
