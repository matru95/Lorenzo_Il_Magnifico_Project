package it.polimi.ingsw.gc_31;

import it.polimi.ingsw.gc_31.model.resources.*;

public class TestFactory {
    public static void main(String[] args) throws NoResourceMatch, it.polimi.ingsw.gc_31.model.resources.NoResourceMatch {

        //Istanzio una risorsa di tipo gold, inizializzandola al valore 5.
        Resource g = null;
        g = ResourceFactory.getResource("Gold",5);
        Resource s = null;
        s = ResourceFactory.getResource("Servants",35);

        //Stampo il numero di gold, aggiungo 3, e ristampo
        System.out.println(g.getNumOf());
        g.addNumOf(3);
        System.out.println(g.getNumOf());

        System.out.println(s.getNumOf());
        s.subNumOf(17);
        System.out.println(s.getNumOf());
    }
}
