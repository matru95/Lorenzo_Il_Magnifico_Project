package it.polimi.ingsw.gc31.model.effects.permanent;

public class ProductionMalus implements Malus {
    private MalusEnum type;
    private int productionFewer=0;

    /**
     * Constructur of Production Malus
     * @param type  Type of the Malus ( in MalusEnum.PRODUCTIONMALUS ).
     * @param productionFewer the value of the production malus.
     */
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
