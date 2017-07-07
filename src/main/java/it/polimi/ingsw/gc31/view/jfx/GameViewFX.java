package it.polimi.ingsw.gc31.view.jfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class GameViewFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/javafx/Bones.fxml"));
        ScrollPane mainPane = loader.load();
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("LORENZO IL MAGNIFICO");
        primaryStage.setMaximized(true);
        primaryStage.show();

    }

}
