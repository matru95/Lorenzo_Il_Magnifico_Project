package it.polimi.ingsw.GC_31;

import it.polimi.ingsw.GC_31.model.resources.*;

public class TestFactory {
    public static void main(String[] args) {

        //Istanzio una risorsa di tipo gold, inizializzandola al valore 5.
        Resource g = ResourceFactory.getResource("Gold",5);
        Resource s = ResourceFactory.getResource("Servants",35);

        //Stampo il numero di gold, aggiungo 3, e ristampo
        System.out.println(g.getNumOf());
        g.addNumOf(3);
        System.out.println(g.getNumOf());

        System.out.println(s.getNumOf());
        s.subNumOf(17);
        System.out.println(s.getNumOf());
    }
}
