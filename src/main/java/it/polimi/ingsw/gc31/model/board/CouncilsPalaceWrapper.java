package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.GameInstance;

public class CouncilsPalaceWrapper extends SpaceWrapper {

    private boolean[] isPlayerInQueque;

    CouncilsPalaceWrapper(int positionID, int diceBond, GameInstance gameInstance) {
        super(positionID, diceBond, gameInstance);
        isPlayerInQueque = new boolean[getGameInstance().getNumOfPlayers()];
        for (int i = 0; i < getGameInstance().getNumOfPlayers(); i++) {
            isPlayerInQueque[i] = false;
        }
    }

    @Override
    public void execWrapper() {
        //TODO
        if(!isPlayerInQueque[getMember().getPlayer().getPlayerOrder()]) {
            this.getGameInstance().putPlayerInQueque(this.getMember().getPlayer());
            isPlayerInQueque[getMember().getPlayer().getPlayerOrder()] = true;
        }
    }
}
