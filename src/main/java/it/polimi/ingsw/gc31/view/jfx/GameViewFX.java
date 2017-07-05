package it.polimi.ingsw.gc31.view.jfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class GameViewFX extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/javafx/Bones.fxml"));
        Pane mainPane = loader.load();
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}
