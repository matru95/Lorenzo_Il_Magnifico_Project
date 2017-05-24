package it.polimi.ingsw.gc_31;


import it.polimi.ingsw.gc_31.model.GameInstance;
import it.polimi.ingsw.gc_31.model.resources.NoResourceMatch;

public class App
{
    private App() {}
    public static void main( String[] args ) throws NoResourceMatch {
        new GameInstance();
    }
}
