package it.polimi.ingsw.gc31.model.effects;



import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.HashMap;
import java.util.Map;


public class AddResEffect extends Effect{
    private Map addRes;
    AddResEffect(Map addRes){
        this.addRes=addRes;
    }
    @Override
    public void exec(Player player) throws NoResourceMatch {
        Map<String, Resource> startingmap = player.getRes();
        Map<String, Resource> addingmap = this.addRes;
        for (String key : startingmap.keySet()) {
                startingmap.get(key).addNumOf(addingmap.get(key).getNumOf());
        }
        player.setRes(startingmap);
    }

    public Map getAddRes() {
        return addRes;
    }

    public void setAddRes(Map addRes) {
        this.addRes = addRes;
    }
}
