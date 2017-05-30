package it.polimi.ingsw.gc31;

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
    public static void main( String[] args ) throws NoResourceMatch {

        int numOfPlayers = askNumOfPlayers();
        Player[] players = new Player[numOfPlayers];


        for(int i = 0; i < numOfPlayers; i++) {
            Player player = askPlayerNameAndColor(i);
            players[i] = player;
        }

        GameInstance instance = new GameInstance(numOfPlayers, players);
        GameBoard gameBoard = new GameBoard(instance);
        instance.setGameBoard(gameBoard);

        initBoardForPlayers(numOfPlayers, players, gameBoard);

        instance.playGame();
    }

    private static void initBoardForPlayers(int numOfPlayers, Player[] players, GameBoard board) {

        for(int i = 0; i < numOfPlayers; i++) {
            players[i].setGameBoard(board);
        }
    }

    private static int askNumOfPlayers() {
        logger.log(Level.FINEST, "Quanti giocatori ci sono?");

        try {
            int number = in.nextInt();
            logger.log(Level.FINEST,"%s", String.valueOf(number));

            if(number < 2) {
                logger.log(Level.FINEST, "Il numero minimo dei giocatori è 2");
                return askNumOfPlayers();
            } else if(number > 4) {
                logger.log(Level.FINEST,"Il numero massimo dei giocatori è 4");
                return askNumOfPlayers();
            } else {
                return number;
            }

        } catch(InputMismatchException e) {
//			Maybe find a way to handle this exception in a better way?
            logger.log(Level.FINE,"Il numero dei giocatori deve essere un numero.");
            System.exit(1);
            return 0;
        }

    }

    private static Player askPlayerNameAndColor(int id) {
        int currentPlayer = id + 1;
        String colorName;
        PlayerColor chosenColor;

        logger.log(Level.FINEST, "Come si chiama il giocatore numero %d?", currentPlayer);
        String playerName = in.next();

        while(true) {
            logger.log(Level.FINEST, "Quale colore ha?");

            for(PlayerColor c : availableColors) {
                logger.log(Level.FINEST,"%s", c.name());
            }

            colorName = in.next();

            if(PlayerColor.contains(colorName)) {
                chosenColor = PlayerColor.valueOf(colorName.toUpperCase());

                if(availableColors.contains(chosenColor)) {
                    availableColors.remove(chosenColor);
                    break;
                } else {
                    logger.log(Level.FINE,"Quel colore è già stato scelto!");
                }

            }
        }

        return new Player(id, playerName, chosenColor);
    }
}