package it.polimi.ingsw.gc31.model.effects;


import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.Tower;
import it.polimi.ingsw.gc31.model.board.TowerSpaceWrapper;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.resources.Resource;
import jdk.nashorn.internal.ir.ObjectNode;
import sun.net.ProgressSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeCardEffect extends Effect  {
    private CardColor freeCardColor;
    private int diceActionValue;
    private List<Resource> resources;

    public FreeCardEffect(CardColor freeCardColor, int diceActionValue, List<Resource> resources){
        this.freeCardColor = freeCardColor;
        this.diceActionValue = diceActionValue;
        this.resources = resources;
    }

    @Override
    public ServerMessage exec(Player player) throws NoResourceMatch {
        Map<CardColor, Tower> towers = player.getBoard().getTowers();
        Map<String,String> payload = new HashMap<>();
        List<Card> towerCards = new ArrayList<>();
        List<Card> towerCardsFiltered = new ArrayList<>();

        if(freeCardColor!=null){
            Tower tower= towers.get(this.freeCardColor);

            for (int i=0;i<3;i++){
                int j=i+1;
                if(tower.getTowerSpaces().get(i).getDiceBond()<=this.diceActionValue){
                    towerCards.add(j,tower.getTowerSpace().get(i).getCard());
                }
            }

            towerCardsFiltered=cardAffordable(player,towerCards);
            for(int i=0;i<towerCardsFiltered.size();i++) {
                int j=i+1;
                payload.put("ID"+j,String.valueOf(towerCardsFiltered.get(i).getCardID()));
            }

            return new ServerMessage(ServerMessageEnum.FREECARDREQUEST,payload);
        } else {
            Map<Integer,TowerSpaceWrapper> towerSpaceWrapper= new HashMap<>();
            for(Map.Entry<CardColor,Tower> tower: towers.entrySet()){
                towerSpaceWrapper=tower.getValue().getTowerSpace();
                for (int i=0;i<3;i++) {
                    int j=i+1;
                    if(towerSpaceWrapper.get(i).getDiceBond()<this.diceActionValue)
                        towerCards.add(j,towerSpaceWrapper.get(i).getCard());
                }
            }
            towerCardsFiltered=cardAffordable(player,towerCards);
            for(int i=0;i<towerCardsFiltered.size();i++) {
                int j=i+1;
                payload.put("ID"+j,String.valueOf(towerCardsFiltered.get(i).getCardID()));
            }

            return new ServerMessage(ServerMessageEnum.FREECARDREQUEST,payload);
        }
    }
    private List<Card> cardAffordable(Player player, List<Card> towerCards){
        Map <ResourceName,Resource> playerRes =player.getRes();
        List<Resource> sales=this.resources;
        for(Resource resourceToAdd: sales) {
            ResourceName resourceName = resourceToAdd.getResourceName();
            int value = resourceToAdd.getNumOf();
            playerRes.get(resourceName).addNumOf(value);
        }
        //ORA IN PLAYERRES HO TUTTI I VALORI AGGIORNATI PER FARE IL CONTROLLO
        //DEVO QUINDI ORA FARE IL CONTROLLO ITERANDO I COSTI DELLE CARTE E CONTROLLANDO CHE IL VALORE DELLE
        for(Card towerCard : towerCards) {
            //se la carta ha il doppio costo

            //controllo che il costo della carta non sia nullo per evitare NullPointerEx
            if(towerCard.getCost()!=null) {

            //carta a DOPPIO COSTO
                List<Map<ResourceName, Resource>> cost = towerCard.getCost();
                if (towerCard.getCost().size() == 2) {
                    //INIZIALIZZO UN'ARRAY DI INT PER VEDERE SE IL PRIMO E IL SECONDO COSTO SONO ATTUABILI.
                    //ALLA FINE DEL CICLO SUI COSTI DI UNA CARTA (if(i==1)) CONTROLLO I VALORI IMMESSI NELL'ARRAY
                    //QUANDO ANALIZZO I COSTI E LE RISORSE DEL PLAYER
                    int[] validCostFound= new int[2];
                    for (int i=0; i<=1; i++){
                        Map<ResourceName, Resource> costMap = cost.get(i);

                        for (Map.Entry<ResourceName, Resource> singleCost : costMap.entrySet()) {
                            ResourceName resourceName = singleCost.getKey();
                            int resourceCost = singleCost.getValue().getNumOf();

                            if (playerRes.get(resourceName).getNumOf() < resourceCost && towerCards.contains(towerCard)) {
                                validCostFound[i]=0;
                            }else{
                                validCostFound[i]=1;
                            }
                            if(i==1){
                                int checkNumOfValidCost=0;
                                for(int j=0 ;j<=1;j++){
                                    if (validCostFound[j]==1){
                                        checkNumOfValidCost++;
                                    }
                                }
                                if (checkNumOfValidCost==0){
                                    towerCards.remove(towerCard);
                                }
                            }
                        }

                    }

                }
                else {


            //carta a SINGOLO COSTO
                    Map<ResourceName, Resource> costMap1 = cost.get(0);

                    for (Map.Entry<ResourceName, Resource> singleCost : costMap1.entrySet()) {

                        ResourceName resourceName = singleCost.getKey();
                        int resourceCost = singleCost.getValue().getNumOf();

                        if (playerRes.get(resourceName).getNumOf() < resourceCost && towerCards.contains(towerCard)) {
                            towerCards.remove(towerCard);
                        }
                    }

                }
            }
        }
        return towerCards;
    }
    @Override
    public ObjectNode toJson() {
        return null;
    }
}
