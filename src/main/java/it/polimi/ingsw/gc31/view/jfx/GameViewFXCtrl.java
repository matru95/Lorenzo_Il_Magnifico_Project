package it.polimi.ingsw.gc31.view.jfx;

import java.io.*;
import java.rmi.NotBoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.client.RMIClient;
import it.polimi.ingsw.gc31.client.SocketClient;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.view.GameViewCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
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
    private static final String NEUTRALCOLOR = "#eddcc6";
    private static final String TILEBASEPATH = "src/main/resources/javafx/playerTiles/tile";
    private static final String MALUSES = "maluses";
    private static final String AGE1STR = "Excommunications:    [AGE1]=";
    private static final String AGE2STR = "    [AGE2]=";
    private static final String AGE3STR = "    [AGE3]=";
    private static final String WAITINGSTR = "Waiting for player's movement ...";
    private static final String FAITHTILES = "faithTiles";
    private static final String NUMOFPARCH = "numOfParchments";

    private StringBuilder sb;
    private String myPlayerID;
    private String myPlayerName;
    private final ObjectMapper mapper;
    private JsonNode rootInstance;
    private JsonNode rootBoard;
    private JsonNode rootMe;

    private Map<String, Boolean> queryState;
    private Map<String, String> choice;

    public GameViewFXCtrl() throws InterruptedException, NotBoundException, IOException, ClassNotFoundException {

        this.sb = new StringBuilder();
        this.mapper = new ObjectMapper();

        myPlayerNameQuery();
        connectionQuery(serverIPQuery());

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
        String myChoice;
        do {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            myChoice = br.readLine();

            if (myChoice.equalsIgnoreCase("SOCKET")) {
                this.myPlayerName = "SOCKETFX";
                new SocketClient(serverIP, myPlayerName, this);
                break;
            }

            if (myChoice.equalsIgnoreCase("RMI")) {
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
    private Text header;

    @FXML
    private Text textQuery;

    @FXML
    private Text textFail;

    @FXML
    private Circle councPal1;

    @FXML
    private Circle councPal2;

    @FXML
    private Circle councPal3;

    @FXML
    private Circle councPal4;

    @FXML
    private Circle councPal5;

    @FXML
    private Circle councPal6;

    @FXML
    private Circle councPal7;

    @FXML
    private Circle councPal8;

    @FXML
    private Circle councPal9;

    @FXML
    private Circle councPal10;

    @FXML
    private Circle parchment0;

    @FXML
    private Circle parchment1;

    @FXML
    private Circle parchment2;

    @FXML
    private Circle parchment3;

    @FXML
    private Circle parchment4;

    @FXML
    private TextField textChoice;

    @FXML
    private Button buttonChoice;

    @FXML
    private Button cancelMove;

    @FXML
    private Circle harv2;

    @FXML
    private Circle harv3;

    @FXML
    private Circle harv4;

    @FXML
    private Circle harv5;

    @FXML
    private Circle harv6;

    @FXML
    private Circle harv7;

    @FXML
    private Circle harv8;

    @FXML
    private Circle prod2;

    @FXML
    private Circle prod3;

    @FXML
    private Circle prod4;

    @FXML
    private Circle prod5;

    @FXML
    private Circle prod6;

    @FXML
    private Circle prod7;

    @FXML
    private Circle prod8;

    @FXML
    private Tab blueTab;

    @FXML
    private ImageView blueGreen1;

    @FXML
    private ImageView blueGreen2;

    @FXML
    private ImageView blueGreen3;

    @FXML
    private ImageView blueGreen4;

    @FXML
    private ImageView blueGreen5;

    @FXML
    private ImageView blueGreen6;

    @FXML
    private ImageView blueYellow1;

    @FXML
    private ImageView blueYellow2;

    @FXML
    private ImageView blueYellow3;

    @FXML
    private ImageView blueYellow4;

    @FXML
    private ImageView blueYellow5;

    @FXML
    private ImageView blueYellow6;

    @FXML
    private ImageView blueBlue1;

    @FXML
    private ImageView blueBlue2;

    @FXML
    private ImageView blueBlue3;

    @FXML
    private ImageView blueBlue4;

    @FXML
    private ImageView blueBlue5;

    @FXML
    private ImageView blueBlue6;

    @FXML
    private ImageView bluePurple1;

    @FXML
    private ImageView bluePurple2;

    @FXML
    private ImageView bluePurple3;

    @FXML
    private ImageView bluePurple4;

    @FXML
    private ImageView bluePurple5;

    @FXML
    private ImageView bluePurple6;

    @FXML
    private TextArea blueText;

    @FXML
    private ImageView blueTile;

    @FXML
    private Tab greenTab;

    @FXML
    private ImageView greenGreen1;

    @FXML
    private ImageView greenGreen2;

    @FXML
    private ImageView greenGreen3;

    @FXML
    private ImageView greenGreen4;

    @FXML
    private ImageView greenGreen5;

    @FXML
    private ImageView greenGreen6;

    @FXML
    private ImageView greenYellow1;

    @FXML
    private ImageView greenYellow2;

    @FXML
    private ImageView greenYellow3;

    @FXML
    private ImageView greenYellow4;

    @FXML
    private ImageView greenYellow5;

    @FXML
    private ImageView greenYellow6;

    @FXML
    private ImageView greenBlue1;

    @FXML
    private ImageView greenBlue2;

    @FXML
    private ImageView greenBlue3;

    @FXML
    private ImageView greenBlue4;

    @FXML
    private ImageView greenBlue5;

    @FXML
    private ImageView greenBlue6;

    @FXML
    private ImageView greenPurple1;

    @FXML
    private ImageView greenPurple2;

    @FXML
    private ImageView greenPurple3;

    @FXML
    private ImageView greenPurple4;

    @FXML
    private ImageView greenPurple5;

    @FXML
    private ImageView greenPurple6;

    @FXML
    private TextArea greenText;

    @FXML
    private ImageView greenTile;

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
    private ImageView redBlue1;

    @FXML
    private ImageView redBlue2;

    @FXML
    private ImageView redBlue3;

    @FXML
    private ImageView redBlue4;

    @FXML
    private ImageView redBlue5;

    @FXML
    private ImageView redBlue6;

    @FXML
    private ImageView redPurple1;

    @FXML
    private ImageView redPurple2;

    @FXML
    private ImageView redPurple3;

    @FXML
    private ImageView redPurple4;

    @FXML
    private ImageView redPurple5;

    @FXML
    private ImageView redPurple6;

    @FXML
    private TextArea redText;

    @FXML
    private ImageView redTile;

    @FXML
    private Tab yellowTab;

    @FXML
    private ImageView yellowGreen1;

    @FXML
    private ImageView yellowGreen2;

    @FXML
    private ImageView yellowGreen3;

    @FXML
    private ImageView yellowGreen4;

    @FXML
    private ImageView yellowGreen5;

    @FXML
    private ImageView yellowGreen6;

    @FXML
    private ImageView yellowYellow1;

    @FXML
    private ImageView yellowYellow2;

    @FXML
    private ImageView yellowYellow3;

    @FXML
    private ImageView yellowYellow4;

    @FXML
    private ImageView yellowYellow5;

    @FXML
    private ImageView yellowYellow6;

    @FXML
    private ImageView yellowBlue1;

    @FXML
    private ImageView yellowBlue2;

    @FXML
    private ImageView yellowBlue3;

    @FXML
    private ImageView yellowBlue4;

    @FXML
    private ImageView yellowBlue5;

    @FXML
    private ImageView yellowBlue6;

    @FXML
    private ImageView yellowPurple1;

    @FXML
    private ImageView yellowPurple2;

    @FXML
    private ImageView yellowPurple3;

    @FXML
    private ImageView yellowPurple4;

    @FXML
    private ImageView yellowPurple5;

    @FXML
    private ImageView yellowPurple6;

    @FXML
    private TextArea yellowText;

    @FXML
    private ImageView yellowTile;


    private void checkExchange() {

        if (queryState.get("isExchangeChoiceOn")) {
            disableChoice();
            choice.put("choice", textChoice.getText());
            queryState.put("isExchangeChoiceOn", false);
        }
    }

    private void checkServants() {

        if (queryState.get("isServantsQueryOn")) {
            if (isInteger(textChoice.getText()) && Integer.parseInt(textChoice.getText()) >=0
                    && Integer.parseInt(textChoice.getText()) <= Integer.parseInt(choice.get("myServants"))) {
                disableChoice();
                choice.put("servantsToPay", textChoice.getText());
                queryState.put("isServantsQueryOn", false);
            } else
                textQuery.setText("You must insert a valid number between 0 and " + choice.get("myServants") + ":");
        }
    }

    private void checkFreeCard () {

        if (queryState.get("isFreeCardChoiceOn") && isInteger(textChoice.getText())) {
            for(Integer i = 0; i < choice.size() - 1; i++)
                if (choice.get("card" + i).equals(textChoice.getText())) {
                    disableChoice();
                    choice.put(CARDID, textChoice.getText());
                    queryState.put("isFreeCardChoiceOn", false);
                }
        }
    }

    private void checkFaith() {

        if (queryState.get("isFaithQueryOn") && textChoice.getText().equalsIgnoreCase("YES") || textChoice.getText().equalsIgnoreCase("NO")) {
            disableChoice();
            choice.put("applyExcommunication", textChoice.getText());
            queryState.put("isFaithQueryOn", false);
        }
    }

    private void checkCost() {
        if (queryState.get("isCostQueryOn") && isInteger(textChoice.getText()) && (textChoice.getText().equals("1") || textChoice.getText().equals("2"))) {
            disableChoice();
            choice.put("cardCostChoice", textChoice.getText());
            queryState.put("isCostQueryOn", false);
        }
    }

    @FXML
    void onButtonClick() {

        checkExchange();
        checkServants();
        checkFreeCard();
        checkFaith();
        checkCost();
    }

    @FXML
    void onClickParchment0() {

        for (Integer i = 1; i <= Integer.parseInt(choice.get(NUMOFPARCH)); i++) {
            if (this.queryState.get("isParchment" + i + "On")) {
                choice.put(i.toString(), "0");
                this.queryState.put("isParchment" + i + "On", false);
                this.queryState.put("isOpenChoice0", false);
            }
        }

    }

    @FXML
    void onClickParchment1() {

        for (Integer i = 1; i <= Integer.parseInt(choice.get(NUMOFPARCH)); i++) {
            if (this.queryState.get("isParchment" + i + "On")) {
                choice.put(i.toString(), "1");
                this.queryState.put("isParchment" + i + "On", false);
                this.queryState.put("isOpenChoice1", false);
            }
        }
    }

    @FXML
    void onClickParchment2() {

        for (Integer i = 1; i <= Integer.parseInt(choice.get(NUMOFPARCH)); i++) {
            if (this.queryState.get("isParchment" + i + "On")) {
                choice.put(i.toString(), "2");
                this.queryState.put("isParchment" + i + "On", false);
                this.queryState.put("isOpenChoice2", false);
            }
        }
    }

    @FXML
    void onClickParchment3() {

        for (Integer i = 1; i <= Integer.parseInt(choice.get(NUMOFPARCH)); i++) {
            if (this.queryState.get("isParchment" + i + "On")) {
                choice.put(i.toString(), "3");
                this.queryState.put("isParchment" + i + "On", false);
                this.queryState.put("isOpenChoice3", false);
            }
        }
    }

    @FXML
    void onClickParchment4() {

        for (Integer i = 1; i <= Integer.parseInt(choice.get(NUMOFPARCH)); i++) {
            if (this.queryState.get("isParchment" + i + "On")) {
                choice.put(i.toString(), "4");
                this.queryState.put("isParchment" + i + "On", false);
                this.queryState.put("isOpenChoice4", false);
            }
        }
    }

    @FXML
    void onClickMemberBlack() {

        if (this.queryState.get("isMemberChoiceOn")) {
            choice.put(DICECOLOR, "BLACK");
            myBlack.setStrokeType(StrokeType.valueOf(OUTSIDE));
            disableFamilyMembers();
            this.queryState.put("isMemberChoiceOn", false);
        }
    }

    @FXML
    void onClickMemberWhite() {

        if (this.queryState.get("isMemberChoiceOn")) {
            choice.put(DICECOLOR, "WHITE");
            myWhite.setStrokeType(StrokeType.valueOf(OUTSIDE));
            disableFamilyMembers();
            this.queryState.put("isMemberChoiceOn", false);
        }
    }

    @FXML
    void onClickMemberOrange() {

        if (this.queryState.get("isMemberChoiceOn")) {
            choice.put(DICECOLOR, "ORANGE");
            myOrange.setStrokeType(StrokeType.valueOf(OUTSIDE));
            disableFamilyMembers();
            this.queryState.put("isMemberChoiceOn", false);
        }
    }

    @FXML
    void onClickMemberNeutral() {

        if (this.queryState.get("isMemberChoiceOn")) {
            choice.put(DICECOLOR, "NEUTRAL");
            myNeutral.setStrokeType(StrokeType.valueOf(OUTSIDE));
            disableFamilyMembers();
            this.queryState.put("isMemberChoiceOn", false);
        }
    }

    @FXML
    void onClickCancelMove() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "0");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace1() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "1");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace2() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "2");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace3() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "3");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace4() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "4");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace5() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "5");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace6() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "6");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace7() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "7");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace8() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "8");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace9() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "9");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace10() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "10");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace11() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "11");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace12() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "12");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace13() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "13");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace14() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "14");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace15() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "15");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace16() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "16");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace17() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "17");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace18() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "18");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace19() {

        if (this.queryState.get(ISSCON)) {
           choice.put(POSITIONID, "19");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace20() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "20");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace21() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "21");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace22() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "22");
            this.queryState.put(ISSCON, false);
        }
    }

    @FXML
    void onClickSpace23() {

        if (this.queryState.get(ISSCON)) {
            choice.put(POSITIONID, "23");
            this.queryState.put(ISSCON, false);
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer integer = Integer.parseInt(s);
        } catch(NumberFormatException|NullPointerException e) {
            return false;
        }
        return true;
    }

    private void hideMultipleActionSpaces() {
        prod2.setVisible(false);
        prod3.setVisible(false);
        prod4.setVisible(false);
        prod5.setVisible(false);
        prod6.setVisible(false);
        prod7.setVisible(false);
        prod8.setVisible(false);
        harv2.setVisible(false);
        harv3.setVisible(false);
        harv4.setVisible(false);
        harv5.setVisible(false);
        harv6.setVisible(false);
        harv7.setVisible(false);
        harv8.setVisible(false);
    }

    private void showMultipleActionSpaces() {
        prod2.setVisible(true);
        prod3.setVisible(true);
        prod4.setVisible(true);
        prod5.setVisible(true);
        prod6.setVisible(true);
        prod7.setVisible(true);
        prod8.setVisible(true);
        harv2.setVisible(true);
        harv3.setVisible(true);
        harv4.setVisible(true);
        harv5.setVisible(true);
        harv6.setVisible(true);
        harv7.setVisible(true);
        harv8.setVisible(true);
    }

    private void disableChoice() {
        textChoice.setVisible(false);
        textChoice.setDisable(true);
        buttonChoice.setVisible(false);
        buttonChoice.setDisable(true);
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

    private void enableParchments() {

        if (queryState.get("isOpenChoice0").equals(true)) {
            parchment0.setVisible(true);
            parchment0.setDisable(false);
        }
        if (queryState.get("isOpenChoice1").equals(true)) {
            parchment1.setVisible(true);
            parchment1.setDisable(false);
        }
        if (queryState.get("isOpenChoice2").equals(true)) {
            parchment2.setVisible(true);
            parchment2.setDisable(false);
        }
        if (queryState.get("isOpenChoice3").equals(true)) {
            parchment3.setVisible(true);
            parchment3.setDisable(false);
        }
        if (queryState.get("isOpenChoice4").equals(true)) {
            parchment4.setVisible(true);
            parchment4.setDisable(false);
        }

    }

    private void disableParchments() {

        parchment0.setVisible(false);
        parchment0.setDisable(true);
        parchment1.setVisible(false);
        parchment1.setDisable(true);
        parchment2.setVisible(false);
        parchment2.setDisable(true);
        parchment3.setVisible(false);
        parchment3.setDisable(true);
        parchment4.setVisible(false);
        parchment4.setDisable(true);
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
                spaceShape.setStroke(Paint.valueOf(NEUTRALCOLOR));
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
            spaceShape.setFill(Paint.valueOf("#ffffff00"));
        }
    }

    private void actionSpaceSetter(Circle spaceShape, JsonNode node) {

        if (node.path(FMS).has(0)) {

            if (beauty(node.path(FMS).path(0).path(COLOR)).equals(NEUTRAL)) {
                spaceShape.setFill(Paint.valueOf(beauty(node.path(FMS).path(0).path(PLCOL))));
                spaceShape.setStroke(Paint.valueOf(NEUTRALCOLOR));
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
            spaceShape.setFill(Paint.valueOf("#ffffff00"));
        }
    }

    private void councilPalaceSetter1() {

        JsonNode councilPalaceNode = rootBoard.path("boardSpaces").path("23").path(FMS);
        if (councilPalaceNode.has(0))
            multipleSpaceMemberSetter(councPal1, councilPalaceNode.path(0));
        else
            councPal1.setVisible(false);

        if (councilPalaceNode.has(1))
            multipleSpaceMemberSetter(councPal2, councilPalaceNode.path(1));
        else
            councPal2.setVisible(false);

        if (councilPalaceNode.has(2))
            multipleSpaceMemberSetter(councPal3, councilPalaceNode.path(2));
        else
            councPal3.setVisible(false);

        if (councilPalaceNode.has(3))
            multipleSpaceMemberSetter(councPal4, councilPalaceNode.path(3));
        else
            councPal4.setVisible(false);

        if (councilPalaceNode.has(4))
            multipleSpaceMemberSetter(councPal5, councilPalaceNode.path(4));
        else
            councPal5.setVisible(false);

    }

    private void councilPalaceSetter2() {

        JsonNode councilPalaceNode = rootBoard.path("boardSpaces").path("23").path(FMS);

        if (councilPalaceNode.has(5))
            multipleSpaceMemberSetter(councPal6, councilPalaceNode.path(5));
        else
            councPal6.setVisible(false);

        if (councilPalaceNode.has(6))
            multipleSpaceMemberSetter(councPal7, councilPalaceNode.path(6));
        else
            councPal7.setVisible(false);

        if (councilPalaceNode.has(7))
            multipleSpaceMemberSetter(councPal8, councilPalaceNode.path(7));
        else
            councPal8.setVisible(false);

        if (councilPalaceNode.has(8))
            multipleSpaceMemberSetter(councPal9, councilPalaceNode.path(8));
        else
            councPal9.setVisible(false);

        if (councilPalaceNode.has(9))
            multipleSpaceMemberSetter(councPal10, councilPalaceNode.path(9));
        else
            councPal10.setVisible(false);

    }

    private void councilPalaceSetter() {
        councilPalaceSetter1();
        councilPalaceSetter2();
    }

    private void productionMultipleSetter() {

        JsonNode node = rootBoard.path("boardSpaces").path("17").path(FMS);

        if (node.has(1))
            multipleSpaceMemberSetter(prod2, node.path(1));
        else
            prod2.setVisible(false);

        if (node.has(2))
            multipleSpaceMemberSetter(prod3, node.path(2));
        else
            prod3.setVisible(false);

        if (node.has(3))
            multipleSpaceMemberSetter(prod4, node.path(3));
        else
            prod4.setVisible(false);

        if (node.has(4))
            multipleSpaceMemberSetter(prod5, node.path(4));
        else
            prod5.setVisible(false);

        if (node.has(5))
            multipleSpaceMemberSetter(prod6, node.path(5));
        else
            prod6.setVisible(false);

        if (node.has(6))
            multipleSpaceMemberSetter(prod7, node.path(6));
        else
            prod7.setVisible(false);

        if (node.has(7))
            multipleSpaceMemberSetter(prod8, node.path(7));
        else
            prod8.setVisible(false);

    }

    private void harvestMultipleSetter() {

        JsonNode node = rootBoard.path("boardSpaces").path("18").path(FMS);

        if (node.has(1))
            multipleSpaceMemberSetter(harv2, node.path(1));
        else
            harv2.setVisible(false);

        if (node.has(2))
            multipleSpaceMemberSetter(harv3, node.path(2));
        else
            harv3.setVisible(false);

        if (node.has(3))
            multipleSpaceMemberSetter(harv4, node.path(3));
        else
            harv4.setVisible(false);

        if (node.has(4))
            multipleSpaceMemberSetter(harv5, node.path(4));
        else
            harv5.setVisible(false);

        if (node.has(5))
            multipleSpaceMemberSetter(harv6, node.path(5));
        else
            harv6.setVisible(false);

        if (node.has(6))
            multipleSpaceMemberSetter(harv7, node.path(6));
        else
            harv7.setVisible(false);

        if (node.has(7))
            multipleSpaceMemberSetter(harv8, node.path(7));
        else
            harv8.setVisible(false);

    }

    private void multipleSpaceMemberSetter(Circle memberShape, JsonNode node) {

        if (beauty(node.path(COLOR)).equals(NEUTRAL)) {
            memberShape.setFill(Paint.valueOf(beauty(node.path(PLCOL))));
            memberShape.setStroke(Paint.valueOf(NEUTRALCOLOR));
        } else {
            memberShape.setFill(Paint.valueOf(beauty(node.path(COLOR))));
            memberShape.setStroke(Paint.valueOf(beauty(node.path(PLCOL))));
        }
        memberShape.setVisible(true);
    }

    private void bluePlayerSetter() {

        JsonNode bluePlayer = rootMe;
        for (JsonNode singlePlayer: rootInstance.path(PL))
            if (beauty(singlePlayer.path(PLCOL)).equals("BLUE"))
                bluePlayer = singlePlayer;
        blueTile.setImage(new Image(new File(TILEBASEPATH + beauty(bluePlayer.path(PLTILE).path("id")) + ".png").toURI().toString()));

        blueText.setText("[" + beauty(bluePlayer.path(PLCOL)) + "] " + beauty(bluePlayer.path(PLNAME)) + ": " + beauty(bluePlayer.path("res"))
        + "\n" + AGE1STR + beauty(bluePlayer.path(MALUSES).path(0)) + AGE2STR + beauty(bluePlayer.path(MALUSES).path(1)) + AGE3STR + beauty(bluePlayer.path(MALUSES).path(2)));

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

        greenTile.setImage(new Image(new File(TILEBASEPATH + beauty(greenPlayer.path(PLTILE).path("id")) + ".png").toURI().toString()));
        greenText.setText("[" + beauty(greenPlayer.path(PLCOL)) + "] " + beauty(greenPlayer.path(PLNAME)) + ": " + beauty(greenPlayer.path("res"))
                + "\n" + AGE1STR + beauty(greenPlayer.path(MALUSES).path(0)) + AGE2STR + beauty(greenPlayer.path(MALUSES).path(1)) + AGE3STR + beauty(greenPlayer.path(MALUSES).path(2)));
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

        redTile.setImage(new Image(new File(TILEBASEPATH + beauty(redPlayer.path(PLTILE).path("id")) + ".png").toURI().toString()));
        redText.setText("[" + beauty(redPlayer.path(PLCOL)) + "] " + beauty(redPlayer.path(PLNAME)) + ": " + beauty(redPlayer.path("res"))
                + "\n" + AGE1STR + beauty(redPlayer.path(MALUSES).path(0)) + AGE2STR + beauty(redPlayer.path(MALUSES).path(1)) + AGE3STR + beauty(redPlayer.path(MALUSES).path(2)));
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

        yellowTile.setImage(new Image(new File(TILEBASEPATH + beauty(yellowPlayer.path(PLTILE).path("id")) + ".png").toURI().toString()));
        yellowText.setText("[" + beauty(yellowPlayer.path(PLCOL)) + "] " + beauty(yellowPlayer.path(PLNAME)) + ": " + beauty(yellowPlayer.path("res"))
                + "\n" + AGE1STR + beauty(yellowPlayer.path(MALUSES).path(0)) + AGE2STR + beauty(yellowPlayer.path(MALUSES).path(1)) + AGE3STR + beauty(yellowPlayer.path(MALUSES).path(2)));
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

    private void initFamilyMembers() {

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
    }

    @Override
    public void update(Map<String, String> gameState) throws IOException {
        this.rootInstance = mapper.readTree(gameState.get("GameInstance"));
        this.rootBoard = mapper.readTree(gameState.get("GameBoard"));

        cancelMove.setDisable(true);
        cancelMove.setVisible(false);
        queryState = new HashMap<>();
        queryState.put("isMemberChoiceOn", false);
        queryState.put(ISSCON, false);
        queryState.put("isExchangeChoiceOn", false);
        queryState.put("isServantsQueryOn", false);
        queryState.put("isFreeCardChoiceOn", false);
        queryState.put("isFaithQueryOn", false);
        queryState.put("isCostQueryOn", false);
        queryState.put("isParchment1On", false);
        queryState.put("isParchment2On", false);
        queryState.put("isParchment3On", false);

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
            hideMultipleActionSpaces();
            space21.setVisible(false);
            space22.setVisible(false);
            space21.setDisable(true);
            space22.setDisable(true);
        } else if (numOfPlayers.equals(3)) {
            board.setImage(new Image(new File("src/main/resources/javafx/boards/board_3.png").toURI().toString()));
            showMultipleActionSpaces();
            space21.setVisible(false);
            space22.setVisible(false);
            space21.setDisable(true);
            space22.setDisable(true);
        } else if (numOfPlayers.equals(4)) {
            showMultipleActionSpaces();
            space21.setVisible(true);
            space22.setVisible(true);
            space21.setDisable(false);
            space22.setDisable(false);
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

        initFamilyMembers();

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

        faithTileSetter(faithTile1, rootBoard.path(FAITHTILES).path("1").path("id").asInt());
        faithTileSetter(faithTile2, rootBoard.path(FAITHTILES).path("2").path("id").asInt());
        faithTileSetter(faithTile3, rootBoard.path(FAITHTILES).path("3").path("id").asInt());

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
        System.out.println(rootInstance.path(PL));
        councilPalaceSetter();
        productionMultipleSetter();
        harvestMultipleSetter();
        textQuery.setText(WAITINGSTR);
    }

    @Override
    public void movementFail(Map<String, String> map) {
        textFail.setVisible(true);
    }

    @Override
    public Map<String, String> movementQuery() {

        queryState = new HashMap<>();
        queryState.put("isMemberChoiceOn", false);
        queryState.put(ISSCON, false);
        choice = new HashMap<>();

        textQuery.setText("It's your turn, do a movement: select a Family Member on the upper-right and then a space.");
        queryState.put("isMemberChoiceOn", true);
        while (queryState.get("isMemberChoiceOn"));
        cancelMove.setDisable(false);
        cancelMove.setVisible(true);
        queryState.put(ISSCON, true);
        while (queryState.get(ISSCON));
        cancelMove.setDisable(true);
        cancelMove.setVisible(false);
        choice.put("servantsToPay", "0");
        resetFamilyMembers();
        textQuery.setText(WAITINGSTR);
        textFail.setVisible(false);
        return choice;
    }

    @Override
    public Map<String, String> servantsQuery(Map<String, String> map) {

        choice = new HashMap<>();
        queryState = new HashMap<>();
        queryState.put("isExchangeChoiceOn", false);
        queryState.put("isServantsQueryOn", false);
        queryState.put("isFreeCardChoiceOn", false);
        queryState.put("isFaithQueryOn", false);
        queryState.put("isCostQueryOn", false);
        choice.put("cardValue", map.get("cardValue"));
        choice.put("myServants", map.get("myServants"));
        textQuery.setText("Enter the number of servants you'd like to add number between 0 and " + choice.get("myServants") + ":");
        queryState.put("isServantsQueryOn", true);
        textChoice.setVisible(true);
        textChoice.setDisable(false);
        buttonChoice.setVisible(true);
        buttonChoice.setDisable(false);
        while (queryState.get("isServantsQueryOn"));
        textQuery.setText(WAITINGSTR);
        choice.put("positionType", map.get("positionType"));
        choice.put("familyMemberValue", map.get("familyMemberValue"));
        choice.remove("myServants");
        return choice;
    }

    @Override
    public Map<String, String> parchmentQuery(Map<String, String> map) {

        choice = new HashMap<>();
        queryState = new HashMap<>();

        choice.put(NUMOFPARCH, map.get("parchments"));
        queryState.put("isOpenChoice0", true);
        queryState.put("isOpenChoice1", true);
        queryState.put("isOpenChoice2", true);
        queryState.put("isOpenChoice3", true);
        queryState.put("isOpenChoice4", true);

        for (Integer i = 1; i <= Integer.parseInt(choice.get(NUMOFPARCH)); i++)
            queryState.put("isParchment" + i + "On", false);
        if (Integer.parseInt(choice.get(NUMOFPARCH)) > 1)
            textQuery.setText("Now you'll have to choose " + choice.get(NUMOFPARCH) + " different parchments.");
        for (Integer i = 1; i <= Integer.parseInt(choice.get(NUMOFPARCH)); i++) {
            queryState.put("isParchment" + i + "On", true);
            textQuery.setText("Choose parchment bonus #" + i +", between the ones available, highlighted in green");
            enableParchments();
            while (queryState.get("isParchment" + i + "On"));
            disableParchments();
        }

        textQuery.setText(WAITINGSTR);
        choice.remove(NUMOFPARCH);
        return choice;
    }

    @Override
    public Map<String, String> faithQuery() throws IOException {

        choice = new HashMap<>();
        queryState = new HashMap<>();
        queryState.put("isExchangeChoiceOn", false);
        queryState.put("isServantsQueryOn", false);
        queryState.put("isFreeCardChoiceOn", false);
        queryState.put("isFaithQueryOn", false);
        queryState.put("isCostQueryOn", false);

        for (JsonNode singleFaithTile: rootBoard.path("faithTiles"))
            if (beauty(singleFaithTile.path("age")).equals(beauty(rootInstance.path("age")))) {
                textQuery.setText("\nChoose if you wanna take this excommunication for this age (YES/NO):\n"
                        + beauty(singleFaithTile.path("effect")));
            }
        queryState.put("isFaithQueryOn", true);
        textChoice.setVisible(true);
        textChoice.setDisable(false);
        buttonChoice.setVisible(true);
        buttonChoice.setDisable(false);
        while (queryState.get("isFaithQueryOn"));
        textQuery.setText(WAITINGSTR);
        return choice;
    }

    @Override
    public Map<String, String> costQuery(Map<String, String> map) throws IOException {

        choice = new HashMap<>();
        queryState = new HashMap<>();
        queryState.put("isExchangeChoiceOn", false);
        queryState.put("isServantsQueryOn", false);
        queryState.put("isFreeCardChoiceOn", false);
        queryState.put("isFaithQueryOn", false);
        queryState.put("isCostQueryOn", false);

        for (JsonNode singlePurpleCard: rootBoard.path(CARDS).path(PURPLE))
            if (beauty(singlePurpleCard.path(CARDID)).equals(map.get(CARDID)))
                textQuery.setText("Choose which cost to pay between those (1, 2):\n" + "{("
                + beauty(singlePurpleCard.path("cost").path(0)) + ") OR (" + beauty(singlePurpleCard.path("cost").path(1)) + ")}");

        queryState.put("isCostQueryOn", true);
        textChoice.setVisible(true);
        textChoice.setDisable(false);
        buttonChoice.setVisible(true);
        buttonChoice.setDisable(false);
        while (queryState.get("isCostQueryOn"));

        textQuery.setText(WAITINGSTR);
        choice.put(CARDID, map.get(CARDID));
        return choice;
    }

    @Override
    public Map<String, String> exchangeQuery(Map<String, String> map) throws IOException {

        choice = new HashMap<>();
        queryState = new HashMap<>();
        queryState.put("isExchangeChoiceOn", false);
        queryState.put("isServantsQueryOn", false);
        queryState.put("isFreeCardChoiceOn", false);
        queryState.put("isFaithQueryOn", false);
        queryState.put("isCostQueryOn", false);
        Integer exchangesNumber = map.size() - 2;
        String str = "(0)";

        switch (exchangesNumber) {
            case 2:
                textQuery.setText("Choose whether or not activate the following exchange effects for the card #"
                        + map.get(CARDID) + " \"" + "cardName" + "\":\n" + "Type 0 to avoid activation of the exchanges;\n"
                        + "Type 1 to activate the first exchange effect;\n" + "Type 2 to activate the second exchange effect.");
                str = "(0, 1, 2)";
                break;
            case 1:
                textQuery.setText("Choose whether or not activate the following exchange effect for the card #"
                        + map.get(CARDID) + " \"" + map.get("cardName") + "\":\n"
                        + "Type 0 to avoid activation of the exchange;\n" + "Type 1 to activate the exchange effect.");
                str = "(0, 1)";
                break;
            default:
        }

        do {
            queryState.put("isExchangeChoiceOn", true);
            textChoice.setVisible(true);
            textChoice.setDisable(false);
            buttonChoice.setVisible(true);
            buttonChoice.setDisable(false);
            while(queryState.get("isExchangeChoiceOn"));
            if ((exchangesNumber.equals(1) && (choice.get("choice").equals("0") || choice.get("choice").equals("1")))
                    || (exchangesNumber.equals(2) && (choice.get("choice").equals("0") || choice.get("choice").equals("1") || choice.get("choice").equals("2"))))
                break;
            textQuery.setText("You must insert a valid number among these: " + str);
        } while (true);
        textQuery.setText(WAITINGSTR);
        choice.put(CARDID, map.get(CARDID));
        return choice;
    }

    @Override
    public Map<String, String> freeCardQuery(Map<String, String> map) throws IOException {

        choice = new HashMap<>();
        queryState = new HashMap<>();
        queryState.put("isExchangeChoiceOn", false);
        queryState.put("isServantsQueryOn", false);
        queryState.put("isFreeCardChoiceOn", false);
        queryState.put("isFaithQueryOn", false);
        queryState.put("isCostQueryOn", false);

        StringBuilder str = new StringBuilder();
        Boolean isFirstLoop = true;
        Integer i = 0;
        for (Map.Entry<String, String> entry: map.entrySet()) {
            if (isFirstLoop) str.append(entry.getValue());
            else str.append(", ").append(entry.getValue());
            isFirstLoop = false;
            choice.put("card" + i, entry.getValue());
            i++;
        }

        textQuery.setText("You've to choose whether or not picking a free card due to the BLUE CARD's instantEffect.\n"
                + "Here there's a list of possible cards you can pick: " + str);
        queryState.put("isFreeCardChoiceOn", true);
        textChoice.setVisible(true);
        textChoice.setDisable(false);
        buttonChoice.setVisible(true);
        buttonChoice.setDisable(false);
        while (queryState.get("isFreeCardChoiceOn"));
        textQuery.setText(WAITINGSTR);
        Integer j = 0;
        for (Map.Entry<String, String> entry: map.entrySet()) {
            choice.remove("card" + j);
            j++;
        }
        return choice;
    }

    @Override
    public void endGameQuery(Map<String, String> map) throws IOException {

        sb.append("Here is the leaderboard for this game:\n");
        JsonNode sortedPlayers = mapper.readTree(map.get("players"));
        Integer i = 1;
        for (JsonNode singlePlayer: sortedPlayers) {
            sb.append(i).append(") ").append(singlePlayer.path("playerName")).append("    ")
                    .append(singlePlayer.path("res").path("VICTORYPOINTS")).append(" VictoryPoints\n");
            i++;
        }
        textQuery.setText(sb.toString());
        printStringBuilder();
    }

    @Override
    public void timeoutAlert() {

        textQuery.setText("ALERT: You have exceeded the time limit, you'll skip this movement");
    }

    @Override
    public void setPlayerID(String playerID) {
        this.myPlayerID = playerID;
    }

    class ValueComparator implements Comparator<String> {
        Map<String, String> base;

        public ValueComparator(Map<String, String> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (Integer.parseInt(base.get(a)) >= Integer.parseInt(base.get(b))) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    /**
     * Print the actual String Builder and then reinitialize it, throwing off the old one
     */
    private void printStringBuilder() {
        System.out.println(this.sb);
        this.sb = new StringBuilder();
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