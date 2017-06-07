package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Tower {

    /** Maps contains towerSpaceWrappers of a single tower:
     * Key is floorID, Value is TowerSpaceWrapper
     */
    private Map<Integer, TowerSpaceWrapper> towerSpaces;
    private CardColor towerColor;
    private boolean isOccupied;

    public Tower(CardColor towerColor, GameBoard gameBoard) {
        this.towerColor = towerColor;
        this.isOccupied = false;
        this.towerSpaces = new HashMap<>();

        int diceValue = 1;

        //TODO specializza risorse towerBonus per torre
        Resource towerBonus = new Resource(ResourceName.GOLD, 3);
        
        for (int floorID = 0; floorID < 4; floorID++) {
            towerSpaces.put(floorID, new TowerSpaceWrapper(
                    gameBoard.getPositionIndex(),
                    diceValue,
                    gameBoard,
                    towerColor,
                    towerBonus));
            gameBoard.incrementPositionIndex();
            diceValue+=2;
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

    public Map<Integer, TowerSpaceWrapper> getTowerSpace() {
        return towerSpaces;
    }
}
