package it.polimi.ingsw.gc31.model.resources;

public class Wood extends Resource {

    private int numOf;

    public Wood(int numOf) {
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