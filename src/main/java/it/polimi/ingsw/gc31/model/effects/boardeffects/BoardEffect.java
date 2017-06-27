package it.polimi.ingsw.gc31.model.effects.boardeffects;

import it.polimi.ingsw.gc31.messages.Message;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

public interface BoardEffect {
    Message exec(Player player, int value) throws NoResourceMatch;
}
