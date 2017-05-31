package it.polimi.ingsw.gc31.model.effects;


import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//TODO implementare il controllo nella board o nella classe player o...?
public class CostFX extends Effect {
    private Map res1;
    private Map subRes1;
    private Map res2;
    private Map subRes2;
    CostFX(Map res1, Map subRes1, Map res2, Map subRes2){
        this.res1=res1;
        this.res2=res2;
        this.subRes1=subRes1;
        this.subRes2=subRes2;
    }
    @Override
    public void exec() throws NoResourceMatch {
//--------------------CREAZIONE OGGETTI NECESSARI PER EXEC

        Map<String, Resource> startingmap1 = this.res1;
        Map<String, Resource> startingmap2 = this.res2;
        Map<String, Resource> subingmap1 = this.subRes1;
        Map<String, Resource> subingmap2 = this.subRes2;
        String playerchoice;


//-------------------------ESEGUO IL METODO SCELTO DAL PLAYER

        //PRIMO METODO DI PAGAMENTO
        if(startingmap2==null) {
            //TODO dare una sistemata alle iterazioni (non for ma "entrySet")[da vedere anche nelle altre classi]
            for (String key : startingmap1.keySet()) {
                startingmap1.get(key).subNumOf(subingmap1.get(key).getNumOf());
            }
            this.res1 = startingmap1;
        }
        //SECONDO METODO DI PAGAMENTO
        if(startingmap2!=null) {//-------------------CONTROLLO DEL VALORE DELLA SCELTA

                System.out.println("Digitare 1 per pagare nel primo modo, 2 per pagare nel secondo");
                Scanner input = new Scanner(System.in);
                playerchoice = input.nextLine();

            if("1".equals(playerchoice)) {
                for (String key : startingmap1.keySet()) {
                    startingmap1.get(key).subNumOf(subingmap1.get(key).getNumOf());
                }
                this.res1 = startingmap1;
            }
            if("2".equals(playerchoice)) {
                for (String key : startingmap2.keySet()) {
                    startingmap2.get(key).subNumOf(subingmap2.get(key).getNumOf());
                }
                this.res1 = startingmap2;
            }
        }
    }
    @Override
    public Map getRes() {
        return this.res1;
    }
}