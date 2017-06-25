package it.polimi.ingsw.gc31.model.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Resource implements Serializable{

    private ResourceName resourceName;
    private int numOf;
    private transient Logger logger = Logger.getLogger(Resource.class.getName());

    public Resource(ResourceName resourceName, int numOf) {
        this.numOf = numOf;
        this.resourceName = resourceName;
    }

    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resourceNode = mapper.createObjectNode();

        resourceNode.put("resourceName", resourceName.toString());
        resourceNode.put("amount", numOf);

        return resourceNode;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resourceNode = toJson();

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resourceNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
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

