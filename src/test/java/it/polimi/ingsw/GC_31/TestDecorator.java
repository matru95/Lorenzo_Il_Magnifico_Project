package it.polimi.ingsw.GC_31;

import it.polimi.ingsw.GC_31.model.cards.Card;
import it.polimi.ingsw.GC_31.model.cards.ConcreteCard;
import it.polimi.ingsw.GC_31.model.cards.GenericCard;

public class TestDecorator {

    public static void main(String[] args) {
        Card c1 = new ConcreteCard(new GenericCard());
        c1.create();
        System.out.println("*****");

        Card c2 = new ConcreteCard(new GenericCard());
        c2.create();
    }

}