package it.polimi.ingsw.GC_31.model.resources;

public class ResourceDecorator implements Resource {

    protected Resource resource;

    public ResourceDecorator(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void create() {
        this.resource.create();
    }
}
