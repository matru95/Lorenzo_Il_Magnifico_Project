package it.polimi.ingsw.GC_31.model.resources;

public class VictoryPoints extends Resource {

    private int numOf;

    public VictoryPoints(int numOf) {
        this.numOf = numOf;
    }

    @Override
    public int getNumOf() {
        return this.numOf;
    }
}
