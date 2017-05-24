package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;

public class TurnState implements State{
    private Player[] orderedPlayers;

    @Override
    public void doAction(GameInstance context) {
        this.orderedPlayers = new Player[context.getNumOfPlayers()];
        Player[] players = context.getPlayers();

        this.orderedPlayers = orderPlayerActions(players);
    }

    private Player[] orderPlayerActions(Player[] players) {
        Player[] orderedPlayers = new Player[players.length];

        for(int i=0; i<players.length; i++) {
            int currentOrder = players[i].getPlayerOrder();
            orderedPlayers[currentOrder-1] = players[i];
        }

        return orderedPlayers;
    }
}
