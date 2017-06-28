//package it.polimi.ingsw.gc31.controller;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import it.polimi.ingsw.gc31.enumerations.DiceColor;
//import it.polimi.ingsw.gc31.messages.*;
//import it.polimi.ingsw.gc31.model.FamilyMember;
//import it.polimi.ingsw.gc31.model.GameInstance;
//import it.polimi.ingsw.gc31.model.Player;
//import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
//import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
//import it.polimi.ingsw.gc31.view.client.Client;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//public class GameActionController extends Controller{
//    public GameActionController(GameInstance model, List<Client> views) {
//        super(model, views);
//    }
//
//    @Override
//    public void updateClients(Message request) {
//        return;
//    }
//
//    public void movementAction(String movementData) throws NoResourceMatch, IOException {
//        Message request = null;
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode rootNode = mapper.readTree(movementData);
//        BasicMessage basicRequest = new BasicMessage();
//
//        String playerID = rootNode.path("playerID").toString();
//        String familyMemberColor = rootNode.path("familyMemberColor").toString();
//        String positionID = rootNode.path("positionID").toString();
//
//        GameInstance game = super.getModel();
//
//        Player player = game.getPlayerFromId(UUID.fromString(playerID));
//
//        DiceColor realFamilyMemberColor = DiceColor.valueOf(familyMemberColor);
//        FamilyMember familyMember = player.getSpecificFamilyMember(realFamilyMemberColor);
//
//        List<SpaceWrapper> possibleMovements = familyMember.checkPossibleMovements();
//
//        if(isMovementValid(positionID, possibleMovements)) {
//            SpaceWrapper position = game.getGameBoard().getSpaceById(Integer.valueOf(positionID));
//            Integer servantsToPay = rootNode.path("servantsToPay").asInt();
//
//            familyMember.moveToPosition(position, servantsToPay);
//
////          Create message for client
//            basicRequest.setRequestType(RequestType.ACTION);
//            request = new ActionMessage(basicRequest, ActionType.UPDATE);
//        } else {
//
//            basicRequest.setRequestType(RequestType.FAIL);
//            request = new FailMessage(basicRequest, FailType.MOVEMENTNOTPOSSIBLE);
//        }
//
//        updateClients(request);
//    }
//
//    private boolean isMovementValid(String positionID, List<SpaceWrapper> possibleMovements) {
//        for(SpaceWrapper possibleMovement: possibleMovements) {
//            if(possibleMovement.getPositionID() == Integer.valueOf(positionID)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//}
