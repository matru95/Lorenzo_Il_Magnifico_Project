package it.polimi.ingsw.gc31.model.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Resource {

    private ResourceName resourceName;
    private int numOf;
    private final Logger logger = Logger.getLogger(Resource.class.getName());

    public Resource(ResourceName resourceName, int numOf) {
        this.numOf = numOf;
        this.resourceName = resourceName;
    }

    public ResourceName getResourceName() {
        return resourceName;
    }

    public int getNumOf() {
        return numOf;
    }

    public void setNumOf(int numOf) {
        this.numOf = numOf;
    }

    public void addNumOf(int value) {
        try {
            if (value > 0) {
                this.setNumOf(this.getNumOf() + value);
            }
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, e.toString(), e);
            logger.log(Level.SEVERE,"Value must be greater then zero");
        }
    }

    public void subNumOf(int value) {
        try {
            if (value > 0 && value < this.getNumOf()) {
                this.setNumOf(this.getNumOf() - value);
            }
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, e.toString(), e);
            logger.log(Level.SEVERE, "Value must be greater then zero and smaller then actual value");
        }
    }
}

