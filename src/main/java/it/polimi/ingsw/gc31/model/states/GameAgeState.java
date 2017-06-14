package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;

public class GameAgeState implements State {

    @Override
    public void doAction(GameInstance context) {
        for(Player p: context.getPlayers()) {

        }
    }
}
