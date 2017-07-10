package it.polimi.ingsw.gc31.model.states;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.effects.permanent.CardPointsMalus;
import it.polimi.ingsw.gc31.model.effects.permanent.Malus;
import it.polimi.ingsw.gc31.model.effects.permanent.MalusEnum;
import it.polimi.ingsw.gc31.model.effects.permanent.PointsMalus;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameEndState implements State {

    private static final ResourceName WP = ResourceName.WARPOINTS;

    @Override
    public void doAction(GameInstance context) {

        boolean malusGreenCard=true;
        boolean malusBlueCard=true;
        boolean malusPurpleCard=true;
        boolean playerResMalus=true;

        int numOfTotalStoneAndWoodInYellowCard = 0;

        for(Player p: context.getPlayers()) {
            List<Malus> maluses=p.getMaluses();
            //Controllo i Malus dei player

            for(Malus malus: maluses){
                if(malus.getMalusType()== MalusEnum.CARDPOINTSMALUS){
                     CardColor cardColor=((CardPointsMalus) malus).getNoEndGamePointsCardColor();
                     switch (cardColor){
                          case BLUE:
                              malusBlueCard=false;
                          break;
                          case GREEN:
                             malusGreenCard=false;
                             break;
                          case PURPLE:
                             malusPurpleCard=false;
                             break;
                          default:
                              break;
                         }
                    }

                    if(malus.getMalusType()==MalusEnum.POINTSMALUS){
                        //Setto i valori su cui devo lavorare per il POINTSMALUS
                        //forType= nome della risorsa del POINTSMALUS
                        //numFor= numero della risorsa del POINTSMALUS

                        List<Resource> forEveryRes= ((PointsMalus) malus).getForEveryRes();
                        ResourceName forType= forEveryRes.get(0).getResourceName();
                        int numFor= forEveryRes.get(0).getNumOf();

                        int playerResTypeMalus=p.getRes().get(forType).getNumOf();
                        List<Resource> loseEveryRes= ((PointsMalus) malus).getLoseRes();

                        ResourceName loseResType= loseEveryRes.get(0).getResourceName();

                        //PER OGNI 5 VICTORY POINTS --> 1 VICTORY POINT IN MENO
                        if(forType==ResourceName.VICTORYPOINTS){
                            int moduleOp=playerResTypeMalus/numFor;
                            p.getRes().get(loseResType).subNumOf(moduleOp);
                        }

                        //PER OGNI WAR POINTS --> 1 VICTORY POINT IN MENO
                        if(forType==ResourceName.WARPOINTS){
                            p.getRes().get(loseResType).subNumOf(numFor);
                        }
                    }

                    if(malus.getMalusType()==MalusEnum.PLAYERRESOURCEMALUS) playerResMalus=false;

                    if(malus.getMalusType()==MalusEnum.YELLOWCARDSCOSTMALUS){
                        numOfTotalStoneAndWoodInYellowCard=totalOfStoneAndWoodCardCost(p.getCards().get(CardColor.YELLOW));
                        p.getRes().get(ResourceName.VICTORYPOINTS).subNumOf( numOfTotalStoneAndWoodInYellowCard);
                    }
                }

                int num = 0;
                if(malusGreenCard){
                    num += applyGreenCardsPoints(p);
                }
                if(malusBlueCard){
                    num += applyBlueCardsPoints(p);
                }
                if(malusPurpleCard){
                    applyPurpleCardsPoints(p);
                }

                if(playerResMalus) {
                    num += applyResourcesPoints(p);
                }else{
                    num -= applyResourcesPoints(p);
                }

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
    private void applyPurpleCardsPoints(Player player) {
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

    /** Return a int that rappresent the total number of Stone and Wood in a List of cards */

    private int totalOfStoneAndWoodCardCost(List<Card> cards){
        int numOfTotalStoneAndWood=0;
        for(Card card : cards){
            List<Map<ResourceName, Resource>> cardCost=card.getCost();
            for(Map<ResourceName,Resource> cost:cardCost){

                if(cost.containsKey(ResourceName.STONE)) {
                    numOfTotalStoneAndWood += cost.get(ResourceName.STONE).getNumOf();
                }
                if(cost.containsKey(ResourceName.WOOD)){
                    numOfTotalStoneAndWood += cost.get(ResourceName.WOOD).getNumOf();
                }
            }
        }
        return numOfTotalStoneAndWood;
    }
}
