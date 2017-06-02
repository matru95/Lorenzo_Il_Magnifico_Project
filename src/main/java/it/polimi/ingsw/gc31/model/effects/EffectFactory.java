package it.polimi.ingsw.gc31.model.effects;

import java.util.Map;

public class EffectFactory {
    EffectFactory(){
    }
    //TODO dividere i metodi per diluire i parametri nei getEffect?!?!
    public static Effect getEffect(String effetto, Map addingres, Map res2choice, Map addingres2choice ,int numparchment){
        String control=effetto.toUpperCase();
        switch(control) {
            //TODO Da inserire i res e gli adding res direttamente nella creazione della carta
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
