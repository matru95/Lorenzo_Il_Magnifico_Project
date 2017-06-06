package it.polimi.ingsw.gc31.model.resources;

public enum ResourceName {
    GOLD("Gold"),
    FAITHPOINTS("Faith Points"),
    SERVANTS("Servants"),
    STONE("Stone"),
    VICTORYPOINTS("Victory Points"),
    WARPOINTS("War Points"),
    WOOD("Wood");

    String resource;

    ResourceName(String resource){
        this.resource = resource;
    }

    @Override
    public String toString() {
        return resource;
    }
}
