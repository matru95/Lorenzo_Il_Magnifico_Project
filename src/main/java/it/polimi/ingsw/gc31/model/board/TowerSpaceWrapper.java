package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.messages.ServerMessage;
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
    private transient Card card;
    private Resource res;
    private transient FamilyMember familyMember;

    TowerSpaceWrapper(int positionID, int diceBond, GameBoard gameBoard, Tower tower, Resource res) {
        super(positionID, diceBond, gameBoard);
        this.color = tower.getTowerColor();
        this.tower = tower;
        this.res = res;
    }

    @Override
    public ServerMessage execWrapper(FamilyMember familyMember, int amountOfServants) {
        payCost(familyMember.getPlayer(), this.card);

        if(tower.isOccupied()) {
            familyMember.getPlayer().getRes().get(ResourceName.GOLD).subNumOf(3);
        }

        execTowerBonus(familyMember.getPlayer().getRes());

        try {
            card.execInstantEffect(familyMember.getPlayer());
        } catch (NoResourceMatch noResourceMatch) {
            noResourceMatch.printStackTrace();
        }

        setOccupied(true);
        tower.setOccupied(true);
        return null;
    }

    @Override
    public ObjectNode toJson() {
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

    public boolean isAffordable(FamilyMember familyMember, Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {


        if(this.tower.isOccupied() && !isTowerOccupiedAffordable(playerResources)) {
//          Tower is occupied and familymember can't pay gold
            return false;
        }

        return canPayCost(familyMember, playerResources);
    }

    /**
     * Method that check if a family member has enough gold to occupy an already occupied tower
     * @param playerResources
     * @return boolean
     */
    private boolean isTowerOccupiedAffordable(Map<ResourceName, Resource> playerResources) {
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

    private boolean canPayCost(FamilyMember familyMember, Map<ResourceName, Resource> playerResources) {
        List<Map<ResourceName, Resource>> cardCosts = getCard().getCost();
        int playerServants = playerResources.get(ResourceName.SERVANTS).getNumOf();
        int diceValue = familyMember.getValue();
        boolean result = false;

//      Check if the card has a cost, if not, return true immediately
        if(cardCosts.isEmpty() && this.getDiceBond() < diceValue+playerServants) {

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

                if(playerResourceAmount < cardCostAmount || playerResource.getResourceName() == ResourceName.SERVANTS && playerResourceAmount < cardCostAmount+3) {
                    singleCostResult = false;
                }
            }

            if(singleCostResult) {
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

    private void payCost(Player player, Card card) {
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
