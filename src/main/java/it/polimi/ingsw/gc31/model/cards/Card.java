package it.polimi.ingsw.gc31.model.cards;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

public class Card {

    private final CardColor cardColor;
    private final String cardName;
    private final int cardID;
    private final int cardAge;
    private final CardEffects cardEffects;
    private Boolean isOnDeck;

    public Card(CardColor cardColor, String cardName, int cardID, int cardAge, CardEffects cardEffects) {
        this.cardColor = cardColor;
        this.cardName = cardName;
        this.cardID = cardID;
        this.cardAge = cardAge;
        this.cardEffects = cardEffects;
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

    public CardColor getCardColor() {
        return cardColor;
    }

    public String getCardName() {
        return cardName;
    }

    public int getCardID() {
        return cardID;
    }

    public int getCardAge() {
        return cardAge;
    }

    public CardEffects getCardEffects() {
        return cardEffects;
    }

    public Boolean getOnDeck() {
        return isOnDeck;
    }

    public void setOnDeck(Boolean onDeck) {
        isOnDeck = onDeck;
    }
}
