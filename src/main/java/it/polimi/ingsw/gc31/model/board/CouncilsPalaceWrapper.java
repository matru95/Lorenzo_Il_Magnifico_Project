package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CouncilsPalaceWrapper extends SpaceWrapper {

    private boolean[] isPlayerInQueue;
    private List<FamilyMember> familyMembers;

    CouncilsPalaceWrapper(int positionID, int diceBond, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
        this.familyMembers = new ArrayList<>();

        isPlayerInQueue = new boolean[gameBoard.getGameInstance().getNumOfPlayers()];
        for (int i = 0; i < gameBoard.getGameInstance().getNumOfPlayers(); i++) {
            isPlayerInQueue[i] = false;
        }
    }

    @Override
    public void execWrapper(FamilyMember familyMember) {
        //TODO
//        if(!isPlayerInQueue[getMember().getPlayer().getPlayerOrder()]) {
//            getGameBoard().getGameInstance().putPlayerInQueue(this.getMember().getPlayer());
//            isPlayerInQueue[getMember().getPlayer().getPlayerOrder()] = true;
//        }
    }

    @Override
    public boolean isAffordable(Map<String, Resource> playerResources, PlayerColor playerColor) {
        return true;
    }

    @Override
    public void setFamilyMember(FamilyMember familyMember) {

        familyMembers.add(familyMember);
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }
}
