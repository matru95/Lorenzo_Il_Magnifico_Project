package it.polimi.ingsw.gc_31.model.resources;

public abstract class Resource {

    public abstract int getNumOf();
    public abstract void setNumOf(int numOf);

    public void addNumOf(int value) {

        this.setNumOf(this.getNumOf() + value);
    }

    public void subNumOf(int value) {

        this.setNumOf(this.getNumOf() - value);
    }
}

