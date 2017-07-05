package it.polimi.ingsw.gc31.view.jfx;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import it.polimi.ingsw.gc31.view.GameView;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GameViewFXController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ap_1;

    @FXML
    private AnchorPane ap_2;

    @FXML
    private AnchorPane ap_3;

    @FXML
    private AnchorPane ap_4;

    @FXML
    private AnchorPane ap_5;

    @FXML
    private AnchorPane ap_6;

    @FXML
    private AnchorPane ap_7;

    @FXML
    private AnchorPane ap_8;

    @FXML
    private AnchorPane ap_9;

    @FXML
    private AnchorPane ap_10;

    @FXML
    private AnchorPane ap_11;

    @FXML
    private AnchorPane ap_12;

    @FXML
    private AnchorPane ap_13;

    @FXML
    private AnchorPane ap_14;

    @FXML
    private AnchorPane ap_15;

    @FXML
    private AnchorPane ap_16;

    @FXML
    private ImageView green_0;

    @FXML
    void onClick(MouseEvent event) {
        green_0.setImage(new Image(new File("src/main/resources/javafx/cards/cardID" + 38 + ".png").toURI().toString()));
    }

    @FXML
    void initialize() {
        assert ap_1 != null : "fx:id=\"ap_1\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_2 != null : "fx:id=\"ap_2\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_3 != null : "fx:id=\"ap_3\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_4 != null : "fx:id=\"ap_4\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_5 != null : "fx:id=\"ap_5\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_6 != null : "fx:id=\"ap_6\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_7 != null : "fx:id=\"ap_7\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_8 != null : "fx:id=\"ap_8\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_9 != null : "fx:id=\"ap_9\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_10 != null : "fx:id=\"ap_10\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_11 != null : "fx:id=\"ap_11\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_12 != null : "fx:id=\"ap_12\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_13 != null : "fx:id=\"ap_13\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_14 != null : "fx:id=\"ap_14\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_15 != null : "fx:id=\"ap_15\" was not injected: check your FXML file 'Bones.fxml'.";
        assert ap_16 != null : "fx:id=\"ap_16\" was not injected: check your FXML file 'Bones.fxml'.";
        assert green_0 != null : "fx:id=\"green_0\" was not injected: check your FXML file 'Bones.fxml'.";

    }
}

//green_0.setImage(new Image(new File("src/main/resources/javafx/cards/cardID" + 54 + ".png").toURI().toString()));
