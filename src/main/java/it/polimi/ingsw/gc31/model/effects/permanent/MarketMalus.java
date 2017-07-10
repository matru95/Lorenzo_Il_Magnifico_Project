package it.polimi.ingsw.gc31.model.effects.permanent;

public class MarketMalus implements Malus{
    private MalusEnum type;
    private boolean noMarket=false;

    /**
     * Constructor of MarketMalus
     * @param type the type of the Malus
     * @param noMarket  a boolean used to applay the malus to the player;
     */
    public MarketMalus(MalusEnum type, boolean noMarket) {
        this.type = type;
        this.noMarket = noMarket;
    }

    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    public boolean isNoMarket() {
        return noMarket;
    }
}
