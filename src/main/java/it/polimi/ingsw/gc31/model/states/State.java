package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.GameInstance;

public interface State {
    void doAction(GameInstance context);
}
