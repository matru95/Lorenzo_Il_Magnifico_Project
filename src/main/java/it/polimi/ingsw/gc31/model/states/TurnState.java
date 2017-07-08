package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.Tower;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.effects.permanent.FamilyMemberMalus;
import it.polimi.ingsw.gc31.model.effects.permanent.Malus;
import it.polimi.ingsw.gc31.model.effects.permanent.MalusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TurnState implements State {
    private Player[] orderedPlayers;

    @Override
    public void doAction(GameInstance context) {
        Map<CardColor, Tower> towers= context.getGameBoard().getTowers();

        for(Map.Entry<CardColor, Tower> towerEntry: towers.entrySet()) {
            towerEntry.getValue().drawCards();
        }

        this.orderedPlayers = new Player[context.getNumOfPlayers()];
        ArrayList<Player> players = context.getPlayers();

//      Get an array of players ordered by turn order
        this.orderedPlayers = orderPlayerActions(players);

//      Throw the dice

        throwDice(context);

        for(int i=0; i<this.orderedPlayers.length; i++) {
            this.orderedPlayers[i].doPlayerActions();
        }
    }

    public Player[] getOrderedPlayers() {
        return orderedPlayers;
    }

    private Player[] orderPlayerActions(ArrayList<Player> players) {
//      Return the array of playered ordered by their playing order this turn.
        Player[] orderedPlayers = new Player[players.size()];

        for(int i = 0; i< players.size(); i++) {
            int currentOrder = players.get(i).getPlayerOrder();
            orderedPlayers[currentOrder-1] = players.get(i);
        }

        return orderedPlayers;
    }

    private void throwDice(GameInstance context) {

        for (DiceColor color: DiceColor.values()) {
            context.getGameBoard().getDices().get(color).throwDice();

            for(Player currentPlayer: context.getPlayers()) {

                List<Malus> maluses = currentPlayer.getMaluses();
                currentPlayer.getSpecificFamilyMember(color).setValueFromDice();
                int currentValue=0;

                for(Malus malus: maluses){
                    if( malus.getMalusType()== MalusEnum.FAMILYMEMBERMALUS && color != DiceColor.NEUTRAL){

                        int malusValue= ((FamilyMemberMalus) malus).getDiceFewer();
                        currentValue= currentPlayer.getSpecificFamilyMember(color).getValue()-malusValue;
                        //family member set value
                        currentPlayer.getSpecificFamilyMember(color).setValue(currentValue);
                    }
                }
                currentPlayer.getSpecificFamilyMember(color).setValue(currentValue);
            }
        }
    }
}
