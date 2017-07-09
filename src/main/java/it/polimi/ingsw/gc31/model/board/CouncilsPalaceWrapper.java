package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.effects.ParchmentEffect;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.*;

public class CouncilsPalaceWrapper extends SpaceWrapper {
    private List<FamilyMember> familyMembers;
    private List<Player> players;
    private Resource res;
    private int numOfParchments;

    public CouncilsPalaceWrapper(int positionID, int diceBond, GameBoard gameBoard, Resource res, int numOfParchments) {
        super(positionID, diceBond, gameBoard);
        this.familyMembers = new ArrayList<>();
        this.res = res;
        this.players = gameBoard.getGameInstance().getPlayers();
        this.numOfParchments = numOfParchments;
    }

    public Player[] getNewPlayerOrder() {
        Player[] orderedPlayers = new Player[getGameBoard().getGameInstance().getNumOfPlayers()];
        Set<PlayerColor> uniqueColors = getUniqueColors();
        int index = 0;

//      Re-order players that had a family member here
        for(PlayerColor playerColor: uniqueColors) {

            for(Player player: players) {
                if(player.getPlayerColor().equals(playerColor)) {
                    orderedPlayers[index] = player;
                }
            }
            index++;
        }

        for(Player player: players) {

            if(!uniqueColors.contains(player.getPlayerColor())) {
                /**
                 * Hasn't changed order
                 * **/

                /*
                * Current player order
                * */
                int playerOrder = player.getPlayerOrder();
                boolean condition = true;

                /*
                *  Only the current last player won't change order
                * */
                while (playerOrder <= getGameBoard().getGameInstance().getNumOfPlayers() && condition) {

                    if(orderedPlayers[playerOrder-1] == null) {
                        orderedPlayers[playerOrder-1] = player;
                        condition = false;
                    } else {
                        playerOrder++;
                    }
                }
            }
        }

        return orderedPlayers;

    }

    private Set<PlayerColor> getUniqueColors() {
        Set<PlayerColor> uniqueColors = new OrderedHashSet<>();

        for(FamilyMember familyMember: familyMembers) {
            if(familyMember!=null){
                uniqueColors.add(familyMember.getPlayerColor());
            }
        }

        return uniqueColors;
    }

    private List<Player> orderPlayers() {
        List<Player> orderedPlayers = new ArrayList<>();

        for(int i=1; i<=players.size(); i++) {
            for(Player player: players) {
                if(player.getPlayerOrder() == i) {
                    orderedPlayers.add(player);
                }
            }
        }

        return orderedPlayers;
    }

    @Override
    public List<ServerMessage> execWrapper(FamilyMember familyMember, int amountOfServants) {
        List<ServerMessage> result = new ArrayList<>();

        ParchmentEffect parchmentEffect = new ParchmentEffect(numOfParchments);
        List<Resource> resources = new ArrayList<>();
        resources.add(this.res);
        AddResEffect addResEffect = new AddResEffect(resources);

        addResEffect.exec(familyMember.getPlayer());


        ServerMessage message = parchmentEffect.exec(familyMember.getPlayer());
        result.add(message);

        return result;
    }

    @Override
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode councilsPalaceWrapperNode = super.toObjectNode();

        councilsPalaceWrapperNode.set("bonus", res.toJson());

        ArrayNode arrayNode = mapper.createArrayNode();

        for(FamilyMember familyMember: familyMembers) {
            if(familyMember != null) {
                arrayNode.add(familyMember.toJson());
            }
        }

        councilsPalaceWrapperNode.set("familyMembers", arrayNode);

        return councilsPalaceWrapperNode;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode councilsPalaceWrapperNode = toJson();

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(councilsPalaceWrapperNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

    }

    @Override
    public boolean isAffordable(FamilyMember familyMember, Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {
        int servants = familyMember.getPlayer().getRes().get(ResourceName.SERVANTS).getNumOf();
        int familyMemberValue = familyMember.getValue();
        int finalValue = servants+familyMemberValue;

        if(this.getDiceBond() > finalValue) return false;

        return true;
    }

    @Override
    public void setFamilyMember(FamilyMember familyMember) {

        if(familyMember == null) {
            familyMembers = new ArrayList<>();
        }

        familyMembers.add(familyMember);
    }

    @Override
    public void reset() {
        this.familyMembers.clear();
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }
}
