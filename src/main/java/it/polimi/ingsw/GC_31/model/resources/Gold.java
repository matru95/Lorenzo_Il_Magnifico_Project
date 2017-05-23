package it.polimi.ingsw.GC_31.model.resources;

public class Gold extends Resource {

    private int numOf;

    public Gold(int numOf) {
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