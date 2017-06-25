package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.*;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.UUID;

public class PlayerTest extends TestCase {

    private Player player;
    private Player[] players;
    private GameInstance gameInstance;
    private GameBoard gameBoard;

    @Override
    public void setUp() throws Exception {
        this.player = new Player(UUID.randomUUID(), "Pippo", PlayerColor.BLUE);
        this.players = new Player[1];
        this.players[0] = player;

        this.gameInstance = new GameInstance(UUID.randomUUID());
        this.gameInstance.addPlayer(this.player);
        this.gameBoard = new GameBoard(gameInstance);

        this.gameInstance.setGameBoard(gameBoard);
        this.player.setGameBoard(gameBoard);

        player.initFamilyMembers();
    }

    @Test
    public void testPlayerShouldHaveOrder() throws NoResourceMatch {
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

    @Override
    public void tearDown() throws Exception {
        System.out.println("Tearing down");
        this.player = null;
        assertNull(this.player);
    }
}
