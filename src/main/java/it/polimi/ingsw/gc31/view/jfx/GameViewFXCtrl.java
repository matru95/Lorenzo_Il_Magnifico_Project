package it.polimi.ingsw.gc31.view.jfx;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.client.Client;
import it.polimi.ingsw.gc31.client.RMIClient;
import it.polimi.ingsw.gc31.client.SocketClient;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.view.GameViewCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameViewFXCtrl implements GameViewCtrl {

    private static final String TOWERS = "towers";
    private static final String TOWERSPACES = "towerSpaces";
    private static final String CARDID = "cardID";

    private StringBuilder sb;
    private static final String PL = "players";
    private static final String CARDS = "cards";

    private String myPlayerID;
    private String myPlayerName;
    private final Client client;
    private final ObjectMapper mapper;
    private JsonNode rootInstance;
    private JsonNode rootBoard;
    private JsonNode rootMe;

    public GameViewFXCtrl() throws InterruptedException, NotBoundException, IOException, ClassNotFoundException {

        this.sb = new StringBuilder();

        this.mapper = new ObjectMapper();
        BufferedReader br;
        System.out.println("Choose between using \"SOCKET\" or \"RMI\":");
        String choice;

        do {

            br = new BufferedReader(new InputStreamReader(System.in));
            choice = br.readLine();

            /*System.out.println("Hello, pls enter your name:");
            br = new BufferedReader(new InputStreamReader(System.in));
            this.myPlayerName = br.readLine();
            System.out.println("Now enter the ip address for the server to which connect (\"127.0.0.1\" for localhost):");
            br = new BufferedReader(new InputStreamReader(System.in));
            String serverIP = br.readLine();*/

            if (choice.equalsIgnoreCase("SOCKET")) {
                this.myPlayerName = "SOCKETFX";
                this.client = new SocketClient("127.0.0.1", myPlayerName, this);
                break;
            }

            if (choice.equalsIgnoreCase("RMI")) {
                this.myPlayerName = "RMIFX";
                this.client = new RMIClient("127.0.0.1", myPlayerName, this);
                break;
            }
        } while (true);

    }

    @FXML
    private ImageView board;

    @FXML
    private ImageView green0;

    @FXML
    private ImageView green1;

    @FXML
    private ImageView green2;

    @FXML
    private ImageView green3;

    @FXML
    private ImageView blue0;

    @FXML
    private ImageView blue1;

    @FXML
    private ImageView blue2;

    @FXML
    private ImageView blue3;

    @FXML
    private ImageView yellow0;

    @FXML
    private ImageView yellow1;

    @FXML
    private ImageView yellow2;

    @FXML
    private ImageView yellow3;

    @FXML
    private ImageView purple0;

    @FXML
    private ImageView purple1;

    @FXML
    private ImageView purple2;

    @FXML
    private ImageView purple3;

    @FXML
    private Text diceBlack;

    @FXML
    private Text diceWhite;

    @FXML
    private Text diceOrange;

    @FXML
    private ImageView faithTile1;

    @FXML
    private ImageView faithTile2;

    @FXML
    private ImageView faithTile3;

    @FXML
    private Circle space1;

    @FXML
    private Circle space2;

    @FXML
    private Circle space3;

    @FXML
    private Circle space4;

    @FXML
    private Circle space5;

    @FXML
    private Circle space6;

    @FXML
    private Circle space7;

    @FXML
    private Circle space8;

    @FXML
    private Circle space9;

    @FXML
    private Circle space10;

    @FXML
    private Circle space11;

    @FXML
    private Circle space12;

    @FXML
    private Circle space13;

    @FXML
    private Circle space14;

    @FXML
    private Circle space15;

    @FXML
    private Circle space16;

    @FXML
    private Circle space17;

    @FXML
    private Circle space18;

    @FXML
    private Circle space19;

    @FXML
    private Circle space20;

    @FXML
    private Circle space21;

    @FXML
    private Circle space22;

    @FXML
    private Rectangle space23;

    @FXML
    private Circle order1;

    @FXML
    private Circle order2;

    @FXML
    private Circle order3;

    @FXML
    private Circle order4;

    @FXML
    private Circle myOrange;

    @FXML
    private Circle myBlack;

    @FXML
    private Circle myNeutral;

    @FXML
    private Circle myWhite;

    @FXML
    private TextArea textQuery;

    @FXML
    private Text header;

    @FXML
    private Tab blueTab;

    @FXML
    private ImageView redGreen11;

    @FXML
    private ImageView redGreen21;

    @FXML
    private ImageView redGreen31;

    @FXML
    private ImageView redGreen41;

    @FXML
    private ImageView redGreen51;

    @FXML
    private ImageView redGreen61;

    @FXML
    private ImageView redYellow11;

    @FXML
    private ImageView redYellow21;

    @FXML
    private ImageView redYellow31;

    @FXML
    private ImageView redYellow41;

    @FXML
    private ImageView redYellow51;

    @FXML
    private ImageView redYellow61;

    @FXML
    private ImageView redGreen111;

    @FXML
    private ImageView redGreen211;

    @FXML
    private ImageView redGreen311;

    @FXML
    private ImageView redGreen411;

    @FXML
    private ImageView redGreen511;

    @FXML
    private ImageView redGreen611;

    @FXML
    private ImageView redYellow111;

    @FXML
    private ImageView redYellow211;

    @FXML
    private ImageView redYellow311;

    @FXML
    private ImageView redYellow411;

    @FXML
    private ImageView redYellow511;

    @FXML
    private ImageView redYellow611;

    @FXML
    private TextArea blueText;

    @FXML
    private Tab greenTab;

    @FXML
    private ImageView redGreen112;

    @FXML
    private ImageView redGreen212;

    @FXML
    private ImageView redGreen312;

    @FXML
    private ImageView redGreen412;

    @FXML
    private ImageView redGreen512;

    @FXML
    private ImageView redGreen612;

    @FXML
    private ImageView redYellow112;

    @FXML
    private ImageView redYellow212;

    @FXML
    private ImageView redYellow312;

    @FXML
    private ImageView redYellow412;

    @FXML
    private ImageView redYellow512;

    @FXML
    private ImageView redYellow612;

    @FXML
    private TextArea greenText;

    @FXML
    private Tab redTab;

    @FXML
    private ImageView redGreen1;

    @FXML
    private ImageView redGreen2;

    @FXML
    private ImageView redGreen3;

    @FXML
    private ImageView redGreen4;

    @FXML
    private ImageView redGreen5;

    @FXML
    private ImageView redGreen6;

    @FXML
    private ImageView redYellow1;

    @FXML
    private ImageView redYellow2;

    @FXML
    private ImageView redYellow3;

    @FXML
    private ImageView redYellow4;

    @FXML
    private ImageView redYellow5;

    @FXML
    private ImageView redYellow6;

    @FXML
    private TextArea redText;

    @FXML
    private Tab yellowTab;

    @FXML
    private ImageView redGreen1111;

    @FXML
    private ImageView redGreen2111;

    @FXML
    private ImageView redGreen3111;

    @FXML
    private ImageView redGreen4111;

    @FXML
    private ImageView redGreen5111;

    @FXML
    private ImageView redGreen6111;

    @FXML
    private ImageView redYellow1111;

    @FXML
    private ImageView redYellow2111;

    @FXML
    private ImageView redYellow3111;

    @FXML
    private ImageView redYellow4111;

    @FXML
    private ImageView redYellow5111;

    @FXML
    private ImageView redYellow6111;

    @FXML
    private TextArea yellowText;

    @FXML
    void onClick(MouseEvent event) {

    }

    @FXML
    void onMemberSelected(MouseEvent event) {

    }

    @FXML
    void onSpaceSelected(MouseEvent event) {

    }


    @FXML
    void initialize() {
    }

    private void cardSetter(ImageView cardView, Integer cardID) {
        cardView.setImage(new Image(new File("src/main/resources/javafx/cards/cardID" + cardID + ".png").toURI().toString()));
    }

    private void diceSetter(Text diceText, Integer diceValue) {
        diceText.setText(diceValue.toString());
    }

    private void faithTileSetter(ImageView faithTileView, Integer faithTileID) {
        faithTileView.setImage(new Image(new File("src/main/resources/javafx/faithTiles/excomm" + faithTileID + ".png").toURI().toString()));
    }

    private void playerOrderColorSetter(Circle circle, String playerColor) {
        if (!(playerColor == null))
            circle.setFill(Paint.valueOf(playerColor));
        circle.setVisible(true);
    }

    private void spaceSetter(Circle spaceShape, JsonNode node) {

        if (node.path("isOccupied").toString().equals("true")) {

            if (beauty(node.path("familyMember").path("color")).equals("NEUTRAL")) {
                spaceShape.setFill(Paint.valueOf(beauty(node.path("familyMember").path("playerColor"))));
                spaceShape.setStroke(Paint.valueOf("#eddcc6"));
            } else {
                spaceShape.setFill(Paint.valueOf(beauty(node.path("familyMember").path("color"))));
                spaceShape.setStroke(Paint.valueOf(beauty(node.path("familyMember").path("playerColor"))));
            }
            spaceShape.setRadius(30);
            spaceShape.setStrokeWidth(15);
            spaceShape.setVisible(true);
        } else
            spaceShape.setVisible(false);
    }

    private void actionSpaceSetter(Circle spaceShape, JsonNode node) {

        if (node.path("isOccupied").toString().equals("true")) {
            spaceShape.setRadius(30);
            spaceShape.setStrokeWidth(15);

            if (beauty(node.path("familyMembers").path(0).path("color")).equals("NEUTRAL")) {
                spaceShape.setFill(Paint.valueOf(beauty(node.path("familyMembers").path(0).path("playerColor"))));
                spaceShape.setStroke(Paint.valueOf("#eddcc6"));
            } else {
                spaceShape.setFill(Paint.valueOf(beauty(node.path("familyMembers").path(0).path("color"))));
                spaceShape.setStroke(Paint.valueOf(beauty(node.path("familyMembers").path(0).path("playerColor"))));
            }
            spaceShape.setFill(Paint.valueOf(beauty(node.path("familyMembers").path(0).path("color"))));
            spaceShape.setStroke(Paint.valueOf(beauty(node.path("familyMembers").path(0).path("playerColor"))));
            spaceShape.setVisible(true);
        } else
            spaceShape.setVisible(false);
    }

    private void spaceSetter(Rectangle spaceShape, JsonNode node) {
        if (node.path("isOccupied").toString().equals("true")) {
            spaceShape.setStroke(Paint.valueOf(beauty(node.path("familyMember").path("playerColor"))));
            spaceShape.setVisible(true);
        } else
            spaceShape.setVisible(false);
    }

    @Override
    public void update(Map<String, String> gameState) throws IOException {
        this.rootInstance = mapper.readTree(gameState.get("GameInstance"));
        this.rootBoard = mapper.readTree(gameState.get("GameBoard"));

        Map<Integer, String> orderedPlayers = new HashMap<>();
        Integer numOfPlayers = 0;
        for (JsonNode singlePlayer: rootInstance.path("players")) {
            if (beauty(singlePlayer.path("playerID")).equals(myPlayerID))
                this.rootMe = singlePlayer;
            orderedPlayers.put(singlePlayer.path("playerOrder").asInt(), beauty(singlePlayer.path("playerColor")));
            numOfPlayers++;
        }

        if (numOfPlayers.equals(2)) {
            board.setImage(new Image(new File("src/main/resources/javafx/boards/board_2.png").toURI().toString()));
            space21.setDisable(true);
            space22.setDisable(true);
        } else if (numOfPlayers.equals(3)) {
            board.setImage(new Image(new File("src/main/resources/javafx/boards/board_3.png").toURI().toString()));
            space21.setDisable(true);
            space22.setDisable(true);
        }

        header.setText("[" + beauty(rootMe.path("playerColor")) + "] " + beauty(rootMe.path("playerName")) + "   AGE:[" + beauty(rootInstance.path("age")) + "]   TURN:[" + beauty(rootInstance.path("turn")) + "]");

        playerOrderColorSetter(order1, orderedPlayers.get(1));
        playerOrderColorSetter(order2, orderedPlayers.get(2));
        playerOrderColorSetter(order3, orderedPlayers.get(3));
        playerOrderColorSetter(order4, orderedPlayers.get(4));

        myBlack.setStroke(Paint.valueOf(beauty(rootMe.path("playerColor"))));
        myWhite.setStroke(Paint.valueOf(beauty(rootMe.path("playerColor"))));
        myOrange.setStroke(Paint.valueOf(beauty(rootMe.path("playerColor"))));
        myNeutral.setFill(Paint.valueOf(beauty(rootMe.path("playerColor"))));
        myBlack.setVisible(true);
        myWhite.setVisible(true);
        myOrange.setVisible(true);
        myNeutral.setVisible(true);

        cardSetter(green0, rootBoard.path(TOWERS).path(CardColor.GREEN.toString()).path(TOWERSPACES).path("0").path("card").path(CARDID).asInt());
        cardSetter(green1, rootBoard.path(TOWERS).path(CardColor.GREEN.toString()).path(TOWERSPACES).path("1").path("card").path(CARDID).asInt());
        cardSetter(green2, rootBoard.path(TOWERS).path(CardColor.GREEN.toString()).path(TOWERSPACES).path("2").path("card").path(CARDID).asInt());
        cardSetter(green3, rootBoard.path(TOWERS).path(CardColor.GREEN.toString()).path(TOWERSPACES).path("3").path("card").path(CARDID).asInt());
        cardSetter(blue0, rootBoard.path(TOWERS).path(CardColor.BLUE.toString()).path(TOWERSPACES).path("0").path("card").path(CARDID).asInt());
        cardSetter(blue1, rootBoard.path(TOWERS).path(CardColor.BLUE.toString()).path(TOWERSPACES).path("1").path("card").path(CARDID).asInt());
        cardSetter(blue2, rootBoard.path(TOWERS).path(CardColor.BLUE.toString()).path(TOWERSPACES).path("2").path("card").path(CARDID).asInt());
        cardSetter(blue3, rootBoard.path(TOWERS).path(CardColor.BLUE.toString()).path(TOWERSPACES).path("3").path("card").path(CARDID).asInt());
        cardSetter(yellow0, rootBoard.path(TOWERS).path(CardColor.YELLOW.toString()).path(TOWERSPACES).path("0").path("card").path(CARDID).asInt());
        cardSetter(yellow1, rootBoard.path(TOWERS).path(CardColor.YELLOW.toString()).path(TOWERSPACES).path("1").path("card").path(CARDID).asInt());
        cardSetter(yellow2, rootBoard.path(TOWERS).path(CardColor.YELLOW.toString()).path(TOWERSPACES).path("2").path("card").path(CARDID).asInt());
        cardSetter(yellow3, rootBoard.path(TOWERS).path(CardColor.YELLOW.toString()).path(TOWERSPACES).path("3").path("card").path(CARDID).asInt());
        cardSetter(purple0, rootBoard.path(TOWERS).path(CardColor.PURPLE.toString()).path(TOWERSPACES).path("0").path("card").path(CARDID).asInt());
        cardSetter(purple1, rootBoard.path(TOWERS).path(CardColor.PURPLE.toString()).path(TOWERSPACES).path("1").path("card").path(CARDID).asInt());
        cardSetter(purple2, rootBoard.path(TOWERS).path(CardColor.PURPLE.toString()).path(TOWERSPACES).path("2").path("card").path(CARDID).asInt());
        cardSetter(purple3, rootBoard.path(TOWERS).path(CardColor.PURPLE.toString()).path(TOWERSPACES).path("3").path("card").path(CARDID).asInt());

        diceSetter(diceBlack, rootBoard.path("dices").path("BLACK").asInt());
        diceSetter(diceWhite, rootBoard.path("dices").path("WHITE").asInt());
        diceSetter(diceOrange, rootBoard.path("dices").path("ORANGE").asInt());

        faithTileSetter(faithTile1, rootBoard.path("faithTiles").path("1").path("id").asInt());
        faithTileSetter(faithTile2, rootBoard.path("faithTiles").path("2").path("id").asInt());
        faithTileSetter(faithTile3, rootBoard.path("faithTiles").path("3").path("id").asInt());

        spaceSetter(space1, rootBoard.path(TOWERS).path(CardColor.GREEN.toString()).path(TOWERSPACES).path("0"));
        spaceSetter(space2, rootBoard.path(TOWERS).path(CardColor.GREEN.toString()).path(TOWERSPACES).path("1"));
        spaceSetter(space3, rootBoard.path(TOWERS).path(CardColor.GREEN.toString()).path(TOWERSPACES).path("2"));
        spaceSetter(space4, rootBoard.path(TOWERS).path(CardColor.GREEN.toString()).path(TOWERSPACES).path("3"));
        spaceSetter(space5, rootBoard.path(TOWERS).path(CardColor.BLUE.toString()).path(TOWERSPACES).path("0"));
        spaceSetter(space6, rootBoard.path(TOWERS).path(CardColor.BLUE.toString()).path(TOWERSPACES).path("1"));
        spaceSetter(space7, rootBoard.path(TOWERS).path(CardColor.BLUE.toString()).path(TOWERSPACES).path("2"));
        spaceSetter(space8, rootBoard.path(TOWERS).path(CardColor.BLUE.toString()).path(TOWERSPACES).path("3"));
        spaceSetter(space9, rootBoard.path(TOWERS).path(CardColor.YELLOW.toString()).path(TOWERSPACES).path("0"));
        spaceSetter(space10, rootBoard.path(TOWERS).path(CardColor.YELLOW.toString()).path(TOWERSPACES).path("1"));
        spaceSetter(space11, rootBoard.path(TOWERS).path(CardColor.YELLOW.toString()).path(TOWERSPACES).path("2"));
        spaceSetter(space12, rootBoard.path(TOWERS).path(CardColor.YELLOW.toString()).path(TOWERSPACES).path("3"));
        spaceSetter(space13, rootBoard.path(TOWERS).path(CardColor.PURPLE.toString()).path(TOWERSPACES).path("0"));
        spaceSetter(space14, rootBoard.path(TOWERS).path(CardColor.PURPLE.toString()).path(TOWERSPACES).path("1"));
        spaceSetter(space15, rootBoard.path(TOWERS).path(CardColor.PURPLE.toString()).path(TOWERSPACES).path("2"));
        spaceSetter(space16, rootBoard.path(TOWERS).path(CardColor.PURPLE.toString()).path(TOWERSPACES).path("3"));
        actionSpaceSetter(space17, rootBoard.path("boardSpaces").path("17"));
        actionSpaceSetter(space18, rootBoard.path("boardSpaces").path("18"));
        spaceSetter(space19, rootBoard.path("boardSpaces").path("19"));
        spaceSetter(space20, rootBoard.path("boardSpaces").path("20"));
        spaceSetter(space21, rootBoard.path("boardSpaces").path("21"));
        spaceSetter(space22, rootBoard.path("boardSpaces").path("22"));
        spaceSetter(space23, rootBoard.path("boardSpaces").path("23"));

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
        result.put("servantsToPay", servants.toString());

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

    @Override
    public Map<String, String> costQuery(Map<String, String> map) throws IOException {

        HashMap<String, String> result = new HashMap<>();
        Integer choice;

        for (JsonNode singlePurpleCard: rootBoard.path(CARDS).path("PURPLE"))
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
        for (Map.Entry<String, String> entry: map.entrySet()) {
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
    public void setPlayerID(String playerID) {
        this.myPlayerID = playerID;
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
            if (beauty(singlePlayer.path("playerID")).equals(myPlayerID))
                return singlePlayer.path("res").path("SERVANTS").asInt();
        return 0;
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
                .replace(",", ", ");
    }

}