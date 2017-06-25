package it.polimi.ingsw.gc31.view.cli;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import it.polimi.ingsw.gc31.controller.PlayerController;
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

public class GameViewCLI implements GameView {

    //TODO RIMUOVI cards
    public static List<Card> cards;

    private Map<String, String> gameState;
    private final UUID playerID;
    private final ObjectMapper mapper;
    private JsonNode rootInstance, rootBoard;

    public GameViewCLI(UUID playerID) throws IOException {
        this.playerID = playerID;
        this.mapper = new ObjectMapper();
        printLogo();
    }

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
    private void printView() throws IOException {
        printHeader();
        printTowers();
        //printSpaces();
        printPlayers();
        printFamilyMembers();
        printMovementQuery();
        //System.out.print(gameState.get("GameBoard").toString());
    }

    /**
     * Method to print an header with information regard the current GameInstance.
     */
    private void printHeader() throws IOException {

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

        /*
        rootNode = mapper.readTree(gameState.get("GameInstance"));

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
        */
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
        at.getRenderer().setCWC(new CWC_FixedWidth().add(30).add(30).add(30).add(30));
        System.out.println(at.render() + "\n");
    }

    /**
     * Method to print information (status) regarding a single player.
     */
    private void printPlayers() throws IOException {

        JsonNode players = rootInstance.path("players");
        Map<Integer, String> orderedPlayers = new HashMap<>();
        String render = "";

        for (JsonNode singlePlayer: players) {

            AsciiTable atp = new AsciiTable();
            atp.addRule();

            atp.addRow("[" + go(singlePlayer.path("playerColor")) + "]: " + go(singlePlayer.path("playerName")),
                    null, null, null, null, null,
                    "RESOURCES: [ " + go(singlePlayer.path("res")).replace(","," | ") + " ]"
            );
            atp.addRule();

            for (CardColor cardColor: CardColor.values()) {
                String color = cardColor.toString();
                atp.addRow(color + " CARDS:",
                        go(singlePlayer.path("cards").path(color).path(0)),
                        go(singlePlayer.path("cards").path(color).path(1)),
                        go(singlePlayer.path("cards").path(color).path(2)),
                        go(singlePlayer.path("cards").path(color).path(3)),
                        go(singlePlayer.path("cards").path(color).path(4)),
                        go(singlePlayer.path("cards").path(color).path(5))
                );
                atp.addRule();
            }

            atp.setTextAlignment(TextAlignment.CENTER);
            atp.getRenderer().setCWC(new CWC_FixedWidth().add(20).add(20).add(20).add(20).add(20).add(20).add(20));
            render += atp.render() + "\n";
            orderedPlayers.put(singlePlayer.path("playerOrder").asInt(), singlePlayer.path("playerName").toString());
        }

        String order = "PLAYERS ORDER: ";
        for (int i = 1; i <= 4; i++) {
            if (orderedPlayers.get(i) != null) order += "[" + i + "]: " + orderedPlayers.get(i) + " ";
        }

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("PLAYERS", order.replace("\"",""));
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
/*
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
*/
    }

    /**
     * This method is used to print the query for the player,
     * in order to choose the space in which move the FamilyMember
     * and its color.
     */
    private void printMovementQuery() throws IOException {
        /*
        Integer id = 0;
        DiceColor color;

        System.out.println("\n" + playerModel.getPlayerName() + ", it's your turn, move a Family Member into a Space.\n");

        System.out.println("Now insert the FamilyMember's color you'd like to use, \n" +
                        "between these available: BLACK, WHITE, ORANGE, NEUTRAL.");
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
        */
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
    public void update(Map<String, String> gameState) throws IOException {
        this.gameState = gameState;
        this.rootInstance = mapper.readTree(gameState.get("GameInstance"));
        this.rootBoard = mapper.readTree(gameState.get("GameBoard"));
        printView();
    }

    /**
     * This method take a JsonNode as input, converting it into a String and removing Double Quotes and Braces.
     * @param node: JsonNode for a JSON Parser.
     * @return String
     */
    public static String go(JsonNode node) {
        return node.toString().replace("\"", "").replace("{","").replace("}","").replace(",", ", ");

    }

    public static void main(String[] args) throws NoResourceMatch, IOException {
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

        Map<String, String> gameState = new HashMap<>();
        gameState.put("GameInstance", gameInstance.toString());
        gameState.put("GameBoard", gameInstance.getGameBoard().toString());
        GameViewCLI view = new GameViewCLI(p1.getPlayerID());
        (new Thread(gameInstance)).start();
        view.update(gameState);
    }
}