package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.DiceColor;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import org.junit.Test;

import static it.polimi.ingsw.gc31.model.PlayerColor.BLUE;

public class FamilyMemberTest {

    @Test
    public void neutralFamilyMemberShouldBeZero() {
        FamilyMember neutralFamilyMember = new FamilyMember(DiceColor.NEUTRAL, BLUE, new GameBoard());
        int value = neutralFamilyMember.getValue();

        assert("Neutral family member should have a value of 0", value, 0);
    }
}
