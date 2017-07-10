package it.polimi.ingsw.gc31.model.effects.permanent;


public class ProductionBonus implements Bonus{
    private int amount;

    /**
     * Constructor ProductionBonus.
     * @param amount the amount of the bonus.
     */
    public ProductionBonus(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
