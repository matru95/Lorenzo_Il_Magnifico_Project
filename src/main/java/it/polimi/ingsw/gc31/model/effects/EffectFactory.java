package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.List;
import java.util.Map;

public class EffectFactory {
    EffectFactory(){
    }
    //TODO dividere i metodi per diluire i parametri nei getEffect?!?!
    public static Effect getEffect(String effetto, List<Resource> addingres, Map res2choice, Map addingres2choice , int numparchment){
        String control=effetto.toUpperCase();
        switch(control) {

            case "ADDRESOURCE":
                return new AddResEffect(addingres);
            case "PARCHEMENT":
                return new ParchementEffect(numparchment);

            //case "RESOURCEXRESOURCE":
              //  return new effects.ResForResEffect();

            //case "COST":
            //    return new cost(res,addingres,res2choice,addingres2choice);
        }
        return null;
    }
}
