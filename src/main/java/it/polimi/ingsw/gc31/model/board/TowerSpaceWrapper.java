package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;


import java.util.List;
import java.util.Map;

public class TowerSpaceWrapper extends SpaceWrapper {

    private final CardColor color;
    private final Tower tower;
    private Card card;
    private Resource res;
    private FamilyMember familyMember;

    public TowerSpaceWrapper(int positionID, int diceBond, GameBoard gameBoard, Tower tower, Resource res) {
        super(positionID, diceBond, gameBoard);
        this.color = tower.getTowerColor();
        this.tower = tower;
        this.res = res;
    }

    @Override
    public void execWrapper(FamilyMember familyMember, int amountOfServants) {
        payCost(familyMember.getPlayer(),this.card);
        //TODO CHECK IF IS POSSIBLE ELSE RETURN MSG TO PLAYER
        execTowerBonus(familyMember.getPlayer().getRes());
        //TODO EXEC OF CARD
        setOccupied(true);
    }

    @Override
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode towerSpaceWrapperNode = super.toObjectNode();

        if(this.card != null) {
            towerSpaceWrapperNode.set("card", card.toJson());
        }

        return towerSpaceWrapperNode;
    }


    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode towerSpaceWrapperNode = toJson();

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(towerSpaceWrapperNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void execTowerBonus(Map<ResourceName, Resource> playerResources) {
        if(res == null) {
            return;
        }

        Resource resourceToAddTo = playerResources.get(res.getResourceName());
        int numberToAdd = res.getNumOf();
        resourceToAddTo.addNumOf(numberToAdd);
    }

    public boolean isAffordable(Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {

        List<Map<ResourceName, Resource>> cardResources = this.getCard().getCost();
        boolean[] results = new boolean[cardResources.size()];

        int index = 0;

        for(Map<ResourceName, Resource> cardResource: cardResources) {
            for(Map.Entry<ResourceName, Resource> playerResource: playerResources.entrySet()) {
                int playerResourceValue = playerResource.getValue().getNumOf();
                String resourceNameString = playerResource.getKey().toString();

                Resource singleCardResource = cardResource.get(ResourceName.valueOf(resourceNameString));
                int cardResourceValue = 0;

                if(singleCardResource != null) {
                    cardResourceValue = singleCardResource.getNumOf();
                }

                if(playerResourceValue < cardResourceValue) {
                    results[index] = false;
                }

            }
            results[index] = true;
            index++;
        }

        index = 0;

//      Check if family member has enough gold to occupy a occupied tower
        if(this.tower.isOccupied()) {
            int playerGold = playerResources.get(ResourceName.GOLD).getNumOf();

            for(Map<ResourceName, Resource> cardResource: cardResources) {
                int possibleCost = cardResource.get(ResourceName.GOLD).getNumOf()+3;

                if(playerGold < possibleCost) {
                    results[index] = false;
                }
                index++;
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

    public void setCard(Card card) {
        this.card = card;
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

    private void payCost(Player player, Card card){
        List <Map<ResourceName,Resource>> cardCost = card.getCost();
        if(cardCost.size()==1){
            Map<ResourceName,Resource> singleCardCost= cardCost.get(0);
            for(Map.Entry <ResourceName,Resource> singleCardCostEntry: singleCardCost.entrySet()){
                ResourceName cardCostName=singleCardCostEntry.getKey();
                int value=singleCardCostEntry.getValue().getNumOf();
                player.getRes().get(cardCostName).subNumOf(value);
            }
        }
    }
}
