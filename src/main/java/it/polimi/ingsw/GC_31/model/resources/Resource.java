package it.polimi.ingsw.GC_31.model.resources;

public abstract class Resource {

    public abstract int getNumOf();
    public abstract void setNumOf(int numOf);

    public void addNumOf(int value) throws IllegalArgumentException {
        try {
            if (value > 0) {
                this.setNumOf(this.getNumOf() + value);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("Value must be greater then zero");
        }
    }

    public void subNumOf(int value) {
        try {
            if (value > 0 && value < this.getNumOf()) {
                this.setNumOf(this.getNumOf() - value);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Value must be greater then zero and smaller then actual value");
            e.printStackTrace();
        }
    }
}

