package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NoResourceMatchTest extends Throwable {
    @Test
    public void testResourceFactoryGetResourceThrowsNoResourceMatch() {
        boolean thrown = false;

        try {
            Resource gold= null;
            gold=ResourceFactory.getResource("Golds",3);
        } catch (it.polimi.ingsw.gc31.model.resources.NoResourceMatch e) {
            thrown = true;
        }

        assertTrue(thrown);
    }
}
