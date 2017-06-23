package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HarvestWrapper extends SpaceWrapper {
    private List<FamilyMember> familyMembers;
    private boolean isFirstPlayer;
    private boolean isMultiple;
    private int malus;

    public HarvestWrapper(int positionID, int diceBond, boolean isMultiple, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
        this.familyMembers = new ArrayList<>();
        this.isMultiple = isMultiple;
        this.malus = 0;
    }

    @Override
    public void execWrapper(FamilyMember familyMember, int amountOfServants) throws NoResourceMatch {
        harvest(familyMember, amountOfServants);
        if (!isMultiple) setOccupied(true);
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode harvestWrapperNode = super.toObjectNode();

        harvestWrapperNode.put("malus", 0);

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(harvestWrapperNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public boolean isAffordable(Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {

        if(!isMultiple && familyMembers.size() == 1) {
            return false;
        }

//      Check if there's already a FM with the same color
        for(FamilyMember familyMember: familyMembers) {
            if(familyMember.getPlayerColor() == playerColor) {
                return false;
            }
        }
        return true;
     }

    @Override
    public void setFamilyMember(FamilyMember familyMember) {

        familyMembers.add(familyMember);
    }

    private void harvest(FamilyMember familyMember, int amountOfServants) throws NoResourceMatch {
        int familyMemberValue =familyMember.getValue()+amountOfServants;
        List<Card> greenCards=familyMember.getPlayer().getCards().get(CardColor.GREEN);
        //EFFETTO HARVESTTILE ESGUITO UNA SOLA VOLTA
        List <Resource> harvestTileRes=familyMember.getPlayer().getPlayerTile().getHarvestBonus();
        Effect harvestTile = new AddResEffect(harvestTileRes);
        harvestTile.exec(familyMember.getPlayer());

        for (Card singleCard : greenCards){

            if(singleCard.getActivationValue()<=familyMemberValue){
                singleCard.execNormalEffect(familyMember.getPlayer());
            }
        }
    }

    public void setMalus(int malus) {
        this.malus = malus;
    }

    public int getMalus() {
        return this.malus;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        isFirstPlayer = firstPlayer;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }
}