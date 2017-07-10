package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.NullWrapper;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class NullWrapperTest extends TestCase{
    private NullWrapper nullWrapper;

    @Override
    public void setUp() {
        this.nullWrapper = new NullWrapper();

    }

    @Test
    public void testNullWrapperShouldExec() {
        List<ServerMessage> messages = nullWrapper.execWrapper(null, 0);
        assertNull(messages);
    }

    @Test
    public void testNullWrapperShouldReturnJSON() {
        assertNull(nullWrapper.toJson());
    }

    @Test
    public void testNullWrapperShouldReturnString() {
        assertEquals("", nullWrapper.toString());
    }

    @Test
    public void testNullWrapperShouldNotBeAffordable() {
        assertFalse(nullWrapper.isAffordable(null, null, null));
    }

    @Override
    public void tearDown() {
        this.nullWrapper = null;
    }
}
