package it.polimi.ingsw.gc31.model.effects;


import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

import java.util.HashMap;
import java.util.Map;

public abstract class Effect {
    public abstract void exec() throws NoResourceMatch;

    public Map getRes() {
        return null;
    }
}

