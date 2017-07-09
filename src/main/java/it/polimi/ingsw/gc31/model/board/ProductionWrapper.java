package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.effects.boardeffects.ProductionEffect;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionWrapper extends SpaceWrapper {

    private boolean isFirstPlayer;
    private boolean isMultiple;
    private List<FamilyMember> familyMembers;
    private int malus;
    private int multipleMalus;

    public ProductionWrapper(int positionID, int diceBond, boolean isMultiple, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
        this.isMultiple = isMultiple;
        familyMembers = new ArrayList<>();
        this.malus = 0;
        this.multipleMalus = 0;

    }

    @Override
    public List<ServerMessage> execWrapper(FamilyMember familyMember, int amountOfServants) {
        int value = familyMember.getValue() + amountOfServants - malus;
        int myServants = familyMember.getPlayer().getRes().get(ResourceName.SERVANTS).getNumOf();
        List<ServerMessage> messages = new ArrayList<>();
        ServerMessage message = new ServerMessage();
        Map<String, String> payload = new HashMap<>();

        payload.put("positionType", "production");
        payload.put("myServants", String.valueOf(myServants));

        message.setMessageType(ServerMessageEnum.SERVANTSREQUEST);
        message.setPayLoad(payload);
        messages.add(message);

        if (!isMultiple) setOccupied(true);

        return messages;
    }

    @Override
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode productionWrapperNode = super.toObjectNode();

        productionWrapperNode.put("malus", malus);

        if(familyMembers.size() != 0) {
            ArrayNode familyMembersNode = mapper.createArrayNode();

            for(FamilyMember familyMember: familyMembers) {
                if(familyMember != null) {

                    familyMembersNode.add(familyMember.toJson());
                }
            }

            productionWrapperNode.set("familyMembers", familyMembersNode);
        }

        return productionWrapperNode;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode productionWrapperNode = toJson();

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(productionWrapperNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

    }

    @Override
    public boolean isAffordable(FamilyMember familyMember, Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {
        int servants = familyMember.getPlayer().getRes().get(ResourceName.SERVANTS).getNumOf();
        int familyMemberValue = familyMember.getValue();
        int finalValue = servants+familyMemberValue;

        if(this.getDiceBond() > finalValue) return false;



        if(!isMultiple && familyMembers.size() == 1) {
            return  false;
        }

//      Check if there's already a FM with the same color
        if(hasFamilyMemberSameColor(playerColor, familyMembers)) {
            return false;
        }

        return true;
    }

    public void setFamilyMember(FamilyMember familyMember) {

        familyMembers.add(familyMember);
    }

    @Override
    public void reset() {
        this.familyMembers.clear();
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

    public void setMalus(int malus) {
        this.malus = malus;
    }

    public int getMalus() {
        return this.malus;
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
