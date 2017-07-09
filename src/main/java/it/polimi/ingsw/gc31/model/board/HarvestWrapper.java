package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
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
    private int multipleMalus;

    public HarvestWrapper(int positionID, int diceBond, boolean isMultiple, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
        this.familyMembers = new ArrayList<>();
        this.isMultiple = isMultiple;
        this.malus = 0;
    }

    @Override
    public List<ServerMessage> execWrapper(FamilyMember familyMember, int amountOfServants) {
        int value = familyMember.getValue() + amountOfServants - malus;

        HarvestEffect harvestEffect = new HarvestEffect();
        List<ServerMessage> messages = harvestEffect.exec(familyMember.getPlayer(), value);

        if (!isMultiple) setOccupied(true);

        this.malus = multipleMalus;

        familyMember.getPlayer().getRes().get(ResourceName.SERVANTS).subNumOf(amountOfServants);

        return messages;
    }

    @Override
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode harvestWrapperNode = super.toObjectNode();

        harvestWrapperNode.put("malus", malus);

        if(familyMembers.size() > 0) {
            ArrayNode arrayNode = mapper.createArrayNode();

            for(FamilyMember familyMember: familyMembers) {
                arrayNode.add(familyMember.toJson());
            }

            harvestWrapperNode.set("familyMembers", arrayNode);
        }

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
        int playerServants = familyMember.getPlayer().getRes().get(ResourceName.SERVANTS).getNumOf();
        int familyMemberValue = familyMember.getValue();
        int finalValue = playerServants+familyMemberValue;

        if(this.getDiceBond() > finalValue) {
            return false;
        }

        if(!isMultiple && familyMembers.size() == 1) {
            return false;
        }

//      Check if there's already a FM with the same color
        if(hasFamilyMemberSameColor(playerColor, familyMembers)) {
            return false;
        }

        return true;
     }


    @Override
    public void setFamilyMember(FamilyMember familyMember) {

        if(familyMember == null) {
            familyMembers = new ArrayList<>();
        }

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

    public void setMultipleMalus(int multipleMalus) {
        this.multipleMalus = multipleMalus;
    }
}