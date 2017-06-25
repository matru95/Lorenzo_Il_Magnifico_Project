package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

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
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode martWrapperNode = mapper.createObjectNode();

        martWrapperNode.set("bonus", generateBonusJSON(mapper));

        return martWrapperNode;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode martWrapperNode = toJson();

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(martWrapperNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Returns the ArrayNode with the JSON representation for each resource in the bonus
     * @param mapper the ObjectMapper that creates the ArrayNode to be returned. Created in the parent method.
     * @return ArrayNode of JSON strings for each Resource in the bonus.
     * @see #toString()
     */
    private ArrayNode generateBonusJSON(ObjectMapper mapper) {
        ArrayNode bonusArray = mapper.createArrayNode();

        for(Resource singleResource: res) {
            bonusArray.add(singleResource.toJson());
        }

        return bonusArray;
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
