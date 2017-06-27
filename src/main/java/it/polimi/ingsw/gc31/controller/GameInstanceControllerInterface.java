package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

import java.util.Map;

public interface GameInstanceControllerInterface {

    void getDataFromView(Map data);
    void sendDataToView(Map data);
    void playGame() throws NoResourceMatch;

}
