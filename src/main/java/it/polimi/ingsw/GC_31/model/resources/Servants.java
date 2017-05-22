package it.polimi.ingsw.GC_31.model.resources;

public class Servants extends Resource {

    private int numOf;

    public Servants(int numOf) {
        this.numOf = numOf;
    }

    @Override
    public int getNumOf() {
        return this.numOf;
    }
}
