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
import it.polimi.ingsw.gc31.view.GameView;

import java.io.*;
import java.util.*;

public class GameViewCLI implements GameView {

    private static final String PN = "playerName";
    private static final String PL = "players";
    private static final String CARDS = "cards";

    private final UUID myPlayerID;
    private final ObjectMapper mapper;
    private String myPlayerName;
    private StringBuilder sb;
    private JsonNode rootInstance;
    private JsonNode rootBoard;

    public GameViewCLI(UUID playerID) {
        this.myPlayerID = playerID;
        this.mapper = new ObjectMapper();
        this.myPlayerName = "";
        this.sb = new StringBuilder();
        printLogo();
        printStringBuilder();
    }

    /**
     * Method to print on Console the current GameView of Client's Player.
     */
    private void printView() {

        initPlayerName();
        printHeader();
        printTowers();
        printSpaces();
        printFaithCards();
        printPlayers();
        printFamilyMembers();
        printStringBuilder();
    }

    /**
     * Method to print an header with information regard the current GameInstance.
     */
    private void printHeader() {

        AsciiTable at = new AsciiTable();
        at.addRule();

        at.addRow("GameID:[" + beauty(rootInstance.path("instanceID")) + "]",
                "AGE:[" + rootInstance.path("age") + "] TURN:[" + rootInstance.path("turn") + "]",
                "MyID:[" + myPlayerID + "]",
                "MyName:[" + myPlayerName + "]");
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(48).add(20).add(48).add(27));
        sb.append(at.render()).append("\n");

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
            att.addRow(color + " TOWER", beauty(node.path("0")), beauty(node.path("1")), beauty(node.path("2")), beauty(node.path("3")));
            att.addRule();
        }

        att.setTextAlignment(TextAlignment.CENTER);
        att.getRenderer().setCWC(new CWC_FixedWidth().add(30).add(28).add(28).add(28).add(28));

        sb.append(at.render()).append("\n")
          .append(att.render()).append("\n");

    }

    /**
     * Method to print all the Faith Cards.
     */
    private void printFaithCards() {

        JsonNode faithTiles = rootBoard.path("faithTiles");

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("~ FAITH CARDS ~", beauty(faithTiles.path(0)), beauty(faithTiles.path(1)), beauty(faithTiles.path(2)));
        at.addRule();
        at.getRenderer().setCWC(new CWC_FixedWidth().add(35).add(36).add(36).add(36));
        at.setTextAlignment(TextAlignment.CENTER);

        sb.append(at.render()).append("\n");
    }

    /**
     * Method to print all the Spaces on the board (except the ones on the towers).
     */
    private void printSpaces() {

        JsonNode boardSpaces = rootBoard.path("boardSpaces");

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("~ OTHER BOARD SPACES ~", beauty(boardSpaces.path("17")), beauty(boardSpaces.path("18")), beauty(boardSpaces.path("23")));
        at.addRule();
        at.addRow(beauty(boardSpaces.path("19")), beauty(boardSpaces.path("20")), beauty(boardSpaces.path("21")), beauty(boardSpaces.path("22")));
        at.addRule();
        at.getRenderer().setCWC(new CWC_FixedWidth().add(35).add(36).add(36).add(36));
        at.setTextAlignment(TextAlignment.CENTER);

        sb.append(at.render()).append("\n");
    }

    /**
     * Method to print information (status) regarding a single player.
     */
    private void printPlayers() {

        JsonNode players = rootInstance.path(PL);
        Map<Integer, String> orderedPlayers = new HashMap<>();
        StringBuilder render = new StringBuilder();

        for (JsonNode singlePlayer: players) {

            AsciiTable atp = new AsciiTable();
            atp.addRule();

            atp.addRow("[" + beauty(singlePlayer.path("playerColor")) + "]: " + beauty(singlePlayer.path(PN)),
                    null, null, null, null, null,
                    "RESOURCES: [ " + beauty(singlePlayer.path("res")).replace(","," | ") + " ]"
            );
            atp.addRule();

            for (CardColor cardColor: CardColor.values()) {
                String color = cardColor.toString();
                atp.addRow(color + " CARDS:",
                        beauty(singlePlayer.path(CARDS).path(color).path(0)),
                        beauty(singlePlayer.path(CARDS).path(color).path(1)),
                        beauty(singlePlayer.path(CARDS).path(color).path(2)),
                        beauty(singlePlayer.path(CARDS).path(color).path(3)),
                        beauty(singlePlayer.path(CARDS).path(color).path(4)),
                        beauty(singlePlayer.path(CARDS).path(color).path(5))
                );
                atp.addRule();
            }

            atp.setTextAlignment(TextAlignment.CENTER);
            atp.getRenderer().setCWC(new CWC_FixedWidth().add(20).add(20).add(20).add(20).add(20).add(20).add(20));
            render.append(atp.render()).append("\n");

            orderedPlayers.put(singlePlayer.path("playerOrder").asInt(), singlePlayer.path(PN).toString());
        }

        StringBuilder order = new StringBuilder("PLAYERS ORDER: ");
        for (int i = 1; i <= 4; i++) {
            if (orderedPlayers.get(i) != null) {
                order.append("[").append(i).append("]: ").append(orderedPlayers.get(i)).append(" ");
            }
        }

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("~ PLAYERS ~", order.toString().replace("\"",""));
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(20).add((125)));

        sb.append(at.render()).append("\n")
          .append(render).append("\n");
    }

    /**
     * Method to print the FamilyMembers and their status.
     */
    private void printFamilyMembers() {

        JsonNode players = rootInstance.path(PL);

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
            atfm.addRow(beauty(singlePlayer.path(PN)),
                    beauty(familyMembers.path(0)),
                    beauty(familyMembers.path(1)),
                    beauty(familyMembers.path(2)),
                    beauty(familyMembers.path(3))
            );
            atfm.addRule();
        }
        atfm.setTextAlignment(TextAlignment.CENTER);
        atfm.getRenderer().setCWC(new CWC_FixedWidth().add(30).add(28).add(28).add(28).add(28));

        sb.append(at.render()).append("\n")
        .append(atfm.render()).append("\n");
    }

    /**
     * Method to print a game logo for the CLI View.
     */
    private void printLogo() {
        sb.append("\n") .append("                                  ██╗      ██████╗ ██████╗ ███████╗███╗   ██╗███████╗ ██████╗          ██╗██╗     \n")
                        .append("                                  ██║     ██╔═══██╗██╔══██╗██╔════╝████╗  ██║╚══███╔╝██╔═══██╗         ██║██║     \n")
                        .append("                                  ██║     ██║   ██║██████╔╝█████╗  ██╔██╗ ██║  ███╔╝ ██║   ██║         ██║██║     \n")
                        .append("                                  ██║     ██║   ██║██╔══██╗██╔══╝  ██║╚██╗██║ ███╔╝  ██║   ██║         ██║██║     \n")
                        .append("                                  ███████╗╚██████╔╝██║  ██║███████╗██║ ╚████║███████╗╚██████╔╝         ██║███████╗\n")
                        .append("                                  ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚══════╝ ╚═════╝          ╚═╝╚══════╝\n\n")
                        .append("                                       ███╗   ███╗ █████╗  ██████╗ ███╗   ██╗██╗███████╗██╗ ██████╗ ██████╗       \n")
                        .append("                                       ████╗ ████║██╔══██╗██╔════╝ ████╗  ██║██║██╔════╝██║██╔════╝██╔═══██╗      \n")
                        .append("                                       ██╔████╔██║███████║██║  ███╗██╔██╗ ██║██║█████╗  ██║██║     ██║   ██║      \n")
                        .append("                                       ██║╚██╔╝██║██╔══██║██║   ██║██║╚██╗██║██║██╔══╝  ██║██║     ██║   ██║      \n")
                        .append("                                       ██║ ╚═╝ ██║██║  ██║╚██████╔╝██║ ╚████║██║██║     ██║╚██████╗╚██████╔╝      \n")
                        .append("                                       ╚═╝     ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝     ╚═╝ ╚═════╝ ╚═════╝       \n\n");
    }

    /**
     * This method is used to print the query for the player,
     * in order to choose the space in which move the FamilyMember
     * and its color.
     */
    public Map<String, String> printMovementQuery() throws IOException {

        HashMap<String, String> result = new HashMap<>();
        DiceColor color;
        Integer id;
        Integer servants;

        sb.append("\nIt's your turn, move a Family Member into a Space:\n")
                .append("Insert the FamilyMember's color you'd like to use, between those available: BLACK, WHITE, ORANGE, NEUTRAL");
        printStringBuilder();

        do {
            try {
                color = readDiceColor();
                break;
            } catch (IllegalArgumentException e) {
                sb.append("You must insert a valid color among these: BLACK, WHITE, ORANGE, NEUTRAL");
                printStringBuilder();
            }
        } while (true);
        result.put("diceColor", color.toString());

        sb.append("\nEnter the ID of the Space:");
        printStringBuilder();
        do {
            try {
                id = readInteger();
                if (id > 0 && id <= 23) break;
                sb.append("You must insert a valid number between 1 and 23:");
                printStringBuilder();
            } catch (NumberFormatException e) {
                sb.append("You must insert a valid number between 1 and 23:");
                printStringBuilder();
            }
        } while (true);
        result.put("spaceID", id.toString());

        Integer max = getMyServants();

        sb.append("\nEnter the number of servants you'd like to add (if you won't enter 0):");
        printStringBuilder();
        do {
            try {
                servants = readInteger();
                if (servants >= 0 && servants <= max) break;
                sb.append("You must insert a valid number between 1 and ").append(max).append(":");
                printStringBuilder();
            } catch (NumberFormatException e) {
                sb.append("You must insert a valid number between 1 and ").append(max).append(":");
                printStringBuilder();
            }
        } while (true);
        result.put("servantsToPay", servants.toString());

        return result;
    }

    /**
     * This method is used to print the query for the player,
     * in order to choose the bonus of one or more parchments.
     */
    public Map<String, String> printParchmentQuery(Map<String, String> map) throws IOException {

        HashMap<String, String> result = new HashMap<>();
        int numOfParchments = Integer.parseInt(map.get("parchments"));
        ArrayList<Integer> lastChoices = new ArrayList();
        if (numOfParchments > 1)
            sb.append("Now you'll have to choose ").append(numOfParchments).append(" different parchments.\n");

        for (int i = 1; i <= numOfParchments; i++) {
            sb.append("Choose a parchment's bonus between this: \n" +
                    "Type 0 for: 1 Wood && 1 Stone \n" +
                    "Type 1 for: 2 Servants \n" +
                    "Type 2 for: 2 Golds \n" +
                    "Type 3 for: 2 Military Points \n" +
                    "Type 4 for: 1 Faith Points ");
            printStringBuilder();

            Integer choice;
            do {
                try {
                    choice = readInteger();
                    if (choice >= 0 && choice <= 4 && !lastChoices.contains(choice)) break;
                    sb.append("You must insert a valid number between 0 and 4 and different from previous choices:");
                    printStringBuilder();
                } catch (IllegalArgumentException e) {
                    sb.append("You must insert a valid number between 0 and 4 and different from previous choices:");
                    printStringBuilder();
                }
            } while (true);
            result.put(String.valueOf(i), choice.toString());
            lastChoices.add(choice);
        }

        return result;
    }

    /**
     * This method is used to print the query for the player,
     * in order to choose if accept or not the effect of an excommunication.
     */
    public Map<String, String> printFaithQuery() throws IOException {

        HashMap<String, String> result = new HashMap<>();
        String choice;

        for (JsonNode singleFaithTile: rootBoard.path("faithTiles"))
            if (beauty(singleFaithTile.path("age")).equals(beauty(rootInstance.path("age")))) {
                sb.append("\nChoose if you wanna take this excommunication (YES/NO):\n")
                        .append(beauty(singleFaithTile.path("effect"))).append("\n");
                printStringBuilder();
            }

        do {
            try {
                choice = readString().toUpperCase();
                if (choice.equals("YES") || choice.equals("NO")) break;
                sb.append("You must insert a valid choice among these: (YES, NO)\n");
                printStringBuilder();
            } catch (IllegalArgumentException e) {
                sb.append("You must insert a valid choice among these: (YES, NO)\n");
                printStringBuilder();
            }
        } while (true);
        result.put("applyExcommunication", choice);

        return result;
    }

    /**
     * This method is used to print the query for the player,
     * in order to choose which cost to pay to take a purple card.
     */
    public Map<String, String> printCostQuery(Map<String, String> map) throws IOException {

        HashMap<String, String> result = new HashMap<>();
        String choice;

        for (JsonNode singlePurpleCard: rootBoard.path("cards").path("PURPLE"))
            if (beauty(singlePurpleCard.path("cardID")).equals(map.get("cardID")))
                sb.append("\nChoose which cost to pay to take the card (1 OR 2):\n{(")
                        .append(beauty(singlePurpleCard.path("cost").path(0))).append(") OR (")
                        .append(beauty(singlePurpleCard.path("cost").path(1))).append(")}");
        printStringBuilder();

        do {
            try {
                choice = readString().toUpperCase();
                if (choice.equals("1") || choice.equals("2")) break;
                sb.append("You must insert a valid choice among these: (1, 2)\n");
                printStringBuilder();
            } catch (IllegalArgumentException e) {
                sb.append("You must insert a valid choice among these: (1, 2)\n");
                printStringBuilder();
            }
        } while (true);
        result.put("cardCostChoice", choice);

        return result;
    }

    /**
     * Method to read a String from Input.
     * @return String
     * @throws IOException: Error during input reading.
     */
    private String readString() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    /**
     * Method to read an Integer from Input.
     * @return Integer
     * @throws IOException: Error during input reading.
     */
    private int readInteger() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return Integer.valueOf(br.readLine());
    }

    /**
     * Method to read a DiceColor from Input.
     * @return DiceColor (enum element)
     * @throws IOException: Error during input reading.
     */
    private DiceColor readDiceColor() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return DiceColor.valueOf(br.readLine().toUpperCase());
    }

    /**
     * Print the actual String Builder and then reinitialize it, throwing off the old one
     */
    private void printStringBuilder() {
        System.out.println(this.sb);
        this.sb = new StringBuilder();
    }

    /**
     * Method to get my number of servants.
     */
    private Integer getMyServants(){
        for (JsonNode singlePlayer: rootInstance.path(PL))
            if (beauty(singlePlayer.path("playerID")).equals(myPlayerID.toString()))
                return singlePlayer.path("res").path("SERVANTS").asInt();
        return 0;
    }


    /**
     * Method to init myPlayerName from GameView's JSON
     */
    private void initPlayerName() {
        if (myPlayerName.equals(""))
            for (JsonNode singleplayer: rootInstance.path(PL))
                if (beauty(singleplayer.path("playerID")).equals(myPlayerID.toString()))
                    myPlayerName = beauty(singleplayer.path("playerName"));
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
    private static String beauty(JsonNode node) {
        return node.toString().replace("\"", "")
                .replace("{","")
                .replace("}","")
                .replace(",", ", ")
                .replace("currentPositionID:0", "[HASN'T_MOVED_YET]");

    }

    public static void main(String[] args) throws IOException {
        List<Card> cards;
        Player p1 = new Player(UUID.randomUUID(), "MATRU", PlayerColor.BLUE);
        Player p2 = new Player(UUID.randomUUID(), "ENDI", PlayerColor.RED);
        Player p3 = new Player(UUID.randomUUID(), "PLUX", PlayerColor.GREEN);
        Player p4 = new Player(UUID.randomUUID(), "VALE", PlayerColor.YELLOW);
        GameInstance gameInstance = new GameInstance(UUID.randomUUID());
        gameInstance.addPlayer(p1);
        gameInstance.addPlayer(p2);
        gameInstance.addPlayer(p3);
        gameInstance.addPlayer(p4);
        GameBoard gameBoard = new GameBoard(gameInstance);
        gameInstance.setGameBoard(gameBoard);
        p1.setGameBoard(gameBoard);
        p2.setGameBoard(gameBoard);
        p3.setGameBoard(gameBoard);
        p4.setGameBoard(gameBoard);

        CardParser cardParser = new CardParser("src/config/Card.json");
        cardParser.parse();
        cards = cardParser.getCards();
        p1.getCards().get(CardColor.YELLOW).add(cards.get(50));
        p1.getCards().get(CardColor.YELLOW).add(cards.get(51));
        p1.getCards().get(CardColor.YELLOW).add(cards.get(52));
        p1.getCards().get(CardColor.GREEN).add(cards.get(1));
        p1.getCards().get(CardColor.BLUE).add(cards.get(25));
        p1.getCards().get(CardColor.PURPLE).add(cards.get(74));
        (new Thread(gameInstance)).start();
        gameInstance.run();
        Map<String, String> gameState = new HashMap<>();
        gameState.put("GameInstance", gameInstance.toString());
        gameState.put("GameBoard", gameInstance.getGameBoard().toString());
        GameViewCLI view = new GameViewCLI(p1.getPlayerID());
        view.update(gameState);

/*        Map<String, String> parchment = new HashMap<>();
        parchment.put("parchments", "3");
        //view.printParchmentQuery(parchment);

        //view.printFaithQuery();
        //view.printMovementQuery();

        Map<String, String> cardID = new HashMap<>();
        cardID.put("cardID", "72");
        //view.printCostQuery(cardID);*/
    }
}