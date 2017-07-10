package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.MartWrapper;
import it.polimi.ingsw.gc31.model.resources.Resource;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MartWrapperTest extends TestCase{
    private MartWrapper martWrapper;
    private Player player;

    @Override
    public void setUp() throws IOException {
        GameInstance gameInstance = new GameInstance(UUID.randomUUID());
        GameBoard gameBoard = new GameBoard(gameInstance);
        gameInstance.setGameBoard(gameBoard);
        this.player = new Player(UUID.randomUUID(), "Foo");
        gameInstance.addPlayer(player);

        gameInstance.run();

        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource(ResourceName.GOLD, 1));

        martWrapper = new MartWrapper(24, 1, gameBoard, resources);

    }

    @Test
    public void testMartWrapperShouldExec() {
        FamilyMember myFamilyMember = player.getSpecificFamilyMember(DiceColor.WHITE);
        int currentPlayerGold = player.getRes().get(ResourceName.GOLD).getNumOf();
        martWrapper.execWrapper(myFamilyMember, 0);
        assertEquals(currentPlayerGold+1, player.getRes().get(ResourceName.GOLD).getNumOf());
    }

    @Test
    public void testMartWrapperShouldReturnJSON() {
        assertNotNull(martWrapper.toJson());
    }

    @Test
    public void testMartWrapperShouldReturnString() {
        assertNotNull(martWrapper.toString());
    }

    @Test
    public void testMartWrapperShouldBeAffordable() {
        FamilyMember myFamilyMember = player.getSpecificFamilyMember(DiceColor.WHITE);
        assertTrue(martWrapper.isAffordable(myFamilyMember, player.getRes(), player.getPlayerColor()));
    }

    @Test
    public void testMartWrapperShouldSetFamilyMember() {
        FamilyMember myFamilyMember = player.getSpecificFamilyMember(DiceColor.WHITE);
        martWrapper.setFamilyMember(myFamilyMember);
        assertNotNull(martWrapper.getFamilyMember());
    }

    public void tearDown() {
        martWrapper = null;
    }

}
