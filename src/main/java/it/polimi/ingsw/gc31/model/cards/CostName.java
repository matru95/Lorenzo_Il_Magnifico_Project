package it.polimi.ingsw.gc31.model.cards;


public enum CostName {

    GOLD("gold"),
    STONE("stone"),
    WOOD("wood"),
    SERVANTS("servants"),
    WARPOINTSNEED("warpointsneed"),
    WARPOINTSPAY ("warpointspay");

    private String nameCost;
    CostName(String nameCost){
        this.nameCost=nameCost;
    }
}
