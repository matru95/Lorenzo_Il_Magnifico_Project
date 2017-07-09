package it.polimi.ingsw.gc31.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.io.Serializable;
import java.util.List;

public class PlayerTile implements Serializable {

    private List<Resource> harvestBonus, productionBonus;
    private int id;

    /**
     * Constructor of PlayerTile
     * @param harvestBonus List of resourse to give for HarvestBonus
     * @param productionBonus List of resource to give for ProductionBonus
     * @param id
     */
    public PlayerTile(List<Resource> harvestBonus, List<Resource> productionBonus,int id) {
        this.harvestBonus = harvestBonus;
        this.productionBonus = productionBonus;
        this.id=id;
    }

    /**
     * Creating the Json of the Player Tile.
     * @return ObjectNode
     */
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode playerTileNode = mapper.createObjectNode();
        ArrayNode harvestBonusNode = mapper.createArrayNode();
        ArrayNode productionBonusNode = mapper.createArrayNode();

        for(Resource resource: harvestBonus) {

            harvestBonusNode.add(resource.toJson());
        }

        for(Resource resource: productionBonus) {

            productionBonusNode.add(resource.toJson());
        }

        playerTileNode.set("harvestBonus", harvestBonusNode);
        playerTileNode.set("productionBonus", productionBonusNode);
        playerTileNode.put("id", String.valueOf(id));

        return playerTileNode;
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
