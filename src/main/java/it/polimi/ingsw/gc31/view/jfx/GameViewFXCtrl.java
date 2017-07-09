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
import it.polimi.ingsw.gc31.client.RMIClient;
import it.polimi.ingsw.gc31.client.SocketClient;
import it.polimi.ingsw.gc31.enumerations.CardColor;
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
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class GameViewFXCtrl implements GameViewCtrl {

    private static final String TOWERS = "towers";
    private static final String POSITIONID = "positionID";
    private static final String TOWERSPACES = "towerSpaces";
    private static final String CARDID = "cardID";
    private static final String DICES = "dices";
    private static final String DICECOLOR = "diceColor";
    private static final String ISOCCUPIED = "isOccupied";
    private static final String PLCOL = "playerColor";
    private static final String PL = "players";
    private static final String CARDS = "cards";
    private static final String BLUE = "BLUE";
    private static final String GREEN = "GREEN";
    private static final String PURPLE = "PURPLE";
    private static final String YELLOW = "YELLOW";
    private static final String OUTSIDE = "OUTSIDE";
    private static final String BLACK = "BLACK";
    private static final String WHITE = "WHITE";
    private static final String ORANGE = "ORANGE";
    private static final String NEUTRAL = "NEUTRAL";
    private static final String INSIDE = "INSIDE";
    private static final String FM = "familyMember";
    private static final String FMS = "familyMembers";
    private static final String ISSCON = "isSpaceChoiceOn";
    private static final String COLOR = "color";
    private static final String PLTILE = "playerTile";
    private static final String PLNAME = "playerName";

    private StringBuilder sb;
    private String myPlayerID;
    private String myPlayerName;
    private final ObjectMapper mapper;
    private JsonNode rootInstance;
    private JsonNode rootBoard;
    private JsonNode rootMe;

    private Map<String, Boolean> movementState;
    private Map<String, String> movementChoice;

    public GameViewFXCtrl() throws InterruptedException, NotBoundException, IOException, ClassNotFoundException {

        this.sb = new StringBuilder();
        this.movementState = new HashMap<>();
        this.movementState.put("isMemberChoiceOn", false);
        this.movementState.put(ISSCON, false);
        this.mapper = new ObjectMapper();

//        myPlayerNameQuery();
//        connectionQuery(serverIPQuery());

        this.myPlayerName = "CLIENTFX";
        connectionQuery("localhost");
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

    private void connectionQuery(String serverIP) throws IOException, ClassNotFoundException, InterruptedException, NotBoundException {
        sb.append("Choose between using \"SOCKET\" or \"RMI\":");
        printStringBuilder();
        String choice;
        do {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            choice = br.readLine();

            if (choice.equalsIgnoreCase("SOCKET")) {
                this.myPlayerName = "SOCKETFX";
                new SocketClient(serverIP, myPlayerName, this);
                break;
            }

            if (choice.equalsIgnoreCase("RMI")) {
                this.myPlayerName = "RMIFX";
                new RMIClient(serverIP, myPlayerName, this);
                break;
            }
            sb.append("Wrong choose, try again entering one between these:\"SOCKET\" or \"RMI\":");
            printStringBuilder();
        } while (true);
    }

    @FXML
    private ImageView board;

    @FXML
    private ImageView green0, green1, green2, green3;

    @FXML
    private ImageView blue0, blue1, blue2, blue3;

    @FXML
    private ImageView yellow0, yellow1, yellow2, yellow3;

    @FXML
    private ImageView purple0, purple1, purple2, purple3;

    @FXML
    private Text diceBlack, diceWhite, diceOrange;

    @FXML
    private ImageView faithTile1, faithTile2, faithTile3;

    @FXML
    private Circle space1, space2, space3, space4, space5, space6, space7, space8, space9, space10, space11;

    @FXML
    private Circle space12, space13, space14, space15, space16, space17, space18, space19, space20, space21, space22;

    @FXML
    private Circle councPal1, councPal2, councPal3, councPal4, councPal5, councPal6, councPal7, councPal8, councPal9, councPal10;

    @FXML
    private Circle order1, order2, order3, order4;

    @FXML
    private Circle myOrange, myBlack, myNeutral, myWhite;

    @FXML
    private Text header, textQuery, textFail;

    @FXML
    private Tab blueTab, greenTab, redTab, yellowTab;

    @FXML
    private ImageView blueTile, greenTile, redTile, yellowTile;

    @FXML
    private TextArea blueText, greenText, redText, yellowText;

    @FXML
    private ImageView blueGreen1, blueGreen2, blueGreen3, blueGreen4, blueGreen5, blueGreen6;

    @FXML
    private ImageView blueYellow1, blueYellow2, blueYellow3, blueYellow4, blueYellow5, blueYellow6;

    @FXML
    private ImageView blueBlue1, blueBlue2, blueBlue3, blueBlue4, blueBlue5, blueBlue6;

    @FXML
    private ImageView bluePurple1, bluePurple2, bluePurple3, bluePurple4, bluePurple5, bluePurple6;

    @FXML
    private ImageView greenGreen1, greenGreen2, greenGreen3, greenGreen4, greenGreen5, greenGreen6;

    @FXML
    private ImageView greenYellow1, greenYellow2, greenYellow3, greenYellow4, greenYellow5, greenYellow6;

    @FXML
    private ImageView greenBlue1, greenBlue2, greenBlue3, greenBlue4, greenBlue5, greenBlue6;

    @FXML
    private ImageView greenPurple1, greenPurple2, greenPurple3, greenPurple4, greenPurple5, greenPurple6;

    @FXML
    private ImageView redGreen1, redGreen2, redGreen3, redGreen4, redGreen5, redGreen6;

    @FXML
    private ImageView redYellow1, redYellow2, redYellow3, redYellow4, redYellow5, redYellow6;

    @FXML
    private ImageView redBlue1, redBlue2, redBlue3, redBlue4, redBlue5, redBlue6;

    @FXML
    private ImageView redPurple1, redPurple2, redPurple3, redPurple4, redPurple5, redPurple6;

    @FXML
    private ImageView yellowGreen1, yellowGreen2, yellowGreen3, yellowGreen4, yellowGreen5, yellowGreen6;

    @FXML
    private ImageView yellowYellow1, yellowYellow2, yellowYellow3, yellowYellow4, yellowYellow5, yellowYellow6;

    @FXML
    private ImageView yellowBlue1, yellowBlue2, yellowBlue3, yellowBlue4, yellowBlue5, yellowBlue6;

    @FXML
    private ImageView yellowPurple1, yellowPurple2, yellowPurple3, yellowPurple4, yellowPurple5, yellowPurple6;

    @FXML
    void onClickMemberBlack(MouseEvent event) {

        if (this.movementState.get("isMemberChoiceOn")) {
            movementChoice.put(DICECOLOR, "BLACK");
            myBlack.setStrokeType(StrokeType.valueOf(OUTSIDE));
            disableFamilyMembers();
            this.movementState.put("isMemberChoiceOn", false);
        }
    }

    @FXML
    void onClickMemberWhite(MouseEvent event) {

        if (this.movementState.get("isMemberChoiceOn")) {
            movementChoice.put(DICECOLOR, "WHITE");
            myWhite.setStrokeType(StrokeType.valueOf(OUTSIDE));
            disableFamilyMembers();
            this.movementState.put("isMemberChoiceOn", false);
        }
    }

    @FXML
    void onClickMemberOrange(MouseEvent event) {

        if (this.movementState.get("isMemberChoiceOn")) {
            movementChoice.put(DICECOLOR, "ORANGE");
            myOrange.setStrokeType(StrokeType.valueOf(OUTSIDE));
            disableFamilyMembers();
            this.movementState.put("isMemberChoiceOn", false);
        }
    }

    @FXML
    void onClickMemberNeutral(MouseEvent event) {

        if (this.movementState.get("isMemberChoiceOn")) {
            movementChoice.put(DICECOLOR, "NEUTRAL");
            myNeutral.setStrokeType(StrokeType.valueOf(OUTSIDE));
            disableFamilyMembers();
            this.movementState.put("isMemberChoiceOn", false);
        }
    }

    @FXML
    void onClickSpace1(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "1");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace2(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "2");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace3(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "3");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace4(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "4");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace5(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "5");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace6(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "6");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace7(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "7");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace8(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "8");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace9(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "9");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace10(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "10");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace11(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "11");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace12(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "12");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace13(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "13");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace14(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "14");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace15(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "15");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace16(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "16");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace17(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "17");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace18(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "18");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace19(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "19");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace20(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "20");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace21(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "21");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace22(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "22");
            this.movementState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace23(MouseEvent event) {

        if (this.movementState.get(ISSCON)) {
            movementChoice.put(POSITIONID, "23");
            this.movementState.put(ISSCON, false);
        }
    }

    private void disableFamilyMembers() {
        myNeutral.setDisable(true);
        myOrange.setDisable(true);
        myBlack.setDisable(true);
        myWhite.setDisable(true);
    }

    private void resetFamilyMembers() {
        myBlack.setStrokeType(StrokeType.valueOf(INSIDE));
        myNeutral.setStrokeType(StrokeType.valueOf(INSIDE));
        myOrange.setStrokeType(StrokeType.valueOf(INSIDE));
        myWhite.setStrokeType(StrokeType.valueOf(INSIDE));
        myNeutral.setDisable(false);
        myOrange.setDisable(false);
        myBlack.setDisable(false);
        myWhite.setDisable(false);
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
        if (playerColor != null)
            circle.setFill(Paint.valueOf(playerColor));
        circle.setVisible(true);
    }

    private void spaceSetter(Circle spaceShape, JsonNode node) {

        if (node.path(ISOCCUPIED).toString().equals("true")) {

            if (beauty(node.path(FM).path(COLOR)).equals(NEUTRAL)) {
                spaceShape.setFill(Paint.valueOf(beauty(node.path(FM).path(PLCOL))));
                spaceShape.setStroke(Paint.valueOf("#eddcc6"));
            } else {
                spaceShape.setFill(Paint.valueOf(beauty(node.path(FM).path(COLOR))));
                spaceShape.setStroke(Paint.valueOf(beauty(node.path(FM).path(PLCOL))));
            }
            spaceShape.setRadius(30);
            spaceShape.setStrokeWidth(15);
        } else {
            spaceShape.setRadius(40);
            spaceShape.setStroke(Paint.valueOf("#00ff0080"));
            spaceShape.setStrokeWidth(5);
        }
    }

    private void actionSpaceSetter(Circle spaceShape, JsonNode node) {

        if (node.path(ISOCCUPIED).toString().equals("true")) {

            if (beauty(node.path(FMS).path(0).path(COLOR)).equals(NEUTRAL)) {
                spaceShape.setFill(Paint.valueOf(beauty(node.path(FMS).path(0).path(PLCOL))));
                spaceShape.setStroke(Paint.valueOf("#eddcc6"));
            } else {
                spaceShape.setFill(Paint.valueOf(beauty(node.path(FMS).path(0).path(COLOR))));
                spaceShape.setStroke(Paint.valueOf(beauty(node.path(FMS).path(0).path(PLCOL))));
            }
            spaceShape.setRadius(30);
            spaceShape.setStrokeWidth(15);
        } else {
            spaceShape.setRadius(40);
            spaceShape.setStroke(Paint.valueOf("#00ff0080"));
            spaceShape.setStrokeWidth(5);
        }
    }

    private void councilPalaceSetter() {
        JsonNode councilPalaceNode = rootBoard.path("boardSpaces").path("23").path(FMS);
        if (councilPalaceNode.has(0))
            councilPalaceMemberSetter(councPal1, councilPalaceNode.path(0));
        else
            councPal1.setVisible(false);

        if (councilPalaceNode.has(1))
            councilPalaceMemberSetter(councPal2, councilPalaceNode.path(1));
        else
            councPal2.setVisible(false);

        if (councilPalaceNode.has(2))
            councilPalaceMemberSetter(councPal3, councilPalaceNode.path(2));
        else
            councPal3.setVisible(false);

        if (councilPalaceNode.has(3))
            councilPalaceMemberSetter(councPal4, councilPalaceNode.path(3));
        else
            councPal4.setVisible(false);

        if (councilPalaceNode.has(4))
            councilPalaceMemberSetter(councPal5, councilPalaceNode.path(4));
        else
            councPal5.setVisible(false);

        if (councilPalaceNode.has(5))
            councilPalaceMemberSetter(councPal6, councilPalaceNode.path(5));
        else
            councPal6.setVisible(false);

        if (councilPalaceNode.has(6))
            councilPalaceMemberSetter(councPal7, councilPalaceNode.path(6));
        else
            councPal7.setVisible(false);

        if (councilPalaceNode.has(7))
            councilPalaceMemberSetter(councPal8, councilPalaceNode.path(7));
        else
            councPal8.setVisible(false);

        if (councilPalaceNode.has(8))
            councilPalaceMemberSetter(councPal9, councilPalaceNode.path(8));
        else
            councPal9.setVisible(false);

        if (councilPalaceNode.has(9))
            councilPalaceMemberSetter(councPal10, councilPalaceNode.path(9));
        else
            councPal10.setVisible(false);
    }

    private void councilPalaceMemberSetter(Circle councilSpaceShape, JsonNode node) {

        if (beauty(node.path(COLOR)).equals(NEUTRAL)) {
            councilSpaceShape.setFill(Paint.valueOf(beauty(node.path(PLCOL))));
            councilSpaceShape.setStroke(Paint.valueOf("#eddcc6"));
        } else {
            councilSpaceShape.setFill(Paint.valueOf(beauty(node.path(COLOR))));
            councilSpaceShape.setStroke(Paint.valueOf(beauty(node.path(PLCOL))));
        }
        councilSpaceShape.setVisible(true);
    }

    private void bluePlayerSetter() {

        JsonNode bluePlayer = rootMe;
        for (JsonNode singlePlayer: rootInstance.path(PL))
            if (beauty(singlePlayer.path(PLCOL)).equals("BLUE"))
                bluePlayer = singlePlayer;

        blueTile.setImage(new Image(new File("src/main/resources/javafx/playerTiles/tile" + beauty(bluePlayer.path(PLTILE).path("id")) + ".png").toURI().toString()));
        blueText.setText("[" + beauty(bluePlayer.path(PLCOL)) + "] " + beauty(bluePlayer.path(PLNAME)) + ": " + beauty(bluePlayer.path("res")));
        JsonNode bluePlayerCards = bluePlayer.path(CARDS);

        cardSetter(blueBlue1, bluePlayerCards.path(BLUE).path(0).path(CARDID).asInt());
        cardSetter(blueBlue2, bluePlayerCards.path(BLUE).path(1).path(CARDID).asInt());
        cardSetter(blueBlue3, bluePlayerCards.path(BLUE).path(2).path(CARDID).asInt());
        cardSetter(blueBlue4, bluePlayerCards.path(BLUE).path(3).path(CARDID).asInt());
        cardSetter(blueBlue5, bluePlayerCards.path(BLUE).path(4).path(CARDID).asInt());
        cardSetter(blueBlue6, bluePlayerCards.path(BLUE).path(5).path(CARDID).asInt());

        cardSetter(blueGreen1, bluePlayerCards.path(GREEN).path(0).path(CARDID).asInt());
        cardSetter(blueGreen2, bluePlayerCards.path(GREEN).path(1).path(CARDID).asInt());
        cardSetter(blueGreen3, bluePlayerCards.path(GREEN).path(2).path(CARDID).asInt());
        cardSetter(blueGreen4, bluePlayerCards.path(GREEN).path(3).path(CARDID).asInt());
        cardSetter(blueGreen5, bluePlayerCards.path(GREEN).path(4).path(CARDID).asInt());
        cardSetter(blueGreen6, bluePlayerCards.path(GREEN).path(5).path(CARDID).asInt());

        cardSetter(bluePurple1, bluePlayerCards.path(PURPLE).path(0).path(CARDID).asInt());
        cardSetter(bluePurple2, bluePlayerCards.path(PURPLE).path(1).path(CARDID).asInt());
        cardSetter(bluePurple3, bluePlayerCards.path(PURPLE).path(2).path(CARDID).asInt());
        cardSetter(bluePurple4, bluePlayerCards.path(PURPLE).path(3).path(CARDID).asInt());
        cardSetter(bluePurple5, bluePlayerCards.path(PURPLE).path(4).path(CARDID).asInt());
        cardSetter(bluePurple6, bluePlayerCards.path(PURPLE).path(5).path(CARDID).asInt());

        cardSetter(blueYellow1, bluePlayerCards.path(YELLOW).path(0).path(CARDID).asInt());
        cardSetter(blueYellow2, bluePlayerCards.path(YELLOW).path(1).path(CARDID).asInt());
        cardSetter(blueYellow3, bluePlayerCards.path(YELLOW).path(2).path(CARDID).asInt());
        cardSetter(blueYellow4, bluePlayerCards.path(YELLOW).path(3).path(CARDID).asInt());
        cardSetter(blueYellow5, bluePlayerCards.path(YELLOW).path(4).path(CARDID).asInt());
        cardSetter(blueYellow6, bluePlayerCards.path(YELLOW).path(5).path(CARDID).asInt());

        blueTab.setDisable(false);

    }

    private void greenPlayerSetter() {

        JsonNode greenPlayer = rootMe;
        for (JsonNode singlePlayer: rootInstance.path(PL))
            if (beauty(singlePlayer.path(PLCOL)).equals("GREEN"))
                greenPlayer = singlePlayer;

        greenTile.setImage(new Image(new File("src/main/resources/javafx/playerTiles/tile" + beauty(greenPlayer.path(PLTILE).path("id")) + ".png").toURI().toString()));
        greenText.setText("[" + beauty(greenPlayer.path(PLCOL)) + "] " + beauty(greenPlayer.path(PLNAME)) + ": " + beauty(greenPlayer.path("res")));
        JsonNode greenPlayerCards = greenPlayer.path(CARDS);

        cardSetter(greenBlue1, greenPlayerCards.path(BLUE).path(0).path(CARDID).asInt());
        cardSetter(greenBlue2, greenPlayerCards.path(BLUE).path(1).path(CARDID).asInt());
        cardSetter(greenBlue3, greenPlayerCards.path(BLUE).path(2).path(CARDID).asInt());
        cardSetter(greenBlue4, greenPlayerCards.path(BLUE).path(3).path(CARDID).asInt());
        cardSetter(greenBlue5, greenPlayerCards.path(BLUE).path(4).path(CARDID).asInt());
        cardSetter(greenBlue6, greenPlayerCards.path(BLUE).path(5).path(CARDID).asInt());

        cardSetter(greenGreen1, greenPlayerCards.path(GREEN).path(0).path(CARDID).asInt());
        cardSetter(greenGreen2, greenPlayerCards.path(GREEN).path(1).path(CARDID).asInt());
        cardSetter(greenGreen3, greenPlayerCards.path(GREEN).path(2).path(CARDID).asInt());
        cardSetter(greenGreen4, greenPlayerCards.path(GREEN).path(3).path(CARDID).asInt());
        cardSetter(greenGreen5, greenPlayerCards.path(GREEN).path(4).path(CARDID).asInt());
        cardSetter(greenGreen6, greenPlayerCards.path(GREEN).path(5).path(CARDID).asInt());

        cardSetter(greenPurple1, greenPlayerCards.path(PURPLE).path(0).path(CARDID).asInt());
        cardSetter(greenPurple2, greenPlayerCards.path(PURPLE).path(1).path(CARDID).asInt());
        cardSetter(greenPurple3, greenPlayerCards.path(PURPLE).path(2).path(CARDID).asInt());
        cardSetter(greenPurple4, greenPlayerCards.path(PURPLE).path(3).path(CARDID).asInt());
        cardSetter(greenPurple5, greenPlayerCards.path(PURPLE).path(4).path(CARDID).asInt());
        cardSetter(greenPurple6, greenPlayerCards.path(PURPLE).path(5).path(CARDID).asInt());

        cardSetter(greenYellow1, greenPlayerCards.path(YELLOW).path(0).path(CARDID).asInt());
        cardSetter(greenYellow2, greenPlayerCards.path(YELLOW).path(1).path(CARDID).asInt());
        cardSetter(greenYellow3, greenPlayerCards.path(YELLOW).path(2).path(CARDID).asInt());
        cardSetter(greenYellow4, greenPlayerCards.path(YELLOW).path(3).path(CARDID).asInt());
        cardSetter(greenYellow5, greenPlayerCards.path(YELLOW).path(4).path(CARDID).asInt());
        cardSetter(greenYellow6, greenPlayerCards.path(YELLOW).path(5).path(CARDID).asInt());

        greenTab.setDisable(false);

    }

    private void redPlayerSetter() {

        JsonNode redPlayer = rootMe;
        for (JsonNode singlePlayer: rootInstance.path(PL))
            if (beauty(singlePlayer.path(PLCOL)).equals("RED"))
                redPlayer = singlePlayer;

        redTile.setImage(new Image(new File("src/main/resources/javafx/playerTiles/tile" + beauty(redPlayer.path(PLTILE).path("id")) + ".png").toURI().toString()));
        redText.setText("[" + beauty(redPlayer.path(PLCOL)) + "] " + beauty(redPlayer.path(PLNAME)) + ": " + beauty(redPlayer.path("res")));
        JsonNode redPlayerCards = redPlayer.path(CARDS);

        cardSetter(redBlue1, redPlayerCards.path(BLUE).path(0).path(CARDID).asInt());
        cardSetter(redBlue2, redPlayerCards.path(BLUE).path(1).path(CARDID).asInt());
        cardSetter(redBlue3, redPlayerCards.path(BLUE).path(2).path(CARDID).asInt());
        cardSetter(redBlue4, redPlayerCards.path(BLUE).path(3).path(CARDID).asInt());
        cardSetter(redBlue5, redPlayerCards.path(BLUE).path(4).path(CARDID).asInt());
        cardSetter(redBlue6, redPlayerCards.path(BLUE).path(5).path(CARDID).asInt());

        cardSetter(redGreen1, redPlayerCards.path(GREEN).path(0).path(CARDID).asInt());
        cardSetter(redGreen2, redPlayerCards.path(GREEN).path(1).path(CARDID).asInt());
        cardSetter(redGreen3, redPlayerCards.path(GREEN).path(2).path(CARDID).asInt());
        cardSetter(redGreen4, redPlayerCards.path(GREEN).path(3).path(CARDID).asInt());
        cardSetter(redGreen5, redPlayerCards.path(GREEN).path(4).path(CARDID).asInt());
        cardSetter(redGreen6, redPlayerCards.path(GREEN).path(5).path(CARDID).asInt());

        cardSetter(redPurple1, redPlayerCards.path(PURPLE).path(0).path(CARDID).asInt());
        cardSetter(redPurple2, redPlayerCards.path(PURPLE).path(1).path(CARDID).asInt());
        cardSetter(redPurple3, redPlayerCards.path(PURPLE).path(2).path(CARDID).asInt());
        cardSetter(redPurple4, redPlayerCards.path(PURPLE).path(3).path(CARDID).asInt());
        cardSetter(redPurple5, redPlayerCards.path(PURPLE).path(4).path(CARDID).asInt());
        cardSetter(redPurple6, redPlayerCards.path(PURPLE).path(5).path(CARDID).asInt());

        cardSetter(redYellow1, redPlayerCards.path(YELLOW).path(0).path(CARDID).asInt());
        cardSetter(redYellow2, redPlayerCards.path(YELLOW).path(1).path(CARDID).asInt());
        cardSetter(redYellow3, redPlayerCards.path(YELLOW).path(2).path(CARDID).asInt());
        cardSetter(redYellow4, redPlayerCards.path(YELLOW).path(3).path(CARDID).asInt());
        cardSetter(redYellow5, redPlayerCards.path(YELLOW).path(4).path(CARDID).asInt());
        cardSetter(redYellow6, redPlayerCards.path(YELLOW).path(5).path(CARDID).asInt());

        redTab.setDisable(false);

    }

    private void yellowPlayerSetter() {

        JsonNode yellowPlayer = rootMe;
        for (JsonNode singlePlayer: rootInstance.path(PL))
            if (beauty(singlePlayer.path(PLCOL)).equals("YELLOW"))
                yellowPlayer = singlePlayer;

        yellowTile.setImage(new Image(new File("src/main/resources/javafx/playerTiles/tile" + beauty(yellowPlayer.path(PLTILE).path("id")) + ".png").toURI().toString()));
        yellowText.setText("[" + beauty(yellowPlayer.path(PLCOL)) + "] " + beauty(yellowPlayer.path(PLNAME)) + ": " + beauty(yellowPlayer.path("res")));
        JsonNode yellowPlayerCards = yellowPlayer.path(CARDS);

        cardSetter(yellowBlue1, yellowPlayerCards.path(BLUE).path(0).path(CARDID).asInt());
        cardSetter(yellowBlue2, yellowPlayerCards.path(BLUE).path(1).path(CARDID).asInt());
        cardSetter(yellowBlue3, yellowPlayerCards.path(BLUE).path(2).path(CARDID).asInt());
        cardSetter(yellowBlue4, yellowPlayerCards.path(BLUE).path(3).path(CARDID).asInt());
        cardSetter(yellowBlue5, yellowPlayerCards.path(BLUE).path(4).path(CARDID).asInt());
        cardSetter(yellowBlue6, yellowPlayerCards.path(BLUE).path(5).path(CARDID).asInt());

        cardSetter(yellowGreen1, yellowPlayerCards.path(GREEN).path(0).path(CARDID).asInt());
        cardSetter(yellowGreen2, yellowPlayerCards.path(GREEN).path(1).path(CARDID).asInt());
        cardSetter(yellowGreen3, yellowPlayerCards.path(GREEN).path(2).path(CARDID).asInt());
        cardSetter(yellowGreen4, yellowPlayerCards.path(GREEN).path(3).path(CARDID).asInt());
        cardSetter(yellowGreen5, yellowPlayerCards.path(GREEN).path(4).path(CARDID).asInt());
        cardSetter(yellowGreen6, yellowPlayerCards.path(GREEN).path(5).path(CARDID).asInt());

        cardSetter(yellowPurple1, yellowPlayerCards.path(PURPLE).path(0).path(CARDID).asInt());
        cardSetter(yellowPurple2, yellowPlayerCards.path(PURPLE).path(1).path(CARDID).asInt());
        cardSetter(yellowPurple3, yellowPlayerCards.path(PURPLE).path(2).path(CARDID).asInt());
        cardSetter(yellowPurple4, yellowPlayerCards.path(PURPLE).path(3).path(CARDID).asInt());
        cardSetter(yellowPurple5, yellowPlayerCards.path(PURPLE).path(4).path(CARDID).asInt());
        cardSetter(yellowPurple6, yellowPlayerCards.path(PURPLE).path(5).path(CARDID).asInt());

        cardSetter(yellowYellow1, yellowPlayerCards.path(YELLOW).path(0).path(CARDID).asInt());
        cardSetter(yellowYellow2, yellowPlayerCards.path(YELLOW).path(1).path(CARDID).asInt());
        cardSetter(yellowYellow3, yellowPlayerCards.path(YELLOW).path(2).path(CARDID).asInt());
        cardSetter(yellowYellow4, yellowPlayerCards.path(YELLOW).path(3).path(CARDID).asInt());
        cardSetter(yellowYellow5, yellowPlayerCards.path(YELLOW).path(4).path(CARDID).asInt());
        cardSetter(yellowYellow6, yellowPlayerCards.path(YELLOW).path(5).path(CARDID).asInt());

        yellowTab.setDisable(false);

    }

    @Override
    public void update(Map<String, String> gameState) throws IOException {
        this.rootInstance = mapper.readTree(gameState.get("GameInstance"));
        this.rootBoard = mapper.readTree(gameState.get("GameBoard"));

        Map<Integer, String> orderedPlayers = new HashMap<>();
        Integer numOfPlayers = 0;

        for (JsonNode singlePlayer: rootInstance.path(PL)) {
            if (beauty(singlePlayer.path("playerID")).equals(myPlayerID))
                this.rootMe = singlePlayer;
            orderedPlayers.put(singlePlayer.path("playerOrder").asInt(), beauty(singlePlayer.path(PLCOL)));
            numOfPlayers++;

            if (beauty(singlePlayer.path(PLCOL)).equals(BLUE))
                bluePlayerSetter();
            if (beauty(singlePlayer.path(PLCOL)).equals(GREEN))
                greenPlayerSetter();
            if (beauty(singlePlayer.path(PLCOL)).equals("RED"))
                redPlayerSetter();
            if (beauty(singlePlayer.path(PLCOL)).equals(YELLOW))
                yellowPlayerSetter();
        }

        if (numOfPlayers.equals(2)) {
            board.setImage(new Image(new File("src/main/resources/javafx/boards/board_2.png").toURI().toString()));
            space21.setVisible(false);
            space22.setVisible(false);
            space21.setDisable(true);
            space22.setDisable(true);
        } else if (numOfPlayers.equals(3)) {
            board.setImage(new Image(new File("src/main/resources/javafx/boards/board_3.png").toURI().toString()));
            space21.setVisible(false);
            space22.setVisible(false);
            space21.setDisable(true);
            space22.setDisable(true);
        }

        header.setText("[" + beauty(rootMe.path(PLCOL)) + "] " + beauty(rootMe.path(PLNAME)) + "   AGE:[" + beauty(rootInstance.path("age")) + "]   TURN:[" + beauty(rootInstance.path("turn")) + "]");

        playerOrderColorSetter(order1, orderedPlayers.get(1));
        playerOrderColorSetter(order2, orderedPlayers.get(2));
        playerOrderColorSetter(order3, orderedPlayers.get(3));
        playerOrderColorSetter(order4, orderedPlayers.get(4));

        myBlack.setStroke(Paint.valueOf(beauty(rootMe.path(PLCOL))));
        myWhite.setStroke(Paint.valueOf(beauty(rootMe.path(PLCOL))));
        myOrange.setStroke(Paint.valueOf(beauty(rootMe.path(PLCOL))));
        myNeutral.setFill(Paint.valueOf(beauty(rootMe.path(PLCOL))));
        myBlack.setDisable(true);
        myBlack.setVisible(false);
        myWhite.setDisable(true);
        myWhite.setVisible(false);
        myOrange.setDisable(true);
        myOrange.setVisible(false);
        myNeutral.setDisable(true);
        myNeutral.setVisible(false);

        for (JsonNode singleMember: rootMe.path(FMS)) {
            if (beauty(singleMember.path("currentPositionID")).equals("0"))
                switch (beauty(singleMember.path(COLOR))) {
                    case BLACK:
                        myBlack.setDisable(false);
                        myBlack.setVisible(true);
                        break;
                    case WHITE:
                        myWhite.setDisable(false);
                        myWhite.setVisible(true);
                        break;
                    case ORANGE:
                        myOrange.setDisable(false);
                        myOrange.setVisible(true);
                        break;
                    case NEUTRAL:
                        myNeutral.setDisable(false);
                        myNeutral.setVisible(true);
                        break;
                    default:
                }
        }

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

        diceSetter(diceBlack, rootBoard.path(DICES).path(BLACK).asInt());
        diceSetter(diceWhite, rootBoard.path(DICES).path(WHITE).asInt());
        diceSetter(diceOrange, rootBoard.path(DICES).path(ORANGE).asInt());

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

        councilPalaceSetter();
        textQuery.setText("Waiting for player's movement ...");
    }

    @Override
    public void movementFail(Map<String, String> map) {
        textFail.setVisible(true);
    }

    @Override
    public Map<String, String> movementQuery() {

        textQuery.setText("It's your turn, move a Family Member into a Space.\nSelect a Family Member on the upper-right and then a space.");
        movementChoice = new HashMap<>();
        movementState.put("isMemberChoiceOn", true);
        while (movementState.get("isMemberChoiceOn"));
        movementState.put(ISSCON, true);
        while (movementState.get(ISSCON));
        movementChoice.put("servantsToPay", "0");
        resetFamilyMembers();
        textQuery.setText("Waiting for player's movement ...");
        textFail.setVisible(false);
        return movementChoice;
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