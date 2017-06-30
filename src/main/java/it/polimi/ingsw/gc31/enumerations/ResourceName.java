package it.polimi.ingsw.gc31.enumerations;

public enum ResourceName {

    GOLD("GOLD"),
    FAITHPOINTS("FAITHPOINTS"),
    SERVANTS("SERVANTS"),
    STONE("STONE"),
    VICTORYPOINTS("VICTORYPOINTS"),
    WARPOINTS("WARPOINTS"),
    WOOD("WOOD");

    String resourceString;

    ResourceName(String resourceString){
        this.resourceString = resourceString;
    }

    @Override
    public String toString() {
        return resourceString;
    }

}
