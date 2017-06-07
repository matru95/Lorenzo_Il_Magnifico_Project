package it.polimi.ingsw.gc31.model.board;

import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceFactory;

import java.util.Map;

public class TowerSpaceWrapper extends SpaceWrapper {

    private final CardColor color;
    private final Tower tower;
    private Card card;
    private Resource towerBonus;
    private FamilyMember familyMember;

    TowerSpaceWrapper(int positionID, int diceBond, GameBoard gameBoard, CardColor color, Resource res) {
        super(positionID, diceBond, gameBoard);
        this.color = color;
        this.tower = gameBoard.getTowerByColor(this.color);
        this.towerBonus = ResourceFactory.getResource(res.getResourceName().toString(),res.getNumOf());
        //TODO Randomize a card
    }

    @Override
    public void execWrapper(Map<String, Resource> playerResources) {
        //TODO CHECK IF IS POSSIBLE ELSE RETURN MSG TO PLAYER
        execTowerBonus(playerResources);
        //TODO EXEC OF CARD
        setOccupied(true);
    }

    private void execTowerBonus(Map<String, Resource> playerResources) {
        playerResources.get(towerBonus.getResourceName()).addNumOf(towerBonus.getNumOf());
    }

    public boolean isAffordable(Map<String, Resource> playerResources) {

        Map<String, Resource>[] cardResources = this.getCard().getCost();
        boolean[] results = new boolean[cardResources.length];

        for(int i=0; i<cardResources.length; i++) {
            for(Map.Entry<String, Resource> playerResource: playerResources.entrySet()) {
                int playerResourceValue = playerResource.getValue().getNumOf();
                int cardResourceValue = cardResources[i].get(playerResource.getKey()).getNumOf();

                if(playerResourceValue < cardResourceValue) {
                    results[i] = false;
                }

            }
            results[i] = true;
        }

//      Check if family member has enough gold to occupy a occupied tower
        if(this.tower.isOccupied()) {
            int playerGold = playerResources.get("Gold").getNumOf();

            for(int i=0; i<cardResources.length; i++) {
                int possibleCost = cardResources[1].get("Gold").getNumOf()+3;

                if(playerGold < possibleCost) {
                    results[i] = false;
                }
            }
        }

//      If at least one of the costs is less than player resources, return true
        for(boolean affordability: results) {
            if(affordability) {
                return true;
            }
        }

        return false;
    }

    public CardColor getColor() {
        return color;
    }

    public Card getCard() {
        return card;
    }

    public Tower getTower() {
        return tower;
    }

    public void setFamilyMember(FamilyMember familyMember) {
        this.familyMember = familyMember;
    }

    public FamilyMember getFamilyMember() {
        return this.familyMember;
    }
}
