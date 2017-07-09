package it.polimi.ingsw.gc31.model.states;


import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.CouncilsPalaceWrapper;
import it.polimi.ingsw.gc31.model.board.NullWrapper;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;

import java.util.List;

public class TurnEndState implements State{

    @Override
    public void doAction(GameInstance context) {
        CouncilsPalaceWrapper councilsPalaceWrapper = (CouncilsPalaceWrapper) context.getGameBoard().getSpaceById(23);
        Player[] orderedPlayers = councilsPalaceWrapper.getNewPlayerOrder();
        orderPlayers(orderedPlayers);

        List<SpaceWrapper> positions = context.getGameBoard().getAllSpaces();

        for(SpaceWrapper position: positions) {
            position.reset();
        }

        for(Player player: orderedPlayers) {
            for(FamilyMember familyMember: player.getFamilyMembers()) {
                familyMember.setMovedThisTurn(false);
                familyMember.setCurrentPosition(new NullWrapper());
            }
        }
    }

    private void orderPlayers(Player[] orderedPlayers) {
        int index;

        for(index=0; index<orderedPlayers.length; index++) {
            orderedPlayers[index].setPlayerOrder(index+1);
        }
    }
}
