package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

import java.util.HashMap;
import java.util.Scanner;

//TODO implementare il controllo nella board o nella classe player o...?
public class CostEffect extends Effect {
    private HashMap res1;
    private HashMap subRes1;
    private HashMap res2;
    private HashMap subRes2;
    private String playerchoice;
    CostEffect(HashMap res1, HashMap subRes1, HashMap res2, HashMap subRes2,String playerchoice){
        this.res1=res1;
        this.res2=res2;
        this.subRes1=subRes1;
        this.subRes2=subRes2;
        this.playerchoice=playerchoice;
    }

    @Override
    public void exec(Player player) throws NoResourceMatch {
//--------------------CREAZIONE OGGETTI NECESSARI PER EXEC

        HashMap<ResourceName, Resource> startingmap1 = this.res1;
        HashMap<ResourceName, Resource> startingmap2 = this.res2;
        HashMap<ResourceName, Resource> subingmap1 = this.subRes1;
        HashMap<ResourceName, Resource> subingmap2 = this.subRes2;
//-------------------CONTROLLO DEL VALORE DELLA SCELTA
        do {
            System.out.println("Digitare 1 per pagare nel primo modo, 2 per pagare nel secondo");
            Scanner input = new Scanner(System.in);
            playerchoice = input.nextLine();
        }while("1".equals(playerchoice) || "2".equals(playerchoice));

//-------------------------ESEGUO IL METODO SCELTO DAL PLAYER

        //PRIMO METODO DI PAGAMENTO
        if(startingmap2==null || "1".equals(playerchoice)) {
            for (ResourceName key : startingmap1.keySet()) {
                startingmap1.get(key).subNumOf(subingmap1.get(key).getNumOf());
            }
            player.setRes(startingmap1);
        }
        //SECONDO METODO DI PAGAMENTO
        if(startingmap2!=null && "2".equals(playerchoice)) {
            for (ResourceName key : startingmap2.keySet()) {
                startingmap2.get(key).subNumOf(subingmap2.get(key).getNumOf());
            }
            player.setRes(startingmap2);
        }
    }
}