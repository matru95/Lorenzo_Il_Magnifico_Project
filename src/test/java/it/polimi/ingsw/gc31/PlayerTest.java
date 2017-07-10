package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.*;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.effects.permanent.Malus;
import it.polimi.ingsw.gc31.model.resources.Resource;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTest extends TestCase {

    private Player player;
    private Player[] players;
    private GameInstance gameInstance;
    private GameBoard gameBoard;

    @Override
    public void setUp() throws Exception {
        this.player = new Player(UUID.randomUUID(), "Pippo");
        this.players = new Player[1];
        this.players[0] = player;

        this.gameInstance = new GameInstance(UUID.randomUUID());
        this.gameInstance.addPlayer(this.player);
        this.gameBoard = new GameBoard(gameInstance);
        this.gameInstance.setGameBoard(gameBoard);

        this.gameInstance.run();

    }

    @Test
    public void testPlayerShouldHaveOrder() {
        this.player.setPlayerOrder(1);
        assertEquals(this.player.getPlayerOrder(), 1);
    }

    @Test
    public void testPlayerShouldHaveFourFamilyMembers() {

        FamilyMember[] familyMembers = this.player.getFamilyMembers();
        assertEquals(familyMembers.length, 4);
    }

    @Test
    public void testPlayerShouldGetSpecificFamilyMember() {

        FamilyMember familyMember = this.player.getSpecificFamilyMember(DiceColor.BLACK);
        assertNotNull(familyMember);
    }

    @Test
    public void testPlayerShouldHaveName() {

        assertEquals(this.player.getPlayerName(), "Pippo");
    }

    @Test
    public void testPlayerShouldHaveJSON() {
        assertNotNull(player.toJson());
    }

    @Test
    public void testPlayerShouldHaveString() {
        assertNotNull(player.toString());
    }

    @Test
    public void testPlayerShouldAffordCost() {
        Map<ResourceName, Resource> cardCost = new HashMap<>();
        Resource cost = new Resource(ResourceName.GOLD, 1);

        cardCost.put(cost.getResourceName(), cost);
        assertTrue(player.canPayCardCost(cardCost, new Card(CardColor.BLUE, "foo", 1, 1)));
    }

    @Test
    public void testPlayerShouldNotHaveMovedThisTurn() {
        assertFalse(player.getMovedThisTurn());
    }

    @Test
    public void testPlayerShouldHaveBoard() {
        assertNotNull(player.getBoard());
    }

    @Test
    public void testPlayerShouldGetNullCard() {
        assertNull(player.getCardByID(1));
    }

    @Test
    public void testPlayerShouldHaveNoCards() {
        assertEquals(0, player.getAllCardsAsList().size());
    }

    @Override
    public void tearDown() throws Exception {
        this.player = null;
        assertNull(this.player);
    }
}
