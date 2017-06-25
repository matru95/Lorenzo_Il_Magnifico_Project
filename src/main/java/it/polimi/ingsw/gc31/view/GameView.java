package it.polimi.ingsw.gc31.view;

import java.io.IOException;
import java.util.Map;

public interface GameView {
    public void update(Map<String, String> gameState) throws IOException;
}
