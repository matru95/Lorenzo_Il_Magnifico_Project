package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.Dice;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;

import java.util.ArrayList;

public class TurnState implements State{
    private Player[] orderedPlayers;

    @Override
    public void doAction(GameInstance context) {
        this.orderedPlayers = new Player[context.getNumOfPlayers()];
        ArrayList<Player> players = context.getPlayers();

//      Get an array of players ordered by turn order
        this.orderedPlayers = orderPlayerActions(players);

//      Throw the dice
        Dice[] dice = context.getGameBoard().getDice();
        this.throwDice(dice);


        for(int i=0; i<this.orderedPlayers.length; i++) {
            this.orderedPlayers[i].doPlayerActions();
        }
    }

    private Player[] orderPlayerActions(ArrayList<Player> players) {
//      Return the array of playered ordered by their playing order this turn.
        Player[] orderedPlayers = new Player[players.size()];

        for(int i = 0; i< players.size(); i++) {
            int currentOrder = players.get(i).getPlayerOrder();
            orderedPlayers[currentOrder-1] = players.get(i);
        }

        return orderedPlayers;
    }

    private void throwDice(Dice[] dice) {
        for(Dice singleDice: dice) {
            singleDice.throwDice();
        }
    }
}
