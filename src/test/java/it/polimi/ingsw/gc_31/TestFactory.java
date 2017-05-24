package it.polimi.ingsw.gc_31;

import it.polimi.ingsw.gc_31.model.resources.*;

public class TestFactory {
    public static void main(String[] args) {

        //Istanzio una risorsa di tipo gold, inizializzandola al valore 5.
        Resource g = null;
        try {
            g = ResourceFactory.getResource("Gold",5);
        } catch (NoResourceMatch noResourceMatch) {
            noResourceMatch.printStackTrace();
        }
        Resource s = null;
        try {
            s = ResourceFactory.getResource("Servants",35);
        } catch (NoResourceMatch noResourceMatch) {
            noResourceMatch.printStackTrace();
        }

        //Stampo il numero di gold, aggiungo 3, e ristampo
        System.out.println(g.getNumOf());
        g.addNumOf(3);
        System.out.println(g.getNumOf());

        System.out.println(s.getNumOf());
        s.subNumOf(17);
        System.out.println(s.getNumOf());
    }
}
