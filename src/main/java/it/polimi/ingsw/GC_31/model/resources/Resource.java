package it.polimi.ingsw.GC_31.model.resources;

public abstract class Resource {

    public abstract int getNumOf();
    public abstract void setNumOf(int numOf);

    public void addNumOf(int value) {

        this.setNumOf(this.getNumOf() + value);
    }

}

