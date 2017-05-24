package it.polimi.ingsw.gc_31.model.resources;

public class WarPoints extends Resource {

    private int numOf;

    public WarPoints(int numOf) {
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
