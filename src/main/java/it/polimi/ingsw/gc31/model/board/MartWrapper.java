package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.util.List;
import java.util.Map;

public class MartWrapper extends SpaceWrapper {
    private FamilyMember familyMember;
    private List<Resource> res;

    public MartWrapper(int positionID, int diceBond, GameBoard gameBoard, List<Resource> res) {
        super(positionID, diceBond, gameBoard);
        this.res = res;
    }

    @Override
    public void execWrapper(FamilyMember familyMember, int amountOfServants) {
        setOccupied(true);
        Map<ResourceName, Resource> playerResources = familyMember.getPlayer().getRes();
        for(Resource myResource : res) {
            int amount = myResource.getNumOf();
            playerResources.get(myResource.getResourceName()).addNumOf(amount);
        }
    }

    @Override
    public boolean isAffordable(Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {
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
