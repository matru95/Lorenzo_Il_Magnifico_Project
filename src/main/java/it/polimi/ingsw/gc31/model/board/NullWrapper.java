package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.util.Map;

public class NullWrapper extends SpaceWrapper {

    private FamilyMember familyMember;

    public NullWrapper() {
        super(0, 0, null);
    }

    @Override
    public void execWrapper(FamilyMember familyMember, int amountOfServants) throws NoResourceMatch {

    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean isAffordable(Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {
        return false;
    }

    @Override
    public void setFamilyMember(FamilyMember familyMember) {
        this.familyMember = familyMember;
    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }
}
