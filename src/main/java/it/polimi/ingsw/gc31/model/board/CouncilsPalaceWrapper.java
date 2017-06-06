package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.Map;

public class CouncilsPalaceWrapper extends SpaceWrapper {

    private boolean[] isPlayerInQueque;

    CouncilsPalaceWrapper(int positionID, int diceBond, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
        isPlayerInQueque = new boolean[gameBoard.getGameInstance().getNumOfPlayers()];
        for (int i = 0; i < gameBoard.getGameInstance().getNumOfPlayers(); i++) {
            isPlayerInQueque[i] = false;
        }
    }

    @Override
    public void execWrapper(Map<String, Resource> playerResources) {
        //TODO
        if(!isPlayerInQueque[getMember().getPlayer().getPlayerOrder()]) {
            getGameBoard().getGameInstance().putPlayerInQueue(this.getMember().getPlayer());
            isPlayerInQueque[getMember().getPlayer().getPlayerOrder()] = true;
        }
    }

    @Override
    public boolean isAffordable(Map<String, Resource> playerResources) {
        return true;
    }
}
