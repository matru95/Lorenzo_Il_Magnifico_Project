package it.polimi.ingsw.gc31.view.jfx;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.client.Client;
import it.polimi.ingsw.gc31.client.RMIClient;
import it.polimi.ingsw.gc31.client.SocketClient;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.view.GameViewCtrl;
import it.polimi.ingsw.gc31.view.cli.GameViewCLI;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class GameViewFXCtrl implements GameViewCtrl {

    private static final String TOWERS = "towers";
    private static final String TOWERSPACES = "towerSpaces";
    private static final String CARDID = "cardID";

    private final Client client;
    private final ObjectMapper mapper;
    private JsonNode rootInstance;
    private JsonNode rootBoard;

    public GameViewFXCtrl() throws InterruptedException, NotBoundException, IOException, ClassNotFoundException {

        this.mapper = new ObjectMapper();
        BufferedReader br;
        System.out.println("Choose between using \"SOCKET\" or \"RMI\":");
        String choice;

        do {

            br = new BufferedReader(new InputStreamReader(System.in));
            choice = br.readLine();

            /*System.out.println("Hello, pls enter your name:");
            br = new BufferedReader(new InputStreamReader(System.in));
            String myPlayerName = br.readLine();
            System.out.println("Now enter the ip address for the server to which connect (\"127.0.0.1\" for localhost):");
            br = new BufferedReader(new InputStreamReader(System.in));
            String serverIP = br.readLine();*/

            if (choice.equalsIgnoreCase("SOCKET")) {
                this.client = new SocketClient("127.0.0.1", "SOCKETFX", this);
                break;
            }

            if (choice.equalsIgnoreCase("RMI")) {
                this.client = new RMIClient("127.0.0.1", "RMIFX", this);
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
    private Circle order1;

    @FXML
    private Circle order2;

    @FXML
    private Circle order3;

    @FXML
    private Circle order4;

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
    void onClick(MouseEvent event) {

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

    private void spaceColorSetter(Shape spaceShape) {
        spaceShape.setVisible(true);
    }

    @Override
    public void update(Map<String, String> gameState) throws IOException {
        this.rootInstance = mapper.readTree(gameState.get("GameInstance"));
        this.rootBoard = mapper.readTree(gameState.get("GameBoard"));

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

        Map<Integer, String> orderedPlayers = new HashMap<>();
        for (JsonNode singlePlayer: rootInstance.path("players"))
            orderedPlayers.put(singlePlayer.path("playerOrder").asInt(), singlePlayer.path("playerColor").toString().replace("\"",""));

        playerOrderColorSetter(order1, orderedPlayers.get(1));
        playerOrderColorSetter(order2, orderedPlayers.get(2));
        playerOrderColorSetter(order3, orderedPlayers.get(3));
        playerOrderColorSetter(order4, orderedPlayers.get(4));

        spaceColorSetter(space1);
        spaceColorSetter(space2);
        spaceColorSetter(space3);
        spaceColorSetter(space4);
        spaceColorSetter(space5);
        spaceColorSetter(space6);
        spaceColorSetter(space7);
        spaceColorSetter(space8);
        spaceColorSetter(space9);
        spaceColorSetter(space10);
        spaceColorSetter(space11);
        spaceColorSetter(space12);
        spaceColorSetter(space13);
        spaceColorSetter(space14);
        spaceColorSetter(space15);
        spaceColorSetter(space16);
        spaceColorSetter(space17);
        spaceColorSetter(space18);
        spaceColorSetter(space19);
        spaceColorSetter(space20);
        spaceColorSetter(space21);
        spaceColorSetter(space22);
        spaceColorSetter(space23);

    }

    @Override
    public void movementFail(Map<String, String> gameState) {

    }

    @Override
    public Map<String, String> movementQuery() throws IOException {
        return null;
    }

    @Override
    public Map<String, String> parchmentQuery(Map<String, String> map) throws IOException {
        return null;
    }

    @Override
    public Map<String, String> faithQuery() throws IOException {
        return null;
    }

    @Override
    public Map<String, String> costQuery(Map<String, String> map) throws IOException {
        return null;
    }

    @Override
    public Map<String, String> exchangeQuery(Map<String, String> map) throws IOException {
        return null;
    }

    @Override
    public Map<String, String> freeCardQuery(Map<String, String> map) throws IOException {
        return null;
    }

    @Override
    public void setPlayerID(String playerID) {

    }
}




