package it.polimi.ingsw.gc31.view;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Map;

public interface GameViewCtrl {

    /**
     * Method for updating the Client's GameViewCtrl, taking as input a Map
     * which contains JSONs of GameBoard and GameInstance
     * @param gameState: a Map with a String as Key and a String (JSON) as Value
     */
    void update(Map<String, String> gameState) throws IOException;

    /**
     * Method to print a message to notify the fail of a movement.
     * @param gameState: a Map with a String as Key and a String (JSON) as Value
     * @throws IOException
     */
    void movementFail(Map<String, String> gameState);

    /**
     * This method is used to print the query for the player,
     * in order to choose the space in which move the FamilyMember
     * and its color.
     * @return Map where key is String and values are String
     * @throws IOException: Error during input reading.
     */
    Map<String, String> movementQuery() throws IOException;

    /**
     * This method is used to print the query for the player,
     * in order to choose the number of servants to add for
     * an harvest or a production.
     * @param map Map<String, String>
     * @return Map where key is String and values are String
     * @throws IOException: Error during input reading.
     */
    Map<String, String> servantsQuery(Map<String, String> map) throws IOException;

    /**
     * This method is used to print the query for the player,
     * in order to choose the bonus of one or more parchments.
     * @param map Map<String, String>
     * @return Map where key is String and values are String
     * @throws IOException: Error during input reading.
     */
    Map<String, String> parchmentQuery(Map<String, String> map) throws IOException;

    /**
     * This method is used to print the query for the player,
     * in order to choose if accept or not the effect of an excommunication.
     * @return Map where key is String and values are String
     * @throws IOException: Error during input reading.
     */
    Map<String, String> faithQuery() throws IOException;

    /**
     * This method is used to print the query for the player,
     * in order to choose which cost to pay to take a purple card.
     * @param map Map<String, String>
     * @return Map where key is String and values are String
     * @throws IOException: Error during input reading.
     */
    Map<String, String> costQuery(Map<String, String> map) throws IOException;

    /**
     * This method is used to print the query for the player,
     * in order to choose whether or not activate an exchange effect
     * of a YellowCard during a "produce" action.
     * @param map Map<String, String>
     * @return Map where key is String and values are String
     * @throws IOException: Error during input reading.
     */
    Map<String, String> exchangeQuery(Map<String, String> map) throws IOException;

    /**
     * This method is used to print the query for the player,
     * in order to choose which card you'd like to pick
     * (this is due to a BLUE CARD's instantEffect).
     * @param map Map<String, String>
     * @return Map where key is String and values are String
     * @throws IOException: Error during input reading.
     */
    Map<String, String> freeCardQuery(Map<String, String> map) throws IOException;

    /**
     * This method is used to print the query for the player,
     * in order to print the winner of the game.
     * @param map Map<String, String>
     * @throws IOException: Error during input reading.
     */
    void endGameQuery(Map<String, String> map) throws IOException;

    /**
     * This method is used to print a timeout message
     */
    void timeoutAlert();

    /**
     * Setter for playerID attribute
     * @param playerID
     */
    void setPlayerID(String playerID);

}
