package it.polimi.ingsw.gc31.model.resources;

public enum ResourceName {
    GOLD("gold"),
    FAITHPOINTS("faithpoints"),
    SERVANTS("servants"),
    STONE("stone"),
    VICTORYPOINTS("victorypoints"),
    WARPOINTS("warpoints"),
    WOOD("wood");
    String resource;
    ResourceName(String resource){
        this.resource=resource;
    }
}
