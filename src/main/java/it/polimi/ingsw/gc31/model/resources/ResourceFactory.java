package it.polimi.ingsw.gc31.model.resources;

import it.polimi.ingsw.gc31.enumerations.ResourceName;

public class ResourceFactory {

    private ResourceFactory() {    }

    /**
     * Factory Pattern
     * @param type the type of the resource you want to return
     * @param numOf the value of the resource you want to return
     * @return Resource
     */
    public static Resource getResource(String type, int numOf) {
        return new Resource(ResourceName.valueOf(type.toUpperCase()), numOf);
    }

}