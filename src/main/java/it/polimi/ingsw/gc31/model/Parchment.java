package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.List;

public class Parchment {
    private int parchmentID;
    private List<Resource> resources;

    /**
     * Constructor of Parchment.
     * @param parchmentID The id of the parchement effect.
     * @param resources the list involved in the cast of the parchement effect.
     */
    public Parchment(String parchmentID, List<Resource> resources) {
        this.parchmentID = Integer.valueOf(parchmentID);
        this.resources = resources;
    }

    public int getParchmentID() {
        return parchmentID;
    }

    public List<Resource> getResource() {
        return resources;
    }

    /**
     * Add resources (taken from the list contained in the Parchement effect) to the player.
     * @param player The player involved in the cast of the Parchement effect
     */
    public void execParchment(Player player) {
        AddResEffect addResEffect = new AddResEffect(resources);
        addResEffect.exec(player);
    }
}
