package it.polimi.ingsw.GC_31.model.resources;

public class Wood extends Resource {

    private int numOf;

    public Wood(int numOf) {
        this.numOf = numOf;
    }

    @Override
    public int getNumOf() {
        return this.numOf;
    }
}
