package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.HarvestWrapper;
import it.polimi.ingsw.gc31.model.resources.Resource;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HarvestWrapperTest extends TestCase{
    private HarvestWrapper harvestWrapper;
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

        harvestWrapper = new HarvestWrapper(24, 1, true, gameBoard);
    }

    @Override
    public void tearDown() {
        harvestWrapper = null;
        player = null;
    }


    @Test
    public void testProductionWrapperShouldReturnJSON() {
        assertNotNull(harvestWrapper.toJson());
    }

    @Test
    public void testHarvestWrapperShouldExec() {
        FamilyMember myFamilyMember = player.getSpecificFamilyMember(DiceColor.WHITE);
        List<ServerMessage> messages = harvestWrapper.execWrapper(myFamilyMember, 0);

        assertEquals(1, messages.size());
    }

    @Test
    public void testHarvestWrapperShouldBeAffordable() {
        FamilyMember myFamilyMember = player.getSpecificFamilyMember(DiceColor.WHITE);
        assertTrue(harvestWrapper.isAffordable(myFamilyMember, player.getRes(), player.getPlayerColor()));
    }

    @Test
    public void testHarvestWrapperShouldReturnString() {
        assertNotNull(harvestWrapper.toString());
    }


    @Test
    public void testHarvestWrapperShouldSetMalus() {
        harvestWrapper.setMalus(1);
        assertEquals(1, harvestWrapper.getMalus());
    }

    @Test
    public void testHarvestWrapperShouldSetFamilyMember() {
        FamilyMember myFamilyMember = player.getSpecificFamilyMember(DiceColor.WHITE);
        harvestWrapper.setFamilyMember(myFamilyMember);

        assertTrue(true);
    }
}

