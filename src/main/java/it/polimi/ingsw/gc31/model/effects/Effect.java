package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import jdk.nashorn.internal.ir.ObjectNode;

public abstract class Effect {

    public abstract ServerMessage exec(Player player) throws NoResourceMatch;
    public abstract ObjectNode toJson();

}

