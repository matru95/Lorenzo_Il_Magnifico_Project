package it.polimi.ingsw.gc31.model.effects.permanent;

public class HarvestBonus implements Bonus{
    private int amount;


    public HarvestBonus(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
