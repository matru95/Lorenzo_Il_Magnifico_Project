package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import jdk.nashorn.internal.ir.ObjectNode;

import java.util.List;
import java.util.Map;


public class AddResEffect extends Effect{
    private List<Resource> resourcesToAdd;

    public AddResEffect(List<Resource> resourcesToAdd){
        this.resourcesToAdd = resourcesToAdd;
    }

    @Override
    public ServerMessage exec(Player player) throws NoResourceMatch {
        Map<ResourceName, Resource> playerResources = player.getRes();

        for(Resource resourceToAdd: resourcesToAdd) {
            ResourceName resourceName = resourceToAdd.getResourceName();
            int value = resourceToAdd.getNumOf();

            playerResources.get(resourceName).addNumOf(value);
        }
        return null;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }

    public List getResourcesToAdd() {
        return resourcesToAdd;
    }

    public void setResourcesToAdd(List resourcesToAdd) {
        this.resourcesToAdd = resourcesToAdd;
    }
}