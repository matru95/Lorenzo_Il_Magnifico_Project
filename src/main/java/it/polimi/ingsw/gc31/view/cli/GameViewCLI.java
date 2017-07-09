package it.polimi.ingsw.gc31.view.cli;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import it.polimi.ingsw.gc31.client.RMIClient;
import it.polimi.ingsw.gc31.client.SocketClient;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.view.GameViewCtrl;

import java.io.*;
import java.rmi.NotBoundException;
import java.util.*;

public class GameViewCLI implements GameViewCtrl, Serializable {

    private static final String PN = "playerName";
    private static final String PL = "players";
    private static final String CARDS = "cards";
    private static final String CARDID = "cardID";

    private String myPlayerName;
    private String myPlayerID;
    private ObjectMapper mapper;
    private StringBuilder sb;
    private transient JsonNode rootInstance;
    private transient JsonNode rootBoard;

    public GameViewCLI() throws IOException, NotBoundException, InterruptedException, ClassNotFoundException {

        this.mapper = new ObjectMapper();
        this.sb = new StringBuilder();
        myPlayerNameQuery();
        connectionQuery(serverIPQuery());
        printLogo();
        printStringBuilder();

    }

    public GameViewCLI(String myPlayerName, String serverIP) throws IOException, NotBoundException, InterruptedException {

        this.mapper = new ObjectMapper();
        this.sb = new StringBuilder();
        this.myPlayerName = myPlayerName;
        connectionQuery(serverIP);
        printLogo();
        printStringBuilder();

    }

    private void myPlayerNameQuery() throws IOException {
        sb.append("Hello, pls enter your name:");
        printStringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        this.myPlayerName = br.readLine();
    }

