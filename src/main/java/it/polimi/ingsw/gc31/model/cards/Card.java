package it.polimi.ingsw.gc31.model.cards;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.Map;

public class Card {

    private final CardColor cardColor;
    private final String cardName;
    private final int cardID;
    private final int cardAge;
    private final int warPointsBond;
    private final Map<String, Resource>[] cost;
    private final Effect[] normalEffect;
    private final Effect[] instantEffect;
    private Boolean isOnDeck;

    public Card(CardColor cardColor, String cardName, int[] values, Map<String, Resource>[] cost, Effect[] normalEffect, Effect[] instantEffect) {
        this.cardColor = cardColor;
        this.cardName = cardName;
        this.cardID = values[0];
        this.cardAge = values[1];
        this.warPointsBond = values[2];
        this.cost = cost;
        this.normalEffect = normalEffect;
        this.instantEffect = instantEffect;
        isOnDeck = true;
    }

    /**
     * Method for executing an array of effects of the card.
     * @param effect an array of effects (could be either Normals or Instants)
     * @throws NoResourceMatch
     */
    private void execFX(Effect[] effect) throws NoResourceMatch {
        for (Effect anEffect : effect) anEffect.exec();
    }

    @Override
    public String toString() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow(cardName);
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        return at.render(80);
    }

    public Map<String, Resource>[] getCost() {
        return this.cost;
    }
}
