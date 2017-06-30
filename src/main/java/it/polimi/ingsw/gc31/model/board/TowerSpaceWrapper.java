package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.org.apache.regexp.internal.RE;
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
        List<Map<ResourceName, Resource>> cardCost = this.getCard().getCost();
        boolean[] results = new boolean[cardCost.size()];

        if(this.tower.isOccupied()) {

            if(!isTowerOccupiedAffordable(playerResources, playerColor)) {
//              Tower is occupied and familymember can't pay gold
                return false;
            }
        }

        return canPayCost(playerResources, playerColor);
    }

    /**
     * Method that check if a family member has enough gold to occupy an already occupied tower
     * @param playerResources
     * @param color
     * @return boolean
     */
    private boolean isTowerOccupiedAffordable(Map<ResourceName, Resource> playerResources, PlayerColor color) {
        int playerGold = playerResources.get(ResourceName.GOLD).getNumOf();
        List<Map<ResourceName, Resource>> cardCost = this.getCard().getCost();

        for(Map<ResourceName, Resource> cardResource: cardCost) {
            int possibleCost = cardResource.get(ResourceName.GOLD).getNumOf()+3;

            if(playerGold < possibleCost) {
                return false;
            }
        }

        return true;
    }

    private boolean canPayCost(Map<ResourceName, Resource> playerResources, PlayerColor color) {
        List<Map<ResourceName, Resource>> cardCosts = getCard().getCost();
        boolean result = false;

//      Check if the card has a cost, if not, return true immediately
        if(cardCosts.size() == 0) {
            return true;
        }

//      Check if the card requires gold and if the tower is occupied


        for(Map<ResourceName, Resource> cardCost: cardCosts) {
            boolean singleCostResult = true;

            if(cardCost.get(ResourceName.GOLD) != null && tower.isOccupied()) {
                int goldToPay = cardCost.get(ResourceName.GOLD).getNumOf() + 3;

                if(goldToPay > playerResources.get(ResourceName.GOLD).getNumOf()) {

                    return false;
                }
            }

            for(Map.Entry<ResourceName, Resource> singleCardCostField: cardCost.entrySet()) {
                Resource playerResource = playerResources.get(singleCardCostField.getKey());
                int playerResourceAmount = playerResource.getNumOf();
                int cardCostAmount = singleCardCostField.getValue().getNumOf();

                if(playerResourceAmount < cardCostAmount) {
                    singleCostResult = false;
                }
            }

            if(singleCostResult == true) {
                result = true;
            }
        }

        return result;

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
