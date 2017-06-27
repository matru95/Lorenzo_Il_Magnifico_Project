package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

import java.util.ArrayList;
import java.util.List;

public class GameEndState implements State {

    private static final ResourceName WP = ResourceName.WARPOINTS;

    @Override
    public void doAction(GameInstance context) throws NoResourceMatch {

        for(Player p: context.getPlayers()) {

            int num = 0;

            num += applyGreenCardsPoints(p);
            num += applyBlueCardsPoints(p);
            num += applyResourcesPoints(p);
            applyPurpleCardsPoints(p);

            p.getRes().get(ResourceName.VICTORYPOINTS).addNumOf(num);
        }
        applyMilitaryZonePoints(context);
    }

    /** Method that calculate Victory Points of a player
     * taken as parameter, due to the number of GreenCards in his possession.
     * @param player (Player)
     * @return The amount (as an int) of Victory Points
     */
    private int applyGreenCardsPoints(Player player) {
        switch(player.getNumOfCards(CardColor.BLUE)) {
            case 0:
                return 0;
            case 1:
                return 0;
            case 2:
                return 0;
            case 3:
                return 1;
            case 4:
                return 4;
            case 5:
                return 10;
            case 6:
                return 20;
            default:
                throw new NullPointerException();
        }
    }

    /** Method that calculate Victory Points of a player
     * taken as parameter, due to the number of BlueCards in his possession.
     * @param player (Player)
     * @return The amount (as an int) of Victory Points
     */
    private int applyBlueCardsPoints(Player player) {
        switch(player.getNumOfCards(CardColor.BLUE)) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 6;
            case 4:
                return 10;
            case 5:
                return 15;
            case 6:
                return 21;
            default:
                throw new NullPointerException();
        }
    }

    /** Method that calculate Victory Points of a player
     * taken as parameter, due to the number of Resources
     * (Wood, Stone, Gold, Servants) in his possession.
     * @param player (Player)
     * @return The amount (as an int) of Victory Points
     */
    private int applyResourcesPoints(Player player) {
        int totalRes = 0;
        totalRes += player.getRes().get(ResourceName.WOOD).getNumOf();
        totalRes += player.getRes().get(ResourceName.STONE).getNumOf();
        totalRes += player.getRes().get(ResourceName.GOLD).getNumOf();
        totalRes += player.getRes().get(ResourceName.SERVANTS).getNumOf();
        return (totalRes/5);
    }

    /** Method that calculates Victory Points of a player
     * taken as parameter, due to Normal Effects of PurpleCards in his possession.
     * @param player (Player)
     */
    private void applyPurpleCardsPoints(Player player) throws NoResourceMatch {
        List<Card> purpleCards = player.getCards().get(CardColor.PURPLE);
        for(Card purpleCard: purpleCards) {
            for(Effect effect: purpleCard.getNormalEffects()) {

                effect.exec(player);
            }
        }
    }

    /** Method that adds the final Military Bonus
     * to players that deserves it.
     * @param context (GameInstance)
     */
    private void applyMilitaryZonePoints(GameInstance context) {
        ArrayList<Player> players = context.getPlayers();
        players.sort(Player.PlayerWarPointsComparator);
        switch (players.size()) {

            case 2:
                execCode1(players);
                break;

            case 3:

            case 4:
                execCode1(players);
                execCode2(players);
                break;

            default:
                throw new IllegalArgumentException();
        }
    }

    /** Just a method with some code to prevent duplication**/
    private void execCode1(ArrayList<Player> players) {
        if(players.get(0).getRes().get(WP).getNumOf() == players.get(1).getRes().get(WP).getNumOf()) {
            players.get(0).getRes().get(WP).addNumOf(5);
            players.get(1).getRes().get(WP).addNumOf(5);
        } else {
            players.get(0).getRes().get(WP).addNumOf(5);
            players.get(1).getRes().get(WP).addNumOf(2);
        }
    }

    /** Just a method with some code to prevent duplication**/
    private void execCode2(ArrayList<Player> players) {
        if(players.get(1).getRes().get(WP).getNumOf() == players.get(2).getRes().get(WP).getNumOf())
            players.get(2).getRes().get(WP).addNumOf(2);
    }

}
