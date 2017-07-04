package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import jdk.nashorn.internal.ir.ObjectNode;

import java.util.Map;

public class MultiplierEffect extends Effect{
    private Map<String, Object> multiplier;

    public MultiplierEffect(Map<String, Object> multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public ServerMessage exec(Player player) throws NoResourceMatch {
        Resource resourceToReceive = (Resource) multiplier.get("receive");

        if(multiplier.get("for").getClass() == CardColor.class) {
            CardColor colorFor = (CardColor) multiplier.get("for");
            int numOfCards = player.getNumOfCards(colorFor);

        } else {
//          Resource
            ResourceName resourceFor = (ResourceName) multiplier.get("for");
            int numOfResource = player.getRes().get(resourceFor).getNumOf();
        }
        return null;

    }

    @Override
    public ObjectNode toJson() {
        return null;
    }

    private void multiply(Resource resource, int number, Player player) {
        ResourceName resourceName = resource.getResourceName();
        int amount = resource.getNumOf() * number;

        player.getRes().get(resourceName).addNumOf(amount);
    }

}
