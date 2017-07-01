package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.parser.FaithPointParser;

import java.util.Map;

public class GameAgeState implements State {

    private Map<Integer, Integer> map;

    @Override
    public void doAction(GameInstance context) {

        map = (new FaithPointParser("/src/config/FaithPoints.json")).parse();


        for(Player p: context.getPlayers()) {

            String choice = "NO";
            /** Implementa un metodo in GameController da chiamare qui per gestire l'invio di un EXCOMMUNICATIONREQUEST
             *  e la ricezione di un EXCOMMUNICATION CHOICE, per poi ritornare una Stringa choice con la scelta "YES"/"NO"
             *  String handleExcommunication(Player) o qualcosa del genere;
             */

            Integer myFaithPoints = p.getRes().get(ResourceName.FAITHPOINTS).getNumOf();

            switch (context.getAge()) {
                case 1: if (choice.equals("NO") && myFaithPoints >=3)
                            payFaithPointsForVictoryPoints(p);
                        else p.getExcommunications().add(context.getGameBoard().getChurch().get(context.getAge()));
                        break;
                case 2: if (choice.equals("NO") && myFaithPoints >=4)
                             payFaithPointsForVictoryPoints(p);
                        else p.getExcommunications().add(context.getGameBoard().getChurch().get(context.getAge()));
                        break;
                case 3: if (myFaithPoints >= 5)
                            payFaithPointsForVictoryPoints(p);
                        else p.getExcommunications().add(context.getGameBoard().getChurch().get(context.getAge()));
                        break;
            }
        }
    }

    private void payFaithPointsForVictoryPoints(Player player) {

        Integer myFaithPoints = player.getRes().get(ResourceName.FAITHPOINTS).getNumOf();

        if (myFaithPoints >= 0 && myFaithPoints < 16)
            player.getRes().get(ResourceName.VICTORYPOINTS).addNumOf(map.get(myFaithPoints));
        else if (myFaithPoints >= 16)
            player.getRes().get(ResourceName.VICTORYPOINTS).addNumOf(map.get(15));

        player.getRes().get(ResourceName.FAITHPOINTS).setNumOf(0);

    }
}
