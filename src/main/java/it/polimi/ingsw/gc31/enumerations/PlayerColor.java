package it.polimi.ingsw.gc31.enumerations;

import java.util.ArrayList;
import java.util.List;

public enum PlayerColor {

	RED, BLUE, GREEN, YELLOW;

    public static List<PlayerColor> toList() {
        List<PlayerColor> result = new ArrayList<>();

        for(PlayerColor playerColor: PlayerColor.values()) {
            result.add(playerColor);
        }

        return result;
    }
}
