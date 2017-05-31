package it.polimi.ingsw.gc31.model.effects;



import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.HashMap;
import java.util.Map;


public class AddResEffect extends Effect{
    private Map res;
    private Map addRes;
    AddResEffect(Map res, Map addRes){
        this.res=res;
        this.addRes=addRes;
    }
    @Override
    public void exec() throws NoResourceMatch {
        Map<String, Resource> startingmap = this.res;
        Map<String, Resource> addingmap = this.addRes;
        for (String key : startingmap.keySet()) {
                startingmap.get(key).addNumOf(addingmap.get(key).getNumOf());
        }
        this.res=startingmap;
    }
    @Override
    public Map getRes() {
        return this.res;
    }

    public void setRes(Map res) {
        this.res = res;
    }

    public Map getAddRes() {
        return addRes;
    }

    public void setAddRes(Map addRes) {
        this.addRes = addRes;
    }
}
