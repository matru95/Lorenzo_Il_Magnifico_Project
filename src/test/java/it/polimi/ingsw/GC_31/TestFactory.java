package it.polimi.ingsw.GC_31;

import it.polimi.ingsw.GC_31.model.resources.*;

public class TestFactory {
    public static void main(String[] args) {

        //Istanzio una risorsa di tipo gold, inizializzandola al valore 5.
        Resource gold = ResourceFactory.getResource("Gold",5);

        //Stampo il numero di gold
        System.out.println(gold.getNumOf());

        //Aggiungo 3 gold
        gold.addNumOf(3);

        System.out.println(gold.getNumOf());
    }
}
