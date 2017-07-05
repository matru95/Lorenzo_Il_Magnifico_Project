package it.polimi.ingsw.gc31.model.effects;


import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.Tower;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.resources.Resource;
import jdk.nashorn.internal.ir.ObjectNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeCardEffect extends Effect  {
    private Card card;
    private List<Tower> towers;

    FreeCardEffect(Card card, List<Tower> towers){
        this.card=card;
        this.towers=towers;
    }

    @Override
    public ServerMessage exec(Player player) throws NoResourceMatch {
        CardColor freecardColor=this.card.getFreeCardChoice().getCardColor();
        int diceActionValue=this.card.getFreeCardChoice().getPoints();
        List<Resource> resources=this.card.getFreeCardChoice().getResources();

        Map<String,String> payload= new HashMap<>();
        for(Tower tower: this.towers){
                if(tower.getTowerColor()==freecardColor){
                    int j=0;
                    for(int i=0;i<3;i++) {
                        j=i+1;
                        if(tower.getTowerSpaces().get(i).getDiceBond()<=diceActionValue)
                            payload.put("ID"+j,String.valueOf(tower.getTowerSpace().get(i).getCard().getCardID()));
                    }
            }
        }
        payload.put("resources",resources.toString());
        ServerMessage request= new ServerMessage(ServerMessageEnum.FREECARDREQUEST,payload);
        return request;
    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
