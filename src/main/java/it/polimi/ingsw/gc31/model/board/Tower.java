package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.io.Serializable;
import java.util.*;


public class Tower implements Serializable{

    /** Maps contains towerSpaceWrappers of a single tower:
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

    private void initFloors(JsonNode floors) {
        int floorID = 0;
        Resource res = null;
        int positionIndex = gameBoard.getPositionIndex();

        for(JsonNode floor: floors) {
            int diceBond = floor.path("diceBond").asInt();

            if(floor.has("bonus")) {
                String bonusName = floor.path("bonus").fieldNames().next().toString();
                int amount = floor.path("bonus").path(bonusName).asInt();

                res = new Resource(ResourceName.valueOf(bonusName.toUpperCase()), amount);
            }


            TowerSpaceWrapper newFloor = new TowerSpaceWrapper(positionIndex, diceBond, gameBoard, this, res);
            towerSpaces.put(floorID, newFloor);

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
}
