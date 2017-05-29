package it.polimi.ingsw.gc31.model.effects;



import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.HashMap;
import java.util.Scanner;

public class resourcePerResource extends Effect{
    HashMap <String, Resource> resplayer= new HashMap();
    HashMap <String, Resource> res1= new HashMap();
    HashMap <String, Resource> res2= new HashMap();
    HashMap <String, Resource> restake1= new HashMap();
    HashMap <String, Resource> restake2= new HashMap();
    int ratio1=0;
    int ratio2=0;
    String tipoScambio= "";
    resourcePerResource(HashMap resplayer,HashMap res1, HashMap restake1 , int ratio1, HashMap res2,HashMap restake2, int ratio2,String tipoScambio) {
        this.res1=res1;
        this.restake1=restake1;
        this.ratio1=ratio1;
        this.res2=res2;
        this.restake2=restake2;
        this.ratio2=ratio2;
        this.resplayer=resplayer;
        this.tipoScambio= tipoScambio.toUpperCase();
    }
    //ESEGUO CODICE
    @Override
    public void exec() throws NoResourceMatch {
        //----------------------------RESOURCE PER RESOURCE

        if (this.res2==null && this.tipoScambio=="RXC"){

            //TODO EXEC RESOURCE PER CARD (una volta svolta tutta la card
            // Devo prendere res1, vedere la ratio, e inserire i valori all'interno
        }

        //---------------------------------SCAMBIO SINGOLO

        if(this.res2==null && this.tipoScambio=="SCAMBIOSINGOLO"){

            //TODO EXEC SCAMBIOSINGOLO--> TODO EFFETTO COST.
            //Prendo un effetto cost e un effetto addResource li monto e li eseguo.
            //Effect cost= EffectFactory.getEffect("Cost",,,)
        }

        //-----------------------------SCELTA SCAMBIO MULTIPLO

        //TODO fare printare a schermo le scelte possibili hashmap res1 -> restake1 e hashmap res2->restake2
        int numscelta=0;
        if (this.res2!=null) {
            Scanner input = new Scanner(System.in);
            numscelta = Integer.parseInt(input.nextLine());

            do {

                if (numscelta == 1) {
                    //TODO eseguire la prima scelta
                }

                if (numscelta == 2) {
                    //TODO eseguire la seconda scelta
                }
            } while ((numscelta != 1) || (numscelta != 2));
        }
    }
}
