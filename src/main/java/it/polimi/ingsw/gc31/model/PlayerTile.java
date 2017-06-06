package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.util.HashMap;
import java.util.Map;

public class PlayerTile {

    Map harvestBonus, productionBonus;

    public PlayerTile(HashMap<ResourceName, Resource> harvestBonus, HashMap<ResourceName, Resource> productionBonus) {
        this.harvestBonus = harvestBonus;
        this.productionBonus = productionBonus;
    }
}
