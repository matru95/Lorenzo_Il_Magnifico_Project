package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HarvestWrapper extends SpaceWrapper {
    private List<FamilyMember> familyMembers;
    private boolean isFirstPlayer;
    private boolean isMultiple;

    HarvestWrapper(int positionID, int diceBond, boolean isMultiple, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
        this.familyMembers = new ArrayList<>();
        this.isMultiple = isMultiple;
    }

    @Override
    public void execWrapper(Map<String, Resource> playerResources) {
        harvest();
        if (!isMultiple) setOccupied(true);
    }

    @Override
    public boolean isAffordable(Map<String, Resource> playerResources, PlayerColor playerColor) {


        if(!isMultiple && familyMembers.size() == 1) {
            return false;
        }

//      Check if there's already a FM with the same color
        for(FamilyMember familyMember: familyMembers) {
            if(familyMember.getPlayerColor() == playerColor) {
                return false;
            }
        }
        return true;
     }

    @Override
    public void setFamilyMember(FamilyMember familyMember) {

        familyMembers.add(familyMember);
    }

    private void harvest(){
        //TODO
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        isFirstPlayer = firstPlayer;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }
}