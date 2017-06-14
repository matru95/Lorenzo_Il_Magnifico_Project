package it.polimi.ingsw.gc31.controller;

import java.util.Map;

public interface GameInstanceControllerInterface {

    void getDataFromView(Map data);
    void sendDataToView(Map data);
    void playGame();

}
