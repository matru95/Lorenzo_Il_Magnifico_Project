package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import jdk.nashorn.internal.ir.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public class Exchange extends Effect{
    private List<Resource> resourcesToGive;
    private List<Resource> resourcesToReceive;
    private int numOfParchmentsToReceive;


    public Exchange() {
        this.resourcesToGive = new ArrayList<>();
        this.resourcesToReceive = new ArrayList<>();
        this.numOfParchmentsToReceive = 0;
    }

    @Override
    public void exec(Player player) throws NoResourceMatch {

    }

    @Override
    public ObjectNode toJson() {
        return null;
    }

    public void setNumOfParchmentsToReceive(int numOfParchmentsToReceive) {
        this.numOfParchmentsToReceive = numOfParchmentsToReceive;
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

}
