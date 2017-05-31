package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.*;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import junit.framework.TestCase;
import org.junit.Test;

import static it.polimi.ingsw.gc31.model.PlayerColor.BLUE;
import static junit.framework.TestCase.assertEquals;

public class FamilyMemberTest extends TestCase{
    private Player player;
    private GameBoard gameBoard;
    private GameInstance gameInstance;
    private FamilyMember familyMember;

    @Override
    public void setUp() throws Exception{

        this.player = new Player(0, "Pippo", PlayerColor.BLUE);
        Player[] players = new Player[1];
        players[0] = this.player;
        this.gameInstance = new GameInstance(1, players);
        this.gameBoard = new GameBoard(gameInstance);

        this.gameInstance.setGameBoard(gameBoard);

        this.familyMember = new FamilyMember(DiceColor.BLACK, this.player, this.gameBoard);
    }

    @Test
    public void testFamilyMemberShouldExist() {
        assertNotNull(this.familyMember);
    }

    @Test
    public void testFamilyMemberShouldHaveDiceValue() {
        Dice dice = this.gameBoard.getDiceByColor(DiceColor.BLACK);
        assertEquals(dice.getValue(), this.familyMember.getDicePoints());
    }

    @Override
    public void tearDown() throws Exception {
        this.familyMember = null;
        assertNull(this.familyMember);
    }


}
