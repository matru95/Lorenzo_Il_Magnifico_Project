package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.DiceColor;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import org.junit.Test;

import static it.polimi.ingsw.gc31.model.PlayerColor.BLUE;
import static junit.framework.TestCase.assertEquals;

// TODO Make this work
public class FamilyMemberTest {

    @Test
    public void neutralFamilyMemberShouldBeZero() {
        GameBoard testGameBoard = new GameBoard();
        FamilyMember neutralFamilyMember = new FamilyMember(DiceColor.NEUTRAL, BLUE, testGameBoard);
        int value = neutralFamilyMember.getValue();

        assertEquals("Neutral family member should have a value of 0", value, 0);
    }
}
