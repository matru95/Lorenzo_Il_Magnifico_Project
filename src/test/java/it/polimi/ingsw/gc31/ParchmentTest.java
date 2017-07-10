package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.Parchment;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.resources.Resource;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParchmentTest extends TestCase{
    private Parchment parchment;
    private Player player;

    @Override
    public void setUp() {
        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource(ResourceName.GOLD, 1));
        this.parchment = new Parchment("1", resources);
        this.player = new Player(UUID.randomUUID(), "Foo");
    }

    @Test
    public void testParchmentShouldHaveID() {
        assertNotNull(parchment.getParchmentID());
    }

    @Test
    public void testParchmentShouldHaveResources() {
        assertNotNull(parchment.getResource());
    }

    @Test
    public void testParchmentShouldExecute() {
        player.getRes().get(ResourceName.GOLD).setNumOf(0);
        parchment.execParchment(player);

        assertEquals(1, player.getRes().get(ResourceName.GOLD).getNumOf());
    }

    @Override
    public void tearDown() {
        parchment = null;
        player = null;
    }
}
