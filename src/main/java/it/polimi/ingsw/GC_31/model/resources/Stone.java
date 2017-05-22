package it.polimi.ingsw.GC_31.model.resources;

public class Stone extends Resource {

    private int numOf;

    public Stone(int numOf) {
        this.numOf = numOf;
    }

    @Override
    public int getNumOf() {
        return this.numOf;
    }
}
