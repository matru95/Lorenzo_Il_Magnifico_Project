package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.util.Map;

public class NullWrapper extends SpaceWrapper {

    private FamilyMember familyMember;

    public NullWrapper() {
        super(0, 0, null);
    }

    @Override
    public ServerMessage execWrapper(FamilyMember familyMember, int amountOfServants) {

        return null;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean isAffordable(FamilyMember familyMember, Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {
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
