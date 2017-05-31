package it.polimi.ingsw.gc31.controller;

import java.util.Map;

/**
 * Created by endi on 5/30/17.
 */
public interface Controller {

    void getDataFromView(Map data);
    void sendDataToView(Map data);
}
