package it.polimi.ingsw.gc31.view.cli;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import it.polimi.ingsw.gc31.controller.PlayerController;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.cards.CardParser;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.view.parser.CardViewParser;
import it.polimi.ingsw.gc31.view.parser.ViewParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

public class GameView implements GameBoardObserver, GameInstanceObserver, PlayerObserver {

    private GameBoard gameBoardModel;
    private Player playerModel;
    private PlayerController playerCtrl;

    public static List<Card> cards;

    public GameView(Player playerModel, PlayerController playerCtrl) {
        this.gameBoardModel = playerModel.getBoard();
        this.playerModel = playerModel;
        this.playerCtrl = playerCtrl;
    }

    /**
     * Method to print on Console the current View of Client's Player.
     */
    public void printView() throws IOException {
        printHeader();
        printTowers();
        printSpaces();
        printPlayer();
        printFamilyMembers();
        printMovementQuery();
    }

    /**
     * Method to print an header with information regard the current GameInstance.
     */
    private void printHeader() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("GameID: [" + gameBoardModel.getGameInstance().getInstanceID() + "]", "AGE:[" + gameBoardModel.getGameInstance().getAge() + "] TURN:[" + gameBoardModel.getGameInstance().getTurn() + "]");
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(51).add(51));
        System.out.println(at.render() + "\n");
    }

    /**
     * Method to print the Towers and only their Spaces.
     */
    private void printTowers() {

        //CREATE TOWERS TO PRINT
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("GREEN TOWER", "BLUE TOWER", "YELLOW TOWER", "PURPLE TOWER");
        at.addRule();
        ViewParser cardParser = new CardViewParser();

        for (int floorID = 3; floorID >= 0; floorID--) {

            String greenCardString = gameBoardModel.getTowerByColor(CardColor.GREEN).getTowerSpace().get(floorID).getCard().toString();
            String blueCardString = gameBoardModel.getTowerByColor(CardColor.BLUE).getTowerSpace().get(floorID).getCard().toString();
            String yellowCardString = gameBoardModel.getTowerByColor(CardColor.YELLOW).getTowerSpace().get(floorID).getCard().toString();
            String purpleCardString = gameBoardModel.getTowerByColor(CardColor.PURPLE).getTowerSpace().get(floorID).getCard().toString();

            at.addRow(cardParser.parse(greenCardString), cardParser.parse(blueCardString), cardParser.parse(yellowCardString), cardParser.parse(purpleCardString));
            at.addRule();
        }

        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(25).add(25).add(25).add(25));

        System.out.println(at.render() + "\n");

    }

    /**
     * Method to print all the Spaces on the board (except the ones on the towers).
     */
    private void printSpaces() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("CIAO", "HOLA", "HALO", "AJO");
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(25).add(25).add(25).add(25));
        System.out.println(at.render() + "\n");
    }

    /**
     * Method to print information (status) regarding a single player.
     */
    private void printPlayer() {

        AsciiTable at = new AsciiTable();
        at.addRule();

        at.addRow(null, null, null, "PLAYERS");
        at.addRule();

        at.addRow("[" + playerModel.getPlayerColor() + "]: " + playerModel.getPlayerName(),
                "[" + playerModel.getPlayerColor() + "]: " + playerModel.getPlayerName(),
                "[" + playerModel.getPlayerColor() + "]: " + playerModel.getPlayerName(),
                "[" + playerModel.getPlayerColor() + "]: " + playerModel.getPlayerName()
                );
        at.addRule();
        for(ResourceName resName: ResourceName.values()) {
            at.addRow(resName.toString() + ":[" + playerModel.getRes().get(resName).getNumOf() + "]",
                    resName.toString() + ":[" + playerModel.getRes().get(resName).getNumOf() + "]",
                    resName.toString() + ":[" + playerModel.getRes().get(resName).getNumOf() + "]",
                    resName.toString() + ":[" + playerModel.getRes().get(resName).getNumOf() + "]"
                    );
            at.addRule();
        }
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(25).add(25).add(25).add(25));
        System.out.println(at.render() + "\n");
    }

    /**
     * Method to print the FamilyMembers and their status.
     */
    private void printFamilyMembers() {
        AsciiTable at = new AsciiTable();
        at.addRule();

        at.addRow(null, null, null, "MY FAMILY MEMBERS");
        at.addRule();

        at.addRow("BLACK:[" + playerModel.getSpecificFamilyMember(DiceColor.BLACK).getValue() + "]",
                "WHITE:["+ playerModel.getSpecificFamilyMember(DiceColor.WHITE).getValue() + "]",
                "ORANGE:["+ playerModel.getSpecificFamilyMember(DiceColor.ORANGE).getValue() + "]",
                "NEUTRAL:["+ playerModel.getSpecificFamilyMember(DiceColor.NEUTRAL).getValue() + "]"
        );
        at.addRule();
        at.addRow(playerModel.getSpecificFamilyMember(DiceColor.BLACK).getCurrentPosition().getPositionID(),
                playerModel.getSpecificFamilyMember(DiceColor.WHITE).getCurrentPosition().getPositionID(),
                playerModel.getSpecificFamilyMember(DiceColor.ORANGE).getCurrentPosition().getPositionID(),
                playerModel.getSpecificFamilyMember(DiceColor.NEUTRAL).getCurrentPosition().getPositionID()
        );
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(25).add(25).add(25).add(25));
        System.out.println(at.render() + "\n");

    }

    /**
     * This method is used to print the query for the player,
     * in order to choose the space in which move the FamilyMember
     * and its color.
     */
    private void printMovementQuery() throws IOException {

        Integer id = 0;
        DiceColor color;

        System.out.println("\n" + playerModel.getPlayerName() + ", it's your turn, move a Family Member into a Space.\n\n" +
                "Enter the ID of the Space: ");

        Boolean isCorrect = false;
        do {
            try {
                id = readMovement();
                isCorrect = true;
            } catch (NumberFormatException e) {
                System.out.println("You must insert a number, not a string");
            }
        } while (!isCorrect);

        System.out.println("\nNow insert the FamilyMember's color you'd like to use, \n" +
                        "between these available: BLACK, WHITE, ORANGE, NEUTRAL.");
        isCorrect = false;
        do {
            try {
                color = readColor();
                isCorrect = true;
            } catch (IllegalArgumentException e) {
                System.out.println("You must insert a valid color among these: \n" +
                        "BLACK, WHITE, ORANGE, NEUTRAL.");
            }
        } while (!isCorrect);

    }

    /**
     * Method to read an Integer from Input.
     * @return Integer
     * @throws IOException
     */
    private int readMovement() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Integer id = Integer.valueOf(br.readLine());
        return id;
    }

    /**
     * Method to read a DiceColor from Input.
     * @return DiceColor (enum element)
     * @throws IOException
     */
    private DiceColor readColor() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DiceColor color = DiceColor.valueOf(br.readLine().toUpperCase());
        return color;
    }

    /**
     * This method is used to print the query for the player,
     * in order to choose the bonus of a parchment.
     */
    private void printParchementQuery() {
        System.out.println("Choose a BONUS for parchement between this: \n" +
            "Type 0 for: 1 Wood && 1 Stone \n" +
            "Type 1 for: 2 Servants \n" +
            "Type 2 for: 2 Golds \n" +
            "Type 3 for: 2 Military Points \n" +
            "Type 4 for: 1 Faith Points \n");
        //TODO
    }

    /**
     * This method is used to print the query for the player,
     * in order to choose if accept or not the effect of an excommunication.
     */
    private void printFaithEffect() {
        /**
        System.out.println("Do you really wanna take this faith effect?" +
                gameBoardModel.getFaithCard.getEffect.toString());  */
        //TODO
    }

    @Override
    public void getKeyboardInput() {
        //TODO
    }

    @Override
    public void updateBoard() {
        //TODO
    }

    public static void main(String[] args) throws NoResourceMatch, IOException {
        Player p = new Player(UUID.randomUUID(), "MATRU", PlayerColor.BLUE);
        GameInstance gameInstance = new GameInstance(UUID.randomUUID());
        gameInstance.addPlayer(p);
        GameBoard gameBoard = new GameBoard(gameInstance);
        gameInstance.setGameBoard(gameBoard);
        p.setGameBoard(gameBoard);
        PlayerController playerController = new PlayerController(p);
        GameView view = new GameView(p, playerController);
        (new Thread(gameInstance)).start();
        CardParser cardParser = new CardParser("src/config/Card.json");
        cardParser.parse();
        cards = cardParser.getCards();
        view.printView();
    }
}