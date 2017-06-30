package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.parser.FaithPointParser;

import java.util.Map;

public class GameAgeState implements State {

    @Override
    public void doAction(GameInstance context) {

        for(Player p: context.getPlayers()) {

            Integer myFaithPoints = p.getRes().get(ResourceName.FAITHPOINTS).getNumOf();
            String choice = "YES";

            FaithPointParser parser = new FaithPointParser("/src/config/FaithPoints.json");
            parser.parse();
            Map<Integer, Integer> map = parser.getFaithPoints();

            if (choice.equals("YES"))
                if (myFaithPoints >= 0 && myFaithPoints < 16)
                    p.getRes().get(ResourceName.VICTORYPOINTS).addNumOf(map.get(myFaithPoints));
                else if (myFaithPoints >= 16)
                    p.getRes().get(ResourceName.VICTORYPOINTS).addNumOf(map.get(15));
            else if (choice.equals("NO"));



        }
    }
}
