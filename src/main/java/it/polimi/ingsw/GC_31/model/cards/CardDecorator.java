package it.polimi.ingsw.GC_31.model.cards;

public class CardDecorator implements Card {

    protected Card card;

    public CardDecorator(Card c){
        this.card = c;
    }

    @Override
    public void create() {
        this.card.create();
    }
}
