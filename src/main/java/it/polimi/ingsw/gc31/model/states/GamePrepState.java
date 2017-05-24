package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;

public class GamePrepState implements State{
    private int numOfPlayers;

    @Override
    public void doAction(GameInstance context) {
        Player[] players = context.getPlayers();
        numOfPlayers = context.getNumOfPlayers();

        blockSpacesIfNeeded();
    }

    private void blockSpacesIfNeeded() {
        if(this.numOfPlayers == 2) {
            // block some spaces
        } else if(this.numOfPlayers == 3) {
            // block some other spaces
        }
    }

}
