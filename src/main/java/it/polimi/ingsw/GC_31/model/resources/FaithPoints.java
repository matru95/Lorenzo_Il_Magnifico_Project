package it.polimi.ingsw.GC_31.model.resources;

public class FaithPoints extends Resource {
    private static int numOf;

    public FaithPoints(int numOf) {
        this.numOf = numOf;
    }
    @Override
    public int getNumOf() {
        return this.numOf;
    }
    @Override
    public void setNumOf(int numOf) {
        this.numOf = numOf;
    }
}