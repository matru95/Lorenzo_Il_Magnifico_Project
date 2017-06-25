package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.view.client.Client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameActionController extends Controller{
    public GameActionController(GameInstance model, List<Client> views) {
        super(model, views);
    }

    @Override
    public void updateClients(Map<String, String> response) {
        return;
    }

    public void movementAction(String movementData) throws NoResourceMatch {
        Map<String, String> response = new HashMap<>();
        String playerID = movementData.get("playerID");
        String familyMemberColor = movementData.get("familyMemberColor");
        String positionID = movementData.get("positionID");

        GameInstance game = super.getModel();
        Player player = game.getPlayerFromId(UUID.fromString(playerID));
        FamilyMember familyMember = player.getSpecificFamilyMember(DiceColor.valueOf(familyMemberColor));
        List<SpaceWrapper> possibleMovements = familyMember.checkPossibleMovements();

        if(isMovementValid(positionID, possibleMovements)) {
            SpaceWrapper position = game.getGameBoard().getSpaceById(Integer.valueOf(positionID));
            Integer servantsToPay = Integer.valueOf(movementData.get("servantsToPay"));

            familyMember.moveToPosition(position, servantsToPay);
            response.put("responseType", "action");
            response.put("actionType", "update");
        } else {
            response.put("responseType", "fail");
            response.put("failMessage", "{\"message\": \"Cannot move there \"}");
        }

        updateClients(response);
    }

    private boolean isMovementValid(String positionID, List<SpaceWrapper> possibleMovements) {
        for(SpaceWrapper possibleMovement: possibleMovements) {
            if(possibleMovement.getPositionID() == Integer.valueOf(positionID)) {
                return true;
            }
        }

        return false;
    }

}
