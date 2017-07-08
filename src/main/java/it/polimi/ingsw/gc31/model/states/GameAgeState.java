package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.FaithTile;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.parser.FaithPointParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameAgeState implements State {

    private Map<Integer, Integer> map;
    private Map<String, ServerMessage> serverMessages;
    private FaithTile ageFaithTile;

    @Override
    public void doAction(GameInstance context) {
        serverMessages = new HashMap<>();
        int age = context.getAge();
        ageFaithTile = context.getGameBoard().getFaithTiles().get(age);
        int minimalFaithPoints = age+2;
        map = (new FaithPointParser("/src/config/FaithPoints.json")).parse();


        for(Player p: context.getPlayers()) {
            Integer myFaithPoints = p.getRes().get(ResourceName.FAITHPOINTS).getNumOf();

            if(myFaithPoints < minimalFaithPoints) {
                ageFaithTile.execute(p);
            } else {
                ServerMessage request = new ServerMessage(ServerMessageEnum.EXCOMMUNICATIONREQUEST, new HashMap<>());
                serverMessages.put(p.getPlayerID().toString(), request);
            }

        }
    }

    public Map<String, ServerMessage> getServerMessages() {
        return serverMessages;
    }

    public void payFaithPointsForVictoryPoints(Player player) {

        Integer myFaithPoints = player.getRes().get(ResourceName.FAITHPOINTS).getNumOf();

        if (myFaithPoints >= 0 && myFaithPoints < 16)
            player.getRes().get(ResourceName.VICTORYPOINTS).addNumOf(map.get(myFaithPoints));
        else if (myFaithPoints >= 16)
            player.getRes().get(ResourceName.VICTORYPOINTS).addNumOf(map.get(15));

        player.getRes().get(ResourceName.FAITHPOINTS).setNumOf(0);

    }

    public FaithTile getAgeFaithTile() {
        return ageFaithTile;
    }
}
