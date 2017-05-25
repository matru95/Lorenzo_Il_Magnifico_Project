package it.polimi.ingsw.gc31.model.resources;


public class NoResourceMatch extends Exception {

    public NoResourceMatch(){
        super("\n Wrong matching between your resource type and resources types availables.");
    }
}
