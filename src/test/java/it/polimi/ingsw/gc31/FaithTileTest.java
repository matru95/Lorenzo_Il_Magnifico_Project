package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.FaithTile;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.permanent.Malus;
import it.polimi.ingsw.gc31.model.parser.FaithTileParser;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class FaithTileTest extends TestCase{
    FaithTileParser parser;
    Logger logger = Logger.getLogger(this.getClass().getName());
    List<FaithTile> faithTiles;
    FaithTile faithTile;
    Player player;

    @Override
    public void setUp() {
        this.parser = new FaithTileParser("src/config/FaithTile.json");
        parser.parse();
        this.faithTiles = parser.getFaithTiles();
        faithTile = faithTiles.get(0);
        this.player = new Player(UUID.randomUUID(), "Foo");
    }

    @Test
    public void testFaithTileShouldExist() {
        assertNotNull(faithTile);
    }

    @Test
    public void testFaithTileShouldExecute() {
        Malus faithTileMalus = faithTile.getMalus();
        faithTile.execute(player);

        assertEquals(faithTileMalus, player.getMaluses().get(0));
    }

    @Test
    public void testFaithTileShouldHaveID() {
        assertNotNull(faithTile.getId());
    }

    @Test
    public void testFaithTileShouldReturnJSON() {
        assertNotNull(faithTile.toJson());
    }

    @Override
    public void tearDown() {
        faithTile = null;
        faithTiles = null;
    }
}
