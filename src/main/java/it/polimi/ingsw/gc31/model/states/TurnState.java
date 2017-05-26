package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.Dice;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;

public class TurnState implements State{
    private Player[] orderedPlayers;

    @Override
    public void doAction(GameInstance context) {
        this.orderedPlayers = new Player[context.getNumOfPlayers()];
        Player[] players = context.getPlayers();

//      Get an array of players ordered by turn order
        this.orderedPlayers = orderPlayerActions(players);

//      Throw the dice
        Dice[] dice = context.getGameBoard().getDice();
        this.throwDice(dice);


        for(int i=0; i<this.orderedPlayers.length; i++) {
            this.orderedPlayers[i].doPlayerActions();
        }
    }

    private Player[] orderPlayerActions(Player[] players) {
//      Return the array of playered ordered by their playing order this turn.
        Player[] orderedPlayers = new Player[players.length];

        for(int i=0; i<players.length; i++) {
            int currentOrder = players[i].getPlayerOrder();
            orderedPlayers[currentOrder-1] = players[i];
        }

        return orderedPlayers;
    }

    private void throwDice(Dice[] dice) {
        for(Dice singleDice: dice) {
            singleDice.throwDice();
        }
    }
}
