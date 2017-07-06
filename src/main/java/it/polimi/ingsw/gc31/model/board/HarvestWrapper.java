package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.effects.boardeffects.HarvestEffect;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HarvestWrapper extends SpaceWrapper {
    private List<FamilyMember> familyMembers;
    private boolean isFirstPlayer;
    private boolean isMultiple;
    private int malus;

    public HarvestWrapper(int positionID, int diceBond, boolean isMultiple, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
        this.familyMembers = new ArrayList<>();
        this.isMultiple = isMultiple;
        this.malus = 0;
    }

    @Override
    public ServerMessage execWrapper(FamilyMember familyMember, int amountOfServants) {
        int value = familyMember.getValue() + amountOfServants - malus;

        HarvestEffect harvestEffect = new HarvestEffect();
        harvestEffect.exec(familyMember.getPlayer(), value);

        if (!isMultiple) setOccupied(true);

        familyMember.getPlayer().getRes().get(ResourceName.SERVANTS).subNumOf(amountOfServants);

        return null;
    }

    @Override
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode harvestWrapperNode = super.toObjectNode();

        harvestWrapperNode.put("malus", 0);

        return harvestWrapperNode;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode harvestWrapperNode = toJson();

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(harvestWrapperNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public boolean isAffordable(FamilyMember familyMember, Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {

        if(!isMultiple && familyMembers.size() == 1) {
            return false;
        }

//      Check if there's already a FM with the same color
        for(FamilyMember myFamilyMember: familyMembers) {
            if(myFamilyMember.getPlayerColor() == playerColor) {
                return false;
            }
        }
        return true;
     }

    @Override
    public void setFamilyMember(FamilyMember familyMember) {

        familyMembers.add(familyMember);
    }

    public void setMalus(int malus) {
        this.malus = malus;
    }

    public int getMalus() {
        return this.malus;
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