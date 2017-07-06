package it.polimi.ingsw.gc31.view.jfx;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.Map;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.client.Client;
import it.polimi.ingsw.gc31.client.RMIClient;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.view.GameViewCtrl;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GameViewFXCtrl implements GameViewCtrl {

    private final Client client;
    private final ObjectMapper mapper;
    private JsonNode rootInstance;
    private JsonNode rootBoard;

    public GameViewFXCtrl() throws InterruptedException, NotBoundException, NoResourceMatch, IOException {

        this.mapper = new ObjectMapper();
        this.client = new RMIClient("127.0.0.1", "LORENZO", this);
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView towers;

    @FXML
    private ImageView space_1;

    @FXML
    private ImageView space_2;

    @FXML
    private ImageView space_3;

    @FXML
    private ImageView space_4;

    @FXML
    private ImageView space_5;

    @FXML
    private ImageView space_6;

    @FXML
    private ImageView space_7;

    @FXML
    private ImageView space_8;

    @FXML
    private ImageView space_9;

    @FXML
    private ImageView space_10;

    @FXML
    private ImageView space_11;

    @FXML
    private ImageView space_12;

    @FXML
    private ImageView space_13;

    @FXML
    private ImageView space_14;

    @FXML
    private ImageView space_15;

    @FXML
    private ImageView space_16;

    @FXML
    private ImageView green_0;

    @FXML
    private ImageView green_1;

    @FXML
    private ImageView green_2;

    @FXML
    private ImageView green_3;

    @FXML
    private ImageView blue_0;

    @FXML
    private ImageView blue_1;

    @FXML
    private ImageView blue_2;

    @FXML
    private ImageView blue_3;

    @FXML
    private ImageView yellow_0;

    @FXML
    private ImageView yellow_1;

    @FXML
    private ImageView yellow_2;

    @FXML
    private ImageView yellow_3;

    @FXML
    private ImageView purple_0;

    @FXML
    private ImageView purple_1;

    @FXML
    private ImageView purple_2;

    @FXML
    private ImageView purple_3;

    @FXML
    void onClick(MouseEvent event) {

    }

    @FXML
    void onSpaceClick(MouseEvent event) {

    }

    @FXML
    void initialize() {

    }

    private void cardSetter(ImageView cardSpace, Integer cardID) {
        cardSpace.setImage(new Image(new File("src/main/resources/javafx/cards/cardID" + cardID + ".png").toURI().toString()));
    }

    @Override
    public void update(Map<String, String> gameState) throws IOException {
        this.rootInstance = mapper.readTree(gameState.get("GameInstance"));
        this.rootBoard = mapper.readTree(gameState.get("GameBoard"));

        cardSetter(green_0, rootBoard.path("towers").path(CardColor.GREEN.toString()).path("towerSpaces").path("0").path("card").path("cardID").asInt());
        cardSetter(green_1, rootBoard.path("towers").path(CardColor.GREEN.toString()).path("towerSpaces").path("1").path("card").path("cardID").asInt());
        cardSetter(green_2, rootBoard.path("towers").path(CardColor.GREEN.toString()).path("towerSpaces").path("2").path("card").path("cardID").asInt());
        cardSetter(green_3, rootBoard.path("towers").path(CardColor.GREEN.toString()).path("towerSpaces").path("3").path("card").path("cardID").asInt());
        cardSetter(blue_0, rootBoard.path("towers").path(CardColor.BLUE.toString()).path("towerSpaces").path("0").path("card").path("cardID").asInt());
        cardSetter(blue_1, rootBoard.path("towers").path(CardColor.BLUE.toString()).path("towerSpaces").path("1").path("card").path("cardID").asInt());
        cardSetter(blue_2, rootBoard.path("towers").path(CardColor.BLUE.toString()).path("towerSpaces").path("2").path("card").path("cardID").asInt());
        cardSetter(blue_3, rootBoard.path("towers").path(CardColor.BLUE.toString()).path("towerSpaces").path("3").path("card").path("cardID").asInt());
        cardSetter(yellow_0, rootBoard.path("towers").path(CardColor.YELLOW.toString()).path("towerSpaces").path("0").path("card").path("cardID").asInt());
        cardSetter(yellow_1, rootBoard.path("towers").path(CardColor.YELLOW.toString()).path("towerSpaces").path("1").path("card").path("cardID").asInt());
        cardSetter(yellow_2, rootBoard.path("towers").path(CardColor.YELLOW.toString()).path("towerSpaces").path("2").path("card").path("cardID").asInt());
        cardSetter(yellow_3, rootBoard.path("towers").path(CardColor.YELLOW.toString()).path("towerSpaces").path("3").path("card").path("cardID").asInt());
        cardSetter(purple_0, rootBoard.path("towers").path(CardColor.PURPLE.toString()).path("towerSpaces").path("0").path("card").path("cardID").asInt());
        cardSetter(purple_1, rootBoard.path("towers").path(CardColor.PURPLE.toString()).path("towerSpaces").path("1").path("card").path("cardID").asInt());
        cardSetter(purple_2, rootBoard.path("towers").path(CardColor.PURPLE.toString()).path("towerSpaces").path("2").path("card").path("cardID").asInt());
        cardSetter(purple_3, rootBoard.path("towers").path(CardColor.PURPLE.toString()).path("towerSpaces").path("3").path("card").path("cardID").asInt());

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




