package it.polimi.ingsw.gc31.model.effects;

import java.util.HashMap;
import java.util.Map;

public class EffectFactory {
    EffectFactory(){
    }
    //TODO dividere i metodi per diluire i parametri nei getEffect?!?!
    public static Effect getEffect(String effetto, Map res, Map addingres, int numparchment){
        String control=effetto.toUpperCase();
        switch(control) {
            //TODO Da inserire i res e gli adding res direttamente nella creazione della carta
            case "ADDRESOURCE":
                return new AddResFX(res,addingres);
            case "PARCHEMENT":
                return new ParchementFX(res,numparchment);

            //case "RESOURCEXRESOURCE":
              //  return new effects.ResForResFX();

            //case "COST":
              //  return new effects.cost();
        }
        return null;
    }
}