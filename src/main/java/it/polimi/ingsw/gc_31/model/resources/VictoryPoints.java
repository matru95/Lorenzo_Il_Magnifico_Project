package it.polimi.ingsw.gc_31.model.resources;

public class VictoryPoints extends Resource {

    private int numOf;

    public VictoryPoints(int numOf) {
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
