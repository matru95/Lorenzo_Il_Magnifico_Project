package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.Map;

public class MartWrapper extends SpaceWrapper {
    private FamilyMember familyMember;

    MartWrapper(int positionID, int diceBond, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
    }

    @Override
    public void execWrapper(Map<String, Resource> playerResources) {
        //TODO
        setOccupied(true);
    }

    @Override
    public boolean isAffordable(Map<String, Resource> playerResources) {
        return true;
    }

    @Override
    public void setFamilyMember(FamilyMember familyMember) {
        this.familyMember = familyMember;
    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }

}
