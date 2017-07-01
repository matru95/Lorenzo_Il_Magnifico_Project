package it.polimi.ingsw.gc31.model.cards;

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
