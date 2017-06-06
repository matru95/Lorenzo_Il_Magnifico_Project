package it.polimi.ingsw.gc31.model.resources;

public class ResourceFactory {

    private ResourceFactory() {    }

    public static Resource getResource(String type, int numOf) {
        return new Resource(ResourceName.valueOf(type.toUpperCase()), numOf);
    }
}