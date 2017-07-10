package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.effects.CostEffect;
import it.polimi.ingsw.gc31.model.effects.permanent.Bonus;
import it.polimi.ingsw.gc31.model.effects.permanent.CardColorBonus;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TowerSpaceWrapper extends SpaceWrapper {

    private final CardColor color;
    private final Tower tower;
    private transient Card card;
    private Resource res;
    private transient FamilyMember familyMember;

    /**
     * Constructor of TowerSpaceWrapper
     * @param positionID the position id of the wrapper
     * @param diceBond the dice bond of the wrapper
     * @param gameBoard the gameboard of the wrapper
     * @param tower the tower of the wrapper
     * @param res the res for the wrapper
     */
    TowerSpaceWrapper(int positionID, int diceBond, GameBoard gameBoard, Tower tower, Resource res) {
        super(positionID, diceBond, gameBoard);
        this.color = tower.getTowerColor();
        this.tower = tower;
        this.res = res;
    }

    @Override
    public List<ServerMessage> execWrapper(FamilyMember familyMember, int amountOfServants) {
        List<ServerMessage> result = new ArrayList<>();

        if(tower.isOccupied()) {
            familyMember.getPlayer().getRes().get(ResourceName.GOLD).subNumOf(3);
        }

        if(!familyMember.getPlayer().hasBlockTowerBonus()) {

            execTowerBonus(familyMember.getPlayer());
        }

        result = card.execInstantEffect(familyMember.getPlayer());

        setOccupied(true);
        tower.setOccupied(true);

        ServerMessage message = payCost(familyMember.getPlayer(), this.card);

        result.add(message);
        return result;
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode towerSpaceWrapperNode = super.toObjectNode();

        if(this.familyMember != null) {
            towerSpaceWrapperNode.set("familyMember", familyMember.toJson());
        }

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

    private void execTowerBonus(Player player) {
        if(res == null) {
            return;
        }

        List<Resource> resources = new ArrayList<>();
        resources.add(res);
        AddResEffect addResEffect = new AddResEffect(resources);

        addResEffect.exec(player);
    }

    /**
     *
     * @param familyMember
     * @param playerResources
     * @param playerColor
     * @return
     */
    public boolean isAffordable(FamilyMember familyMember, Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {

        if(this.tower.isOccupied() && !isTowerOccupiedAffordable(playerResources)) {
            return false;
        }

        return canPayCost(familyMember, playerResources);
    }


    private boolean canPayCost(FamilyMember familyMember, Map<ResourceName, Resource> playerResources) {
        int bonusValue = familyMember.getPlayer().getCardColorBonusValue(this.color);
        List<Map<ResourceName, Resource>> cardCosts = getCard().getCost();
        int playerServants = playerResources.get(ResourceName.SERVANTS).getNumOf();
        int diceValue = familyMember.getValue();
        boolean result = false;

//      Check if the card has a cost, if not, return true immediately
        if(cardCosts.isEmpty() && this.getDiceBond() < diceValue+playerServants+bonusValue) {

            return true;
        }

//      Check if the card requires gold and if the tower is occupied


        for(Map<ResourceName, Resource> cardCost: cardCosts) {
            boolean singleCostResult = true;

            if(cardCost.get(ResourceName.GOLD) != null && tower.isOccupied()) {
                int bonusGold = familyMember.getPlayer().getCardColorBonusResourceValue(this.color, ResourceName.GOLD);
                int goldToPay = cardCost.get(ResourceName.GOLD).getNumOf() + 3;

                if(goldToPay > playerResources.get(ResourceName.GOLD).getNumOf()+bonusGold) {

                    return false;
                }
            }

            for(Map.Entry<ResourceName, Resource> singleCardCostField: cardCost.entrySet()) {
                Resource playerResource = playerResources.get(singleCardCostField.getKey());
                int bonusResourceAmount = familyMember.getPlayer().getCardColorBonusResourceValue(this.color, singleCardCostField.getKey());
                int playerResourceAmount = playerResource.getNumOf()+bonusResourceAmount;
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


    /**
     * Method that checks if a family member has enough gold to occupy an already occupied tower
     * @param playerResources
     * @return boolean
     */
    private boolean isTowerOccupiedAffordable(Map<ResourceName, Resource> playerResources) {
        int playerGold = playerResources.get(ResourceName.GOLD).getNumOf();
        List<Map<ResourceName, Resource>> cardCost = this.getCard().getCost();

        for(Map<ResourceName, Resource> cardResource: cardCost) {
            int possibleCost = 0;

            if(cardResource.get(ResourceName.GOLD) != null) {

                possibleCost = cardResource.get(ResourceName.GOLD).getNumOf()+3;
            }

            if(playerGold < possibleCost) {
                return false;
            }
        }

        return true;
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

    @Override
    public void reset() {
        this.familyMember = null;
    }

    public FamilyMember getFamilyMember() {
        return this.familyMember;
    }


    private ServerMessage payCost(Player player, Card card) {
        List <Map<ResourceName,Resource>> cardCost = card.getCost();

        if(cardCost.size() == 1){
            Map<ResourceName,Resource> singleCardCost = cardCost.get(0);
            paySingleCost(singleCardCost, player);

            return null;
        } else if(cardCost.size() == 0) {
            
            return null;
        } else if(numOfCostsCanPay(player, card) == 1) {

            for(Map<ResourceName, Resource> singleCardCost: card.getCost()) {
                if(player.canPayCardCost(singleCardCost, card)) {
                    paySingleCost(singleCardCost, player);
                }
            }

            return null;

        } else {
            CostEffect costEffect = new CostEffect(card);

            ServerMessage request = costEffect.exec(player);
            return request;

        }
    }

    private void paySingleCost(Map<ResourceName, Resource> singleCardCost, Player player) {

        for(Map.Entry<ResourceName,Resource> singleCardCostEntry: singleCardCost.entrySet()){
            ResourceName cardCostName = singleCardCostEntry.getKey();
            int value = singleCardCostEntry.getValue().getNumOf();
            int bonusResourceValue = player.getCardColorBonusResourceValue(this.color, cardCostName);


            value -= bonusResourceValue;

            if(value < 0) value = 0;

            player.getRes().get(cardCostName).subNumOf(value);
        }
    }

    private int numOfCostsCanPay(Player player, Card card) {
        int numOfCosts = 0;

        for(Map<ResourceName, Resource> cardCost: card.getCost()) {
            if(player.canPayCardCost(cardCost, card)) {
                numOfCosts++;
            }
        }

        return numOfCosts;
    }
}
