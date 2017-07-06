package it.polimi.ingsw.gc31.model.effects.permanent;

public class ProductionMalus implements Malus {
    private MalusEnum type;
    private int productionFewer=0;

    public ProductionMalus(MalusEnum type, int productionFewer) {
        this.type = type;
        this.productionFewer = productionFewer;
    }

    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    public int getProductionFewer() {
        return productionFewer;
    }
}
