package it.polimi.ingsw.gc31.view;

import java.io.IOException;
import java.util.Map;

public interface GameView {

    /**
     * Method for updating the Client's GameView, taking as input a Map
     * which contains JSONs of GameBoard and GameInstance
     * @param gameState: a Map with a String as Key and a String (JSON) as Value
     * @throws IOException
     */
    void update(Map<String, String> gameState) throws IOException;
}
