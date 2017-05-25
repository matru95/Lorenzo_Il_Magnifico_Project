package it.polimi.ingsw.gc31.model.cards;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PurpleCard extends CardDecorator{

    private final Logger logger = Logger.getLogger(Card.class.getName());

    public PurpleCard(Card card) {
        super(card);
    }

    @Override
    public void create(){
        super.create();
        logger.log(Level.INFO,"Creating a PurpleCard");
    }
}
