package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.model.effects.AddResEffect;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.List;

public class Parchment {
    private int parchmentID;
    private List<Resource> resources;

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

    public void execParchment(Player player) throws NoResourceMatch {
        AddResEffect addResEffect = new AddResEffect(resources);
        addResEffect.exec(player);
    }
}