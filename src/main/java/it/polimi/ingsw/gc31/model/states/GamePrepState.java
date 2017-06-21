package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GamePrepState implements State {

    private int numOfPlayers;

    @Override
    public void doAction(GameInstance context) {
        ArrayList<Player> players = context.getPlayers();
        numOfPlayers = context.getNumOfPlayers();
        this.distributeResources(players);

    }

    private void distributeResources(List<Player> players) {
        for(Player player: players) {
           int playerOrder = player.getPlayerOrder();
           Map<ResourceName, Resource> playerResources = player.getRes();
           playerResources.get(ResourceName.GOLD).setNumOf(playerOrder+4);

        }
    }

}
