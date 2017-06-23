package it.polimi.ingsw.gc31.model.effects;



import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.util.List;
import java.util.Map;


public class AddResEffect extends Effect{
    private List<Resource> addRes;

    public AddResEffect(List<Resource> addRes){
        this.addRes=addRes;
    }

    @Override
    public void exec(Player player) throws NoResourceMatch {
        Map<ResourceName, Resource> startingmap = player.getRes();
        List<Resource> addingmap = this.addRes;

        //ciclo che prende i valori contenuti nell'addingmap e li somma alle risorse del player
        int i;
        int numResToAdd = addingmap.size();
        for(i=0;i<numResToAdd;i++){
            ResourceName resourceName= addingmap.get(i).getResourceName();
            int value=addingmap.get(i).getNumOf();

            startingmap.get(resourceName).addNumOf(value);
        }
        player.setRes(startingmap);
    }

    public List getAddRes() {
        return addRes;
    }

    public void setAddRes(List addRes) {
        this.addRes = addRes;
    }
}