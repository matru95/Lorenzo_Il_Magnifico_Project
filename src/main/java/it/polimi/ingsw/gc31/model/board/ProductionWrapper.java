package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductionWrapper extends SpaceWrapper {

    private boolean isFirstPlayer;
    private boolean isMultiple;
    private List<FamilyMember> familyMembers;

    ProductionWrapper(int positionID, int diceBond, boolean isMultiple, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
        this.isMultiple = isMultiple;
        familyMembers = new ArrayList<>();

    }

    @Override
    public void execWrapper(FamilyMember familyMember) {
        produce(familyMember.getDicePoints());
        if (!isMultiple) setOccupied(true);
    }

    private void produce(int dicePoints){
        //TODO
    }

    @Override
    public boolean isAffordable(Map<String, Resource> playerResources, PlayerColor playerColor) {

        if(!isMultiple && familyMembers.size() == 1) {
            return  false;
        }

//      Check if there's already a FM with the same color
        for(FamilyMember familyMember: familyMembers) {
            if(familyMember.getPlayerColor() == playerColor) {
                return false;
            }
        }
        for(FamilyMember familyMember: familyMembers) {
            if(familyMember.getPlayerColor() == playerColor) {
                return false;
            }
        }

        return true;
    }

    public void setFamilyMember(FamilyMember familyMember) {

        familyMembers.add(familyMember);
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
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
