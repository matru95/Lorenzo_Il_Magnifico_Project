package it.polimi.ingsw.GC_31;

import it.polimi.ingsw.GC_31.model.resources.Resource;
import it.polimi.ingsw.GC_31.model.resources.ResourceFactory;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FaithPointsTest {
    @Test
    public void faithPointsShouldHaveInitialNumOf() {
        Resource faithPointsTester = ResourceFactory.getResource("FaithPoints", 5);
        assertEquals("FaithPoints should be 5", faithPointsTester.getNumOf(), 5);
    }

    @Test
    public void faithPointsShouldBeAdded() {
        Resource faithPointsTester = ResourceFactory.getResource("FaithPoints", 5);
        faithPointsTester.addNumOf(2);
        assertEquals("FaithPoints should be 7", faithPointsTester.getNumOf(), 7);
    }

    @Test
    public void faithPointsShouldBeSubstracted() {
        Resource faithPointsTester = ResourceFactory.getResource("FaithPoints", 5);
        faithPointsTester.subNumOf(2);
        assertEquals("FaithPoints should be 3", faithPointsTester.getNumOf(), 3);
    }
}
