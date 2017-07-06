package it.polimi.ingsw.gc31.model.effects.permanent;

public class ServantsMalus implements Malus {
    private MalusEnum type;
    private boolean doubleServants=false;

    public ServantsMalus(MalusEnum type, boolean doubleServants) {
        this.type = type;
        this.doubleServants = doubleServants;
    }

    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    public boolean isDoubleServants() {
        return doubleServants;
    }
}
