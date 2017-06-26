package it.polimi.ingsw.gc31.view.cli;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardParser;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.view.GameView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameViewCLI implements GameView {

    private static final String PN = "playerName";
    private static final String CARDS = "cards";

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private final UUID playerID;
    private final ObjectMapper mapper;
    private JsonNode rootInstance;
    private JsonNode rootBoard;

    public GameViewCLI(UUID playerID) {
        this.playerID = playerID;
        this.mapper = new ObjectMapper();
        printLogo();
    }

    /**
     * Method to print a game logo for the CLI View.
     */
    private void printLogo() {
        System.out.println(                                                                                                       "\n" +
                "                                  ██╗      ██████╗ ██████╗ ███████╗███╗   ██╗███████╗ ██████╗          ██╗██╗     \n" +
                "                                  ██║     ██╔═══██╗██╔══██╗██╔════╝████╗  ██║╚══███╔╝██╔═══██╗         ██║██║     \n" +
                "                                  ██║     ██║   ██║██████╔╝█████╗  ██╔██╗ ██║  ███╔╝ ██║   ██║         ██║██║     \n" +
                "                                  ██║     ██║   ██║██╔══██╗██╔══╝  ██║╚██╗██║ ███╔╝  ██║   ██║         ██║██║     \n" +
                "                                  ███████╗╚██████╔╝██║  ██║███████╗██║ ╚████║███████╗╚██████╔╝         ██║███████╗\n" +
                "                                  ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚══════╝ ╚═════╝          ╚═╝╚══════╝\n\n" +
                "                                       ███╗   ███╗ █████╗  ██████╗ ███╗   ██╗██╗███████╗██╗ ██████╗ ██████╗       \n" +
                "                                       ████╗ ████║██╔══██╗██╔════╝ ████╗  ██║██║██╔════╝██║██╔════╝██╔═══██╗      \n" +
                "                                       ██╔████╔██║███████║██║  ███╗██╔██╗ ██║██║█████╗  ██║██║     ██║   ██║      \n" +
                "                                       ██║╚██╔╝██║██╔══██║██║   ██║██║╚██╗██║██║██╔══╝  ██║██║     ██║   ██║      \n" +
                "                                       ██║ ╚═╝ ██║██║  ██║╚██████╔╝██║ ╚████║██║██║     ██║╚██████╗╚██████╔╝      \n" +
                "                                       ╚═╝     ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝     ╚═╝ ╚═════╝ ╚═════╝       \n\n");
    }

    /**
     * Method to print on Console the current GameView of Client's Player.
     * @throws IOException
     */
    private void printView() {
        printHeader();
        printTowers();
        //printSpaces();
        printPlayers();
        printFamilyMembers();
        //printMovementQuery();
    }

    /**
     * Method to print an header with information regard the current GameInstance.
     */
    private void printHeader() {

        AsciiTable at = new AsciiTable();
        at.addRule();

        at.addRow("GameID: [" + go(rootInstance.path("instanceID")) + "]",
                "MyPlayerID: [" + playerID + "]",
                "AGE: [" + rootInstance.path("age") + "] TURN: [" + rootInstance.path("turn") + "]");
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(60).add(60).add(24));
        System.out.println(at.render() + "\n");

    }

    /**
     * Method to print the Towers and only their Spaces.
     */
    private void printTowers() {

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("~ TOWERS ~");
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(146));

        AsciiTable att = new AsciiTable();
        att.addRule();
        att.addRow("[TOWER COLOR]", "FLOOR:[0]", "FLOOR:[1]", "FLOOR:[2]", "FLOOR:[3]");
        att.addRule();

        for (CardColor cardColor: CardColor.values()) {
            String color = cardColor.toString();
            JsonNode node = rootBoard.path("towers").path(color).path("towerSpaces");
            att.addRow(color + " TOWER", go(node.path("0")), go(node.path("1")), go(node.path("2")), go(node.path("3")));
            att.addRule();
        }

        att.setTextAlignment(TextAlignment.CENTER);
        att.getRenderer().setCWC(new CWC_FixedWidth().add(30).add(28).add(28).add(28).add(28));

        System.out.println(at.render());
        System.out.println(att.render() + "\n");

    }

    /**
     * Method to print all the Spaces on the board (except the ones on the towers).
     */
    private void printSpaces() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("CIAO", "HOLA", "HALO", "AJO", "HELLO");
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(30).add(28).add(28).add(28).add(28));
        System.out.println(at.render() + "\n");
    }

    /**
     * Method to print information (status) regarding a single player.
     */
    private void printPlayers() {

        JsonNode players = rootInstance.path("players");
        Map<Integer, String> orderedPlayers = new HashMap<>();
        StringBuilder render = new StringBuilder();

        for (JsonNode singlePlayer: players) {

            AsciiTable atp = new AsciiTable();
            atp.addRule();

            atp.addRow("[" + go(singlePlayer.path("playerColor")) + "]: " + go(singlePlayer.path(PN)),
                    null, null, null, null, null,
                    "RESOURCES: [ " + go(singlePlayer.path("res")).replace(","," | ") + " ]"
            );
            atp.addRule();

            for (CardColor cardColor: CardColor.values()) {
                String color = cardColor.toString();
                atp.addRow(color + " CARDS:",
                        go(singlePlayer.path(CARDS).path(color).path(0)),
                        go(singlePlayer.path(CARDS).path(color).path(1)),
                        go(singlePlayer.path(CARDS).path(color).path(2)),
                        go(singlePlayer.path(CARDS).path(color).path(3)),
                        go(singlePlayer.path(CARDS).path(color).path(4)),
                        go(singlePlayer.path(CARDS).path(color).path(5))
                );
                atp.addRule();
            }

            atp.setTextAlignment(TextAlignment.CENTER);
            atp.getRenderer().setCWC(new CWC_FixedWidth().add(20).add(20).add(20).add(20).add(20).add(20).add(20));
            render.append(atp.render() + "\n");

            orderedPlayers.put(singlePlayer.path("playerOrder").asInt(), singlePlayer.path(PN).toString());
        }

        StringBuilder order = new StringBuilder("PLAYERS ORDER: ");
        for (int i = 1; i <= 4; i++) {
            if (orderedPlayers.get(i) != null) order.append("[" + i + "]: " + orderedPlayers.get(i) + " ");
        }

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("~ PLAYERS ~", order.toString().replace("\"",""));
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(20).add((125)));

        System.out.println(at.render());
        System.out.println(render);
    }

    /**
     * Method to print the FamilyMembers and their status.
     */
    private void printFamilyMembers() {

        JsonNode players = rootInstance.path("players");

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("~ FAMILY MEMBERS ~");
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(146));

        AsciiTable atfm = new AsciiTable();
        atfm.addRule();
        for (JsonNode singlePlayer: players) {
            JsonNode familyMembers = singlePlayer.path("familyMembers");
            atfm.addRow(go(singlePlayer.path(PN)),
                    go(familyMembers.path(0)),
                    go(familyMembers.path(1)),
                    go(familyMembers.path(2)),
                    go(familyMembers.path(3))
            );
            atfm.addRule();
        }
        atfm.setTextAlignment(TextAlignment.CENTER);
        atfm.getRenderer().setCWC(new CWC_FixedWidth().add(30).add(28).add(28).add(28).add(28));

        System.out.println(at.render());
        System.out.println(atfm.render() + "\n");
    }

    /**
     * This method is used to print the query for the player,
     * in order to choose the space in which move the FamilyMember
     * and its color.
     */
    private void printMovementQuery() throws IOException {

        Integer id = 0;
        DiceColor color;

        System.out.println("\nIt's your turn, move a Family Member into a Space:\n");

        System.out.println("Insert the FamilyMember's color you'd like to use, between those available: BLACK, WHITE, ORANGE, NEUTRAL\n");
        Boolean  isCorrect = false;
        do {
            try {
                color = readColor();
                isCorrect = true;
            } catch (IllegalArgumentException e) {
                System.out.println("You must insert a valid color among these: \n" +
                        "BLACK, WHITE, ORANGE, NEUTRAL.");
            }
        } while (!isCorrect);

        System.out.println("\nEnter the ID of the Space:\n");
        isCorrect = false;
        do {
            try {
                id = readMovement();
                isCorrect = true;
            } catch (NumberFormatException e) {
                System.out.println("You must insert a number, not a string");
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
        return Integer.valueOf(br.readLine());
    }

    /**
     * Method to read a DiceColor from Input.
     * @return DiceColor (enum element)
     * @throws IOException
     */
    private DiceColor readColor() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return DiceColor.valueOf(br.readLine().toUpperCase());
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
    private void printFaithQuery() {
        /**
        System.out.println("Do you really wanna take this faith effect?" +
                gameBoardModel.getFaithCard.getEffect.toString());  */
        //TODO
    }

    @Override
    public void update(Map<String, String> gameState) throws IOException {
        this.rootInstance = mapper.readTree(gameState.get("GameInstance"));
        this.rootBoard = mapper.readTree(gameState.get("GameBoard"));
        printView();
    }

    /**
     * This method take a JsonNode as input, converting it into a String,
     * removing Double Quotes and Braces and adding a space after a Comma.
     * @param node: JsonNode for a JSON Parser.
     * @return String
     */
    public static String go(JsonNode node) {
        return node.toString().replace("\"", "")
                .replace("{","")
                .replace("}","")
                .replace(",", ", ")
                .replace("currentPositionID:0", "[HASN'T_MOVED_YET]");

    }

    public static void main(String[] args) throws NoResourceMatch, IOException {
        List<Card> cards;
        Player p1 = new Player(UUID.randomUUID(), "MATRU", PlayerColor.BLUE);
        Player p2 = new Player(UUID.randomUUID(), "ENDI", PlayerColor.RED);
        GameInstance gameInstance = new GameInstance(UUID.randomUUID());
        gameInstance.addPlayer(p1);
        gameInstance.addPlayer(p2);
        GameBoard gameBoard = new GameBoard(gameInstance);
        gameInstance.setGameBoard(gameBoard);
        p1.setGameBoard(gameBoard);
        p2.setGameBoard(gameBoard);
        p1.setPlayerOrder(2);
        p2.setPlayerOrder(1);

        CardParser cardParser = new CardParser("src/config/Card.json");
        cardParser.parse();
        cards = cardParser.getCards();
        p1.getCards().get(CardColor.YELLOW).add(cards.get(50));
        p1.getCards().get(CardColor.YELLOW).add(cards.get(51));
        p1.getCards().get(CardColor.YELLOW).add(cards.get(52));
        p1.getCards().get(CardColor.GREEN).add(cards.get(1));
        p1.getCards().get(CardColor.BLUE).add(cards.get(25));
        p1.getCards().get(CardColor.PURPLE).add(cards.get(74));
        gameInstance.run();
        Map<String, String> gameState = new HashMap<>();
        gameState.put("GameInstance", gameInstance.toString());
        gameState.put("GameBoard", gameInstance.getGameBoard().toString());
        GameViewCLI view = new GameViewCLI(p1.getPlayerID());
        (new Thread(gameInstance)).start();

        view.update(gameState);
    }
}