package it.polimi.ingsw.gc31.model.effects.permanent;

import it.polimi.ingsw.gc31.enumerations.CardColor;

public class CardPointsMalus implements Malus {
    private MalusEnum type;
    private CardColor noEndGamePointsCardColor;

    /**
     * Constructor of CardPointsMalus.
     * @param type the type of the Malus.
     * @param noEndGamePointsCardColor
     */
    public CardPointsMalus(MalusEnum type, CardColor noEndGamePointsCardColor) {
        this.type = type;
        this.noEndGamePointsCardColor = noEndGamePointsCardColor;
    }

    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    public CardColor getNoEndGamePointsCardColor() {
        return noEndGamePointsCardColor;
    }
}
