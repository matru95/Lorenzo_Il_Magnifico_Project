package it.polimi.ingsw.gc31.model.states;


import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.CouncilsPalaceWrapper;

public class TurnEndState implements State{

    @Override
    public void doAction(GameInstance context) throws NoResourceMatch {
        CouncilsPalaceWrapper councilsPalaceWrapper = (CouncilsPalaceWrapper) context.getGameBoard().getSpaceById(23);
        Player[] orderedPlayers = councilsPalaceWrapper.getNewPlayerOrder();
        orderPlayers(orderedPlayers);
    }

    private void orderPlayers(Player[] orderedPlayers) {
        int index;

        for(index=0; index<orderedPlayers.length; index++) {
            orderedPlayers[index].setPlayerOrder(index);
        }
    }
}
