package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.model.resources.Resource;

import java.io.Serializable;
import java.util.List;

public class PlayerTile implements Serializable {

    List<Resource> harvestBonus, productionBonus;
    int id;
    public PlayerTile(List<Resource> harvestBonus, List<Resource> productionBonus,int id) {
        this.harvestBonus = harvestBonus;
        this.productionBonus = productionBonus;
        this.id=id;
    }

    public List<Resource> getHarvestBonus() {
        return harvestBonus;
    }

    public List<Resource> getProductionBonus() {
        return productionBonus;
    }

    public int getId() {
        return id;
    }
}
