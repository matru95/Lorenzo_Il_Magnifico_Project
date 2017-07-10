package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.ProductionWrapper;
import it.polimi.ingsw.gc31.model.resources.Resource;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductionWrapperTest extends TestCase{
    private ProductionWrapper productionWrapper;
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

        productionWrapper = new ProductionWrapper(24, 1, true, gameBoard);
    }

    @Override
    public void tearDown() {
        productionWrapper = null;
        player = null;
    }


    @Test
    public void testProductionWrapperShouldReturnJSON() {
        assertNotNull(productionWrapper.toJson());
    }


    @Test
    public void testProductionWrapperShouldReturnString() {
        assertNotNull(productionWrapper.toString());
    }


    @Test
    public void testProductionWrapperShouldReturnFamilyMembers() {
        assertNotNull(productionWrapper.getFamilyMembers());
    }

    @Test
    public void testProductionWrapperShouldSetMalus() {
        productionWrapper.setMalus(1);
        assertEquals(1, productionWrapper.getMalus());
    }
}
