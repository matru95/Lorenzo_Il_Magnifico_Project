package it.polimi.ingsw.gc31.exceptions;


public class NoResourceMatch extends Exception {

    public NoResourceMatch(){
        super("This type of resource does not exist");
    }

}
