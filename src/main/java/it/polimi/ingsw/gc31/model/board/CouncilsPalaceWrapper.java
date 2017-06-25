package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CouncilsPalaceWrapper extends SpaceWrapper {

    private boolean[] isPlayerInQueue;
    private List<FamilyMember> familyMembers;
    private Resource res;

    public CouncilsPalaceWrapper(int positionID, int diceBond, GameBoard gameBoard, Resource res) {
        super(positionID, diceBond, gameBoard);
        this.familyMembers = new ArrayList<>();
        this.res = res;

        isPlayerInQueue = new boolean[gameBoard.getGameInstance().getNumOfPlayers()];
        for (int i = 0; i < gameBoard.getGameInstance().getNumOfPlayers(); i++) {
            isPlayerInQueue[i] = false;
        }
    }

    @Override
    public void execWrapper(FamilyMember familyMember , int amountOfServants) {
        //TODO
//        if(!isPlayerInQueue[getMember().getPlayer().getPlayerOrder()]) {
//            getGameBoard().getGameInstance().putPlayerInQueue(this.getMember().getPlayer());
//            isPlayerInQueue[getMember().getPlayer().getPlayerOrder()] = true;
//        }
    }

    @Override
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode councilsPalaceWrapperNode = super.toObjectNode();

        councilsPalaceWrapperNode.set("bonus", res.toJson());

        return councilsPalaceWrapperNode;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode councilsPalaceWrapperNode = toJson();

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(councilsPalaceWrapperNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

    }

    @Override
    public boolean isAffordable(Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {
        return true;
    }

    @Override
    public void setFamilyMember(FamilyMember familyMember) {

        familyMembers.add(familyMember);
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }
}
