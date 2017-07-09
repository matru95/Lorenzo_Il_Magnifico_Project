package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.io.Serializable;
import java.util.*;


public class Tower implements Serializable{
    /**
     * Maps contains towerSpaceWrappers of a single tower:
     * Key is floorID, Value is TowerSpaceWrapper
     */

    private Map<Integer, TowerSpaceWrapper> towerSpaces;
    private CardColor towerColor;
    private boolean isOccupied;
    private GameBoard gameBoard;
    private Stack<Card> deck;

    public Tower(CardColor towerColor, GameBoard gameBoard, JsonNode floors) {
        this.towerColor = towerColor;
        this.isOccupied = false;
        this.towerSpaces = new HashMap<>();
        this.gameBoard = gameBoard;
        initFloors(floors);
    }

    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode towerNode = mapper.createObjectNode();

        towerNode.put("towerColor", towerColor.toString());
        towerNode.set("towerSpaces", createTowerSpacesJson(mapper));

        return towerNode;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode towerNode = toJson();

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(towerNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private ObjectNode createTowerSpacesJson(ObjectMapper mapper) {
        ObjectNode towerSpacesNode = mapper.createObjectNode();

        for(Map.Entry<Integer, TowerSpaceWrapper> singleTowerSpaceWrapperRow: towerSpaces.entrySet()) {
            towerSpacesNode.set(singleTowerSpaceWrapperRow.getKey().toString(), singleTowerSpaceWrapperRow.getValue().toJson());
        }

        return towerSpacesNode;
    }

    private void initFloors(JsonNode floors) {

        int floorID = 0;
        Resource res = null;

        for(JsonNode floor: floors) {
            int diceBond = floor.path("diceBond").asInt();
            int positionID = floor.path("positionID").asInt();

            if(floor.has("bonus")) {
                String bonusName = floor.path("bonus").fieldNames().next().toString();
                int amount = floor.path("bonus").path(bonusName).asInt();

                res = new Resource(ResourceName.valueOf(bonusName.toUpperCase()), amount);
            }

            towerSpaces.put(floorID, new TowerSpaceWrapper(positionID, diceBond, gameBoard, this, res));
            floorID++;
        }
    }

    public boolean hasFamilyMemberSameColor(PlayerColor playerColor) {

        for(Map.Entry<Integer, TowerSpaceWrapper> towerSpaceWrapperEntry: towerSpaces.entrySet()) {

            if(towerSpaceWrapperEntry.getValue().isOccupied()) {

                if(towerSpaceWrapperEntry.getValue().getFamilyMember().getPlayerColor() == playerColor) {
                    return  true;
                }
            }
        }
        return false;
    }
    public void drawCards(){
        int i;
        for (i=0;i<=3;i++){
            Card card= deck.pop();
            towerSpaces.get(i).setCard(card);
        }
    }
    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public CardColor getTowerColor() {
        return towerColor;
    }

    public void setDeck(Stack deck) {
        this.deck = deck;
    }

    public Map<Integer, TowerSpaceWrapper> getTowerSpace() {
        return towerSpaces;
    }

    public List<SpaceWrapper> getTowerSpaces() {
        List<SpaceWrapper> spacesToReturn = new ArrayList<>();

        for(Map.Entry<Integer, TowerSpaceWrapper> floor: towerSpaces.entrySet()) {
            spacesToReturn.add(floor.getValue());
        }

        return spacesToReturn;
    }

    public boolean hasFamilyMemberWithColor(PlayerColor color) {
        List<FamilyMember> familyMembers = new ArrayList<>();

        List<SpaceWrapper> spaceWrappers = getTowerSpaces();

//      Insert all family members to the list
        for(SpaceWrapper position: spaceWrappers) {

            if(position.isOccupied()) {
                familyMembers.add(((TowerSpaceWrapper) position).getFamilyMember());
            }
        }

        for(FamilyMember familyMember: familyMembers) {
            if(familyMember.getPlayerColor() == color) {

                return true;
            }
        }

        return false;
    }
}
