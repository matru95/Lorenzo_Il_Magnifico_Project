package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.resources.*;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

public class TestFactory {
    public static void main(String[] args) throws NoResourceMatch {

        //Istanzio una risorsa di tipo gold, inizializzandola al valore 5.

        Resource g = ResourceFactory.getResource("Gold",5);

        Resource s = ResourceFactory.getResource("Servants",35);
        Resource h = ResourceFactory.getResource("GOLDO", 4);

        //Stampo il numero di gold, aggiungo 3, e ristampo
        System.out.println(g.getNumOf());
        g.addNumOf(3);
        System.out.println(g.getNumOf());

        System.out.println(s.getNumOf());
        s.subNumOf(17);
        System.out.println(s.getNumOf());
    }
}
