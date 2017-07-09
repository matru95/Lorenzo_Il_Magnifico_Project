package it.polimi.ingsw.gc31.view.jfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class GameViewFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Media musicFile = new Media(new File("src/main/resources/javafx/lorenzo.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(musicFile);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/javafx/Board.fxml"));
        TabPane mainPane = loader.load();
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("LORENZO IL MAGNIFICO");
        primaryStage.getIcons().add(new Image(new File("src/main/resources/javafx/icon.png").toURI().toString()));
        primaryStage.setMaximized(true);
        primaryStage.show();

    }

}
