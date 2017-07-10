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

    /**
     * Constructor of a Resource
     * @param resourceName name of the resource
     * @param numOf quantity of the resource
     */
    public Resource(ResourceName resourceName, int numOf) {
        this.numOf = numOf;
        this.resourceName = resourceName;
    }

    /**
     * Creating the Json for the Resource
     * @return ObjectNode
     */
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

    /**
     * Return the name of the resource
     * @return ResourceName
     */
    public ResourceName getResourceName() {
        return resourceName;
    }

    /**
     * Return the value of the resource
     * @return int
     */
    public int getNumOf() {
        return numOf;
    }

    /**
     * Set the value of the resource
     * @param numOf the value to set
     */
    public void setNumOf(int numOf) {
        this.numOf = numOf;
    }

    /**
     * Add a value 'value' to the value of the resource
     * @param value value to add
     */
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

    /**
     * Sub a value 'value' to the value of the resource
     * @param value value to sub
     */
    public void subNumOf(int value) {
        this.setNumOf(this.getNumOf() - value);
    }
}

