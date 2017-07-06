package it.polimi.ingsw.gc31.model.effects.permanent;


import it.polimi.ingsw.gc31.enumerations.CardColor;

public class CardDiceMalus implements Malus{
    private MalusEnum type;
    private CardColor fewerDiceCardColor;
    private int fewerDiceCardValue=0;

    public CardDiceMalus(MalusEnum type, CardColor fewerDiceCardColor, int fewerDiceCardValue) {
        this.type = type;
        this.fewerDiceCardColor = fewerDiceCardColor;
        this.fewerDiceCardValue = fewerDiceCardValue;
    }

    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    public int getFewerDiceCardValue() {
        return fewerDiceCardValue;
    }

    public CardColor getFewerDiceCardColor() {
        return fewerDiceCardColor;
    }
}
