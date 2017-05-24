package it.polimi.ingsw.GC_31.model.cards;

public class ConcreteCard extends CardDecorator{

    public ConcreteCard(Card card) {
        super(card);
    }

    @Override
    public void create(){
        super.create();
        System.out.print(" Adding features to Card.");
    }
}
