package it.polimi.ingsw.gc31.model.effects.permanent;

public class HarvestMalus implements Malus {
    private MalusEnum type;
    private int harvestFewer=0;

    /**
     * Constructor of HarvestMalus
     * @param type  the type of the Malus
     * @param harvestFewer a boolean used to applay the malus to the player;
     */
    public HarvestMalus(MalusEnum type, int harvestFewer){
        this.type=type;
        this.harvestFewer=harvestFewer;
    }
    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    /**
     * Returning the value of the Harvest Malus
     * @return int
     */
    public int getHarvestFewer() {
        return harvestFewer;
    }
}
