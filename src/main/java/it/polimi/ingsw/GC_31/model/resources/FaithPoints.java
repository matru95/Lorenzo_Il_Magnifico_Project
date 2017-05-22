package it.polimi.ingsw.GC_31.model.resources;

public class FaithPoints extends Resource {

    private int numOf;

    public FaithPoints(int numOf) {
        this.numOf = numOf;
    }

    @Override
    public int getNumOf() {
        return this.numOf;
    }
}
