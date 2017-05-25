package it.polimi.ingsw.gc31.model.cards;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GreenCard extends CardDecorator{

    private final Logger logger = Logger.getLogger(Card.class.getName());

    public GreenCard(Card card) {
        super(card);
    }

    @Override
    public void create(){
        super.create();
        logger.log(Level.INFO,"Creating a GreenCard");
    }
}
