package it.polimi.ingsw.gc31.model.effects.boardeffects;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;

import java.util.List;

public interface BoardEffect {
    List<ServerMessage> exec(Player player, int value);
}
