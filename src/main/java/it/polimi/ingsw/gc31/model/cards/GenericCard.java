package it.polimi.ingsw.gc31.model.cards;


import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericCard implements Card {

    private final Logger logger = Logger.getLogger(Card.class.getName());

    private final String cardName;
    private final int cardID;
    private final int cardAge;
    private final int warPointsBond;
    private final CardColor cardColor;
    private final Map<String, Resource> cost[];
    private final Effect[] normalEffect;
    private final Effect[] instantEffect;
    private Boolean isOnDeck;

    public GenericCard(String cardName, int cardID, int cardAge, int warPointsBond, Effect normalEffect,
                Effect instantEffect) {

        this.cardName = cardName;
        this.cardID = cardID;
        this.cardAge = cardAge;
        this.warPointsBond = warPointsBond;
        this.normalEffect = normalEffect;
        this.instantEffect = instantEffect;

        //	A card first starts in the deck;
        this.deck = true;
    }

    public void execEffect(Effect effect) {
        effect.runEffect();
    }*/

    @Override
    public void create() {
        logger.log(Level.INFO,"Basic Card");
    }

}
