package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MartWrapper extends SpaceWrapper {
    private FamilyMember familyMember;
    private List<Resource> res;
    /**
     * Costructor of MartWrapper
     * @param positionID
     * @param diceBond
     * @param gameBoard
     * @param res List of resource of the single market space wrapper
     * */
    public MartWrapper(int positionID, int diceBond, GameBoard gameBoard, List<Resource> res) {
        super(positionID, diceBond, gameBoard);
        this.res = res;
    }
        /**
         *
         * @param familyMember
         * @param amountOfServants
         * @return messages*/
    @Override
    public List<ServerMessage> execWrapper(FamilyMember familyMember, int amountOfServants) {
        //TODO CON ENDI SE HA IL MALUS INVIARE MESSAGGIO DI MOSSA NON VALIDA PER VIA DEL MALUS
        setOccupied(true);
        AddResEffect addResEffect = new AddResEffect(res);
        List<ServerMessage> messages = new ArrayList<>();

        ServerMessage message = addResEffect.exec(familyMember.getPlayer());
        messages.add(message);

        return messages;
    }

    @Override
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode martWrapperNode = super.toObjectNode();

        martWrapperNode.set("bonus", generateBonusJSON(mapper));

        if(familyMember != null) {
            martWrapperNode.set("familyMember", familyMember.toJson());
        }

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
    public boolean isAffordable(FamilyMember familyMember, Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {
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
