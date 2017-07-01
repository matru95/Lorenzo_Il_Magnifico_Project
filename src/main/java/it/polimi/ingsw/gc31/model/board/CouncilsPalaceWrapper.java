package it.polimi.ingsw.gc31.model.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.*;

public class CouncilsPalaceWrapper extends SpaceWrapper {
    private List<FamilyMember> familyMembers;
    private List<Player> players;
    private Resource res;

    public CouncilsPalaceWrapper(int positionID, int diceBond, GameBoard gameBoard, Resource res) {
        super(positionID, diceBond, gameBoard);
        this.familyMembers = new ArrayList<>();
        this.res = res;
        this.players = gameBoard.getGameInstance().getPlayers();
    }

    public Player[] getNewPlayerOrder() {
        Player[] orderedPlayers = new Player[players.size()];
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

                /*
                *  Only the current last player won't change order
                * */
                while (playerOrder < 4) {

                    /**
                     * If there's a player that has a new order that equals playerOrder, increase playerOrder by one.
                     * **/
                    if(orderedPlayers[playerOrder] != null) {
                        playerOrder++;
                    }
                }

                orderedPlayers[playerOrder] = player;
            }
        }

        return orderedPlayers;

    }

    private Set<PlayerColor> getUniqueColors() {
        Set<PlayerColor> uniqueColors = new OrderedHashSet<>();

        for(FamilyMember familyMember: familyMembers) {
            uniqueColors.add(familyMember.getPlayerColor());
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
    public void execWrapper(FamilyMember familyMember , int amountOfServants) {
    }

    @Override
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode councilsPalaceWrapperNode = super.toObjectNode();

        councilsPalaceWrapperNode.set("bonus", res.toJson());

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
    public boolean isAffordable(Map<ResourceName, Resource> playerResources, PlayerColor playerColor) {
        return true;
    }

    @Override
    public void setFamilyMember(FamilyMember familyMember) {

        familyMembers.add(familyMember);
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }
}
