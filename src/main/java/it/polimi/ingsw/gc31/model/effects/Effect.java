package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.messages.Message;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import jdk.nashorn.internal.ir.ObjectNode;

import java.util.Map;

public abstract class Effect {

    public abstract void exec(Player player) throws NoResourceMatch;
    public abstract ObjectNode toJson();

}

