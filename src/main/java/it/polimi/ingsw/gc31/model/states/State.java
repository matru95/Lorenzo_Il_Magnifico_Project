package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.GameInstance;

public interface State {

    /**Execute current State's action
     * @param context (GameInstance)
     */
    void doAction(GameInstance context);
}
