package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.resources.*;
import jdk.nashorn.internal.ir.ObjectNode;


import java.util.HashMap;


public class ParchementEffect extends Effect{

    int numparchment;
    ParchementEffect( int numparchment) {
        this.numparchment=numparchment;
    }

    @Override
    public void exec(Player player) throws NoResourceMatch {

        int exit=0;
        int[] choicenum= new int[this.numparchment];
        String[] choicestring= new String[this.numparchment];

/*//------------------HASHMAP CHE CONTIENE LA DESCRIZIONE DEGLI EFFETTI

        HashMap <String,String> descriptioneffect=new HashMap();
        descriptioneffect.put("Digita 0 per "," 1 x Wood & 1 x Stone");
        descriptioneffect.put("Digita 1 per "," 2 x Servants");
        descriptioneffect.put("Digita 2 per "," 2 x Golds");
        descriptioneffect.put("Digita 3 per "," 2 x Military Points");
        descriptioneffect.put("Digita 4 per "," 1 x Faith Points");
*/
//----------------------------VERO BODY DELL'EXEC IN FOR
        for(exit=0; exit<=this.numparchment;exit++) {

//---------------------------MONTO GLI EFFETTI COME ADDRESOURCE

            HashMap<String, Resource>[] mapsEffects = new HashMap[5];
            // TODO Parsing MAPSEFFECTS
            //Effetto 0

            mapsEffects[0] = new HashMap();
            mapsEffects[0].put("Stone", new Resource(ResourceName.STONE,1));
            mapsEffects[0].put("Wood", new Resource(ResourceName.WOOD,1));
            mapsEffects[0].put("Gold", new Resource(ResourceName.GOLD,1));
            mapsEffects[0].put("Servants", new Resource(ResourceName.SERVANTS,0));
            mapsEffects[0].put("VictoryPoints", new Resource(ResourceName.VICTORYPOINTS,0));
            mapsEffects[0].put("WarPoints", new Resource(ResourceName.WARPOINTS,0));
            mapsEffects[0].put("FaithPoints", new Resource(ResourceName.FAITHPOINTS,0));

            //Effetto 1

            mapsEffects[1] = new HashMap();
            mapsEffects[1].put("Stone", new Resource(ResourceName.STONE,0));
            mapsEffects[1].put("Wood", new Resource(ResourceName.WOOD,0));
            mapsEffects[1].put("Gold", new Resource(ResourceName.GOLD,1));
            mapsEffects[1].put("Servants", new Resource(ResourceName.SERVANTS,2));
            mapsEffects[1].put("VictoryPoints", new Resource(ResourceName.VICTORYPOINTS,0));;
            mapsEffects[1].put("WarPoints", new Resource(ResourceName.WARPOINTS,0));
            mapsEffects[1].put("FaithPoints", new Resource(ResourceName.FAITHPOINTS,0));

            //Effetto 2

            mapsEffects[2] = new HashMap();
            mapsEffects[2].put("Stone", new Resource(ResourceName.STONE,0));
            mapsEffects[2].put("Wood", new Resource(ResourceName.WOOD,0));
            mapsEffects[2].put("Gold", new Resource(ResourceName.GOLD,3));
            mapsEffects[2].put("Servants", new Resource(ResourceName.SERVANTS,0));
            mapsEffects[2].put("VictoryPoints", new Resource(ResourceName.VICTORYPOINTS,0));;
            mapsEffects[2].put("WarPoints", new Resource(ResourceName.WARPOINTS,0));
            mapsEffects[2].put("FaithPoints", new Resource(ResourceName.FAITHPOINTS,0));

            //Effetto 3

            mapsEffects[3] = new HashMap();
            mapsEffects[3].put("Stone", new Resource(ResourceName.STONE,0));
            mapsEffects[3].put("Wood", new Resource(ResourceName.WOOD,0));
            mapsEffects[3].put("Gold", new Resource(ResourceName.GOLD,1));
            mapsEffects[3].put("Servants", new Resource(ResourceName.SERVANTS,0));
            mapsEffects[3].put("VictoryPoints", new Resource(ResourceName.VICTORYPOINTS,0));;
            mapsEffects[3].put("WarPoints", new Resource(ResourceName.WARPOINTS,2));
            mapsEffects[3].put("FaithPoints", new Resource(ResourceName.FAITHPOINTS,0));

            //Effetto 4

            mapsEffects[4] = new HashMap();
            mapsEffects[4].put("Stone", new Resource(ResourceName.STONE,0));
            mapsEffects[4].put("Wood", new Resource(ResourceName.WOOD,0));
            mapsEffects[4].put("Gold", new Resource(ResourceName.GOLD,1));
            mapsEffects[4].put("Servants", new Resource(ResourceName.SERVANTS,0));
            mapsEffects[4].put("VictoryPoints", new Resource(ResourceName.VICTORYPOINTS,0));;
            mapsEffects[4].put("WarPoints", new Resource(ResourceName.WARPOINTS,0));
            mapsEffects[4].put("FaithPoints", new Resource(ResourceName.FAITHPOINTS,1));

//--------------------CREO GLI EFFETTI COME ADD RESOURCE (UN'ISTANZA PER SINGOLO OGGETTO)

            Effect[] effetti = new Effect[5];
            for (int j = 0; j < 5; j++) {
//                effetti[j] = EffectFactory.getEffect("addResource", mapsEffects[j]);
            }
//----------------------------------STAMPO I VALORI DELL'EFFETTO
            System.out.println("Valori dell'EFFETTO:\n");
            for (String key : mapsEffects[choicenum[exit]].keySet()) {
                System.out.println(key + " : " + mapsEffects[choicenum[exit]].get(key).getNumOf());
            }
            System.out.println("Eseguendo l'Effetto... " + choicenum[exit]);
            effetti[choicenum[exit]].exec(player);
            exit++;
        }
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
