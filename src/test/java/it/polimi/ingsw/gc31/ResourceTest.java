package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.resources.Resource;
import junit.framework.TestCase;
import org.junit.Test;

public class ResourceTest extends TestCase{
    private Resource resourceToTest;

    @Override
    public void setUp() {
        this.resourceToTest = new Resource(ResourceName.GOLD, 0);
    }

    @Test
    public void testResourceShouldExist() {
        assertNotNull(resourceToTest);
    }

    @Test
    public void testResourceShouldHaveName() {
        assertEquals(ResourceName.GOLD, resourceToTest.getResourceName());
    }

    @Test
    public void testResourceShouldHaveAmount() {
        assertEquals(0, resourceToTest.getNumOf());
    }

    @Test
    public void testResourceShouldDoAddition() {
        int amountToAdd = 1;
        resourceToTest.addNumOf(amountToAdd);

        assertEquals(1, resourceToTest.getNumOf());
    }

    @Test
    public void testResourceShouldDoSubstraction() {
        resourceToTest.setNumOf(3);
        resourceToTest.subNumOf(1);

        assertEquals(2, resourceToTest.getNumOf());
    }

    @Override
    public void tearDown() {
        this.resourceToTest = null;
    }
}
