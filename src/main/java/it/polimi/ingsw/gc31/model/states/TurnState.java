package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.Dice;
import it.polimi.ingsw.gc31.model.DiceColor;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.Tower;
import it.polimi.ingsw.gc31.model.cards.CardColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TurnState implements State {

    private Player[] orderedPlayers;

    @Override
    public void doAction(GameInstance context) {
        Map<CardColor, Tower> towers= context.getGameBoard().getTowers();
        for(Map.Entry<CardColor, Tower> towerEntry: towers.entrySet()) {
            towerEntry.getValue().drawCards(towerEntry.getValue());
        }
        this.orderedPlayers = new Player[context.getNumOfPlayers()];
        ArrayList<Player> players = context.getPlayers();

//      Get an array of players ordered by turn order
        this.orderedPlayers = orderPlayerActions(players);

//      Throw the dice

        throwDice(context);

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

    private void throwDice(GameInstance context) {
        for (DiceColor color: DiceColor.values()) {
            context.getGameBoard().getDices().get(color).throwDice();
        }
    }
}
