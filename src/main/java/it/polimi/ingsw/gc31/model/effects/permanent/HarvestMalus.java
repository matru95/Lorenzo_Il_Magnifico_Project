package it.polimi.ingsw.gc31.model.effects.permanent;

public class HarvestMalus implements Malus {
    private MalusEnum type;
    private int harvestFewer=0;
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

    public int getHarvestFewer() {
        return harvestFewer;
    }
}
