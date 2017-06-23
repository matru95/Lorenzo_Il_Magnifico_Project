package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductionWrapper extends SpaceWrapper {

    private boolean isFirstPlayer;
    private boolean isMultiple;
    private List<FamilyMember> familyMembers;
    private int malus;

    public ProductionWrapper(int positionID, int diceBond, boolean isMultiple, GameBoard gameBoard) {
        super(positionID, diceBond, gameBoard);
        this.isMultiple = isMultiple;
        familyMembers = new ArrayList<>();
        this.malus = 0;

    }

    @Override
    public void execWrapper(FamilyMember familyMember, int amountOfServants) throws NoResourceMatch {
        produce(familyMember,amountOfServants);
        if (!isMultiple) setOccupied(true);
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode productionWrapperNode = super.toObjectNode();

        productionWrapperNode.put("malus", malus);

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(productionWrapperNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

    }

    private void produce(FamilyMember familyMember, int amountOfServants) throws NoResourceMatch {
        {
            int familyMemberValue = familyMember.getValue() + amountOfServants;
            List<Card> yellowCards = familyMember.getPlayer().getCards().get(CardColor.YELLOW);

            List<Resource> productionTileRes = familyMember.getPlayer().getPlayerTile().getProductionBonus();
            Effect productionTile = new AddResEffect(productionTileRes);
            productionTile.exec(familyMember.getPlayer());

            //TODO DA VERIFICARE LA SCELTA DELL'UTENTE SULL'ESECUZIONE DEGLI EFFETTI DELLE CARTE
            for (Card singleCard : yellowCards) {
                if (singleCard.getActivationValue() <= familyMemberValue) {
                    singleCard.execNormalEffect(familyMember.getPlayer());
                }
            }
        }
    }

    @Override
    public boolean isAffordable(Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {

        if(!isMultiple && familyMembers.size() == 1) {
            return  false;
        }

//      Check if there's already a FM with the same color
        for(FamilyMember familyMember: familyMembers) {
            if(familyMember.getPlayerColor() == playerColor) {
                return false;
            }
        }
        for(FamilyMember familyMember: familyMembers) {
            if(familyMember.getPlayerColor() == playerColor) {
                return false;
            }
        }

        return true;
    }

    public void setFamilyMember(FamilyMember familyMember) {

        familyMembers.add(familyMember);
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        isFirstPlayer = firstPlayer;
    }

    public void setMalus(int malus) {
        this.malus = malus;
    }

    public int getMalus() {
        return this.malus;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }
}