    private String serverIPQuery() throws IOException {
        sb.append("Now enter the ip address for the server to which connect (\"127.0.0.1\" for localhost):");
        printStringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    private void connectionQuery(String serverIP) throws IOException, InterruptedException, NotBoundException {
        sb.append("Choose between using \"SOCKET\" or \"RMI\":");
        printStringBuilder();
        String choice;
        do {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            choice = br.readLine();

            if (choice.equalsIgnoreCase("SOCKET")) {
                new SocketClient(serverIP, this.myPlayerName, this);
                break;
            }

            if (choice.equalsIgnoreCase("RMI")) {
                new RMIClient(serverIP, this.myPlayerName, this);
                break;
            }
            sb.append("Wrong choose, try again entering one between these:\"SOCKET\" or \"RMI\":");
            printStringBuilder();
        } while (true);
    }

    /**
     * Method to print on Console the current GameViewCtrl of Client's Player.
     */
    private void printView() {

        printDivider();
        printHeader();
        printTowers();
        printSpaces();
        printFaithCards();
        printPlayers();
        printFamilyMembers();
        printDivider();
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

        for (CardColor cardColor : CardColor.values()) {
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
        at.addRow("~ FAITH CARDS ~", beauty(faithTiles.path("1")), beauty(faithTiles.path("2")), beauty(faithTiles.path("3")));
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

        for (JsonNode singlePlayer : players) {

            AsciiTable atp = new AsciiTable();
            atp.addRule();

            atp.addRow("[" + beauty(singlePlayer.path("playerColor")) + "]: " + beauty(singlePlayer.path(PN)),
                    null, null, null, null, null,
                    "RESOURCES: [ " + beauty(singlePlayer.path("res")).replace(",", " | ") + " ]"
            );
            atp.addRule();

            for (CardColor cardColor : CardColor.values()) {
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

            atp.addRow("PLAYER TILE:", null, null, null, null, null, beauty(singlePlayer.path("playerTile")));
            atp.addRule();
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
        at.addRow("~ PLAYERS ~", order.toString().replace("\"", ""));
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
        for (JsonNode singlePlayer : players) {
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
     * Method to print a divider for a Board.
     */
    private void printDivider() {
        sb.append("\n\n\n+--------------------------------------------------------------------------------------------------------------------------------------------------+\n")
                .append("+--------------------------------------------------------------------------------------------------------------------------------------------------+\n\n\n");
    }


    /**
     * Method to print a game logo for the CLI View.
     */
    private void printLogo() {
        sb.append("\n").append("                                  ██╗      ██████╗ ██████╗ ███████╗███╗   ██╗███████╗ ██████╗          ██╗██╗     \n")
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

    @Override
    public void update(Map<String, String> gameState) throws IOException {
        this.rootInstance = mapper.readTree(gameState.get("GameInstance"));
        this.rootBoard = mapper.readTree(gameState.get("GameBoard"));
        printView();
    }

    @Override
    public void movementFail(Map<String, String> map) {
        sb.append("Movement is NOT Valid, try again: \n");
        printStringBuilder();
    }

    @Override
    public Map<String, String> movementQuery() throws IOException {

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
        result.put("positionID", id.toString());
        /*
        Integer max = getMyServants();

        sb.append("\nEnter the number of servants you'd like to add (if you won't enter 0):");
        printStringBuilder();
        do {
            try {
                servants = readInteger();
                if (servants >= 0 && servants <= max) break;
                sb.append("You must insert a valid number between 0 and ").append(max).append(":");
                printStringBuilder();
            } catch (NumberFormatException e) {
                sb.append("You must insert a valid number between 0 and ").append(max).append(":");
                printStringBuilder();
            }
        } while (true);
        result.put("servantsToPay", servants.toString());*/
        result.put("servantsToPay", "0");
        return result;
    }

    @Override
    public Map<String, String> parchmentQuery(Map<String, String> map) throws IOException {

        HashMap<String, String> result = new HashMap<>();
        int numOfParchments = Integer.parseInt(map.get("parchments"));
        ArrayList<Integer> lastChoices = new ArrayList<>();
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

    @Override
    public Map<String, String> faithQuery() throws IOException {

        HashMap<String, String> result = new HashMap<>();
        String choice;

        for (JsonNode singleFaithTile : rootBoard.path("faithTiles"))
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

    @Override
    public Map<String, String> costQuery(Map<String, String> map) throws IOException {

        HashMap<String, String> result = new HashMap<>();
        Integer choice;

        for (JsonNode singlePurpleCard : rootBoard.path(CARDS).path("PURPLE"))
            if (beauty(singlePurpleCard.path(CARDID)).equals(map.get(CARDID)))
                sb.append("\n{(")
                        .append(beauty(singlePurpleCard.path("cost").path(0))).append(") OR (")
                        .append(beauty(singlePurpleCard.path("cost").path(1))).append(")}");
        printStringBuilder();

        do {
            try {
                choice = readInteger();
                if (choice.equals(1) || choice.equals(2)) break;
                sb.append("You must insert a valid number among these: (1, 2).\n");
                printStringBuilder();
            } catch (IllegalArgumentException e) {
                sb.append("You must insert a valid number among these: (1, 2).\n");
                printStringBuilder();
            }
        } while (true);
        result.put(CARDID, map.get(CARDID));
        result.put("cardCostChoice", choice.toString());

        return result;
    }

    @Override
    public Map<String, String> exchangeQuery(Map<String, String> map) throws IOException {

        HashMap<String, String> result = new HashMap<>();
        Integer exchangesNumber = map.size() - 2;
        Integer choice;
        String str;

        switch (exchangesNumber) {
            case 2:
                sb.append("You've to choose whether or not activate the following exchange effects for the card #")
                        .append(map.get(CARDID)).append(" \"").append("cardName").append("\":\n")
                        .append("1st Exchange Effect: ").append(map.get("1").replace("\n"," ")).append("\n")
                        .append("2nd Exchange Effect: ").append(map.get("2").replace("\n"," ")).append("\n")
                        .append("Type 0 to avoid activation of the exchanges;\n")
                        .append("Type 1 to activate the first exchange effect;\n")
                        .append("Type 2 to activate the second exchange effect.");
                printStringBuilder();
                str = "(0, 1, 2)";
                break;
            case 1:
            default:
                sb.append("You've to choose whether or not activate the following exchange effect for the card #")
                        .append(map.get(CARDID)).append(" \"").append(map.get("cardName")).append("\":\n")
                        .append("Exchange Effect: ").append(map.get("1").replace("\n"," ")).append("\n")
                        .append("Type 0 to avoid activation of the exchange;\n")
                        .append("Type 1 to activate the exchange effect.");
                printStringBuilder();
                str = "(0, 1)";
        }

        do {
            try {
                choice = readInteger();

                if ((exchangesNumber.equals(1) && (choice.equals(0) || choice.equals(1)))
                        || (exchangesNumber.equals(2) && (choice.equals(0) || choice.equals(1) || choice.equals(2))))
                    break;
                sb.append("You must insert a valid number among these: ").append(str);
                printStringBuilder();
            } catch (IllegalArgumentException e) {
                sb.append("You must insert a valid number among these: ").append(str);
                printStringBuilder();
            }
        } while (true);

        result.put(CARDID, map.get(CARDID));
        result.put("choice", choice.toString());

        return result;
    }

    @Override
    public Map<String, String> freeCardQuery(Map<String, String> map) throws IOException {

        HashMap<String, String> result = new HashMap<>();
        StringBuilder str = new StringBuilder();
        Integer choice;

        Boolean isFirstLoop = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (isFirstLoop) str.append(entry.getValue());
            else str.append(", ").append(entry.getValue());
            isFirstLoop = false;

        }

        sb.append("You've to choose whether or not picking a free card due to the BLUE CARD's instantEffect.\n")
                .append("Here there's a list of possible cards you can pick: ").append(str);
        printStringBuilder();

        do {
            try {
                choice = readInteger();
                if (map.containsValue(choice.toString())) break;
                sb.append("You must insert a valid number between these: ").append(str);
                printStringBuilder();
            } catch (IllegalArgumentException e) {
                sb.append("You must insert a valid number between these: ").append(str);
                printStringBuilder();
            }
        } while (true);

        result.put(CARDID, choice.toString());

        return result;
    }

    @Override
    public void timeoutAlert() {

        sb.append("\nALERT: You have exceeded the time limit, you'll skip this movement\n");
        printStringBuilder();

    }

    @Override
    public void setPlayerID(String playerID) {
        this.myPlayerID = playerID;
    }

    /**
     * Method to read a String from Input.
     *
     * @return String
     * @throws IOException: Error during input reading.
     */
    private String readString() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        br.reset();
        return br.readLine();
    }

    /**
     * Method to read an Integer from Input.
     *
     * @return Integer
     * @throws IOException: Error during input reading.
     */
    private int readInteger() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return Integer.valueOf(br.readLine());
    }

    /**
     * Method to read a DiceColor from Input.
     *
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
    private Integer getMyServants() {
        for (JsonNode singlePlayer : rootInstance.path(PL))
            if (beauty(singlePlayer.path("playerID")).equals(myPlayerID))
                return singlePlayer.path("res").path("SERVANTS").asInt();
        return 0;
    }

    /**
     * This method take a JsonNode as input, converting it into a String,
     * removing Double Quotes and Braces and adding a space after a Comma.
     *
     * @param node: JsonNode for a JSON Parser.
     * @return String
     */
    private static String beauty(JsonNode node) {
        return node.toString().replace("\"", "")
                .replace("{", "")
                .replace("}", "")
                .replace(",", ", ")
                .replace("currentPositionID:0", "[HASN'T_MOVED_YET]");
    }

}