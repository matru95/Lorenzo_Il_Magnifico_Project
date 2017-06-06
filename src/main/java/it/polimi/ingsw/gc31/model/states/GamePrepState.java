package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.cards.CardColor;

import java.util.ArrayList;

public class GamePrepState implements State{
    private int numOfPlayers;

    @Override
    public void doAction(GameInstance context) {
        ArrayList<Player> players = context.getPlayers();
        numOfPlayers = context.getNumOfPlayers();

    }


    private void distributeResources(Player[] players) {
        for(Player player: players) {
           int playerOrder = player.getPlayerOrder();
        }
    }

}
