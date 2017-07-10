package it.polimi.ingsw.gc31.model.cards;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.ParchmentEffect;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;

public class Exchange {
    private List<Resource> resourcesToGive;
    private List<Resource> resourcesToReceive;
    private int numOfParchmentsToReceive;


    public Exchange() {
        this.resourcesToGive = new ArrayList<>();
        this.resourcesToReceive = new ArrayList<>();
        this.numOfParchmentsToReceive = 0;
    }

    @Override
    public String toString() {
        String result = "Pay: " + resourcesToGive.toString();

        if(resourcesToReceive.size() > 0) {
            result += " to receive: " + resourcesToReceive.toString();
        } else {
            result += " to receive: " + numOfParchmentsToReceive + " parchment(s)";
        }

        return result;
    }


    public void setNumOfParchmentsToReceive(int numOfParchmentsToReceive) {
        this.numOfParchmentsToReceive = numOfParchmentsToReceive;
    }

    public int getNumOfParchmentsToReceive() {
        return numOfParchmentsToReceive;
    }

    public void addResourceToGive(Resource resourceToGive) {
        resourcesToGive.add(resourceToGive);
    }

    public void addResourceToReceive(Resource resourceToReceive) {
        resourcesToReceive.add(resourceToReceive);
    }

    public List<Resource> getResourcesToGive() {
        return this.resourcesToGive;
    }

    public List<Resource> getResourcesToReceive() {
        return this.resourcesToReceive;
    }

    public void setResourcesToGive(List<Resource> resourcesToGive) {
        this.resourcesToGive = resourcesToGive;
    }

    public void setResourcesToReceive(List<Resource> resourcesToReceive) {
        this.resourcesToReceive = resourcesToReceive;
    }

    public ServerMessage exec(Player player) {
        ServerMessage message = null;
//      Take resources
        for(Resource resourceToGive: resourcesToGive) {
            player.getRes().get(resourceToGive.getResourceName()).subNumOf(resourceToGive.getNumOf());
        }

        for(Resource resourceToReceive: resourcesToReceive) {
            player.getRes().get(resourceToReceive.getResourceName()).addNumOf(resourceToReceive.getNumOf());
        }

        if(numOfParchmentsToReceive > 0) {
            ParchmentEffect parchmentEffect = new ParchmentEffect(numOfParchmentsToReceive);
            parchmentEffect.exec(player);
        }

        return message;

    }
}
