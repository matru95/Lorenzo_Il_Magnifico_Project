package it.polimi.ingsw.gc_31;

import it.polimi.ingsw.gc_31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc_31.model.resources.Resource;
import it.polimi.ingsw.gc_31.model.resources.ResourceFactory;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FaithPointsTest {
    @Test
    public void faithPointsShouldHaveInitialNumOf() {
        Resource faithPointsTester = null;
        try {
            faithPointsTester = ResourceFactory.getResource("FaithPoints", 5);
        } catch (NoResourceMatch noResourceMatch) {
            noResourceMatch.printStackTrace();
        }
        assertEquals("FaithPoints should be 5", faithPointsTester.getNumOf(), 5);
    }

    @Test
    public void faithPointsShouldBeAdded() {
        Resource faithPointsTester = null;
        try {
            faithPointsTester = ResourceFactory.getResource("FaithPoints", 5);
        } catch (NoResourceMatch noResourceMatch) {
            noResourceMatch.printStackTrace();
        }
        faithPointsTester.addNumOf(2);
        assertEquals("FaithPoints should be 7", faithPointsTester.getNumOf(), 7);
    }

    @Test
    public void faithPointsShouldBeSubstracted() {
        Resource faithPointsTester = null;
        try {
            faithPointsTester = ResourceFactory.getResource("FaithPoints", 5);
        } catch (NoResourceMatch noResourceMatch) {
            noResourceMatch.printStackTrace();
        }
        faithPointsTester.subNumOf(2);
        assertEquals("FaithPoints should be 3", faithPointsTester.getNumOf(), 3);
    }
}
