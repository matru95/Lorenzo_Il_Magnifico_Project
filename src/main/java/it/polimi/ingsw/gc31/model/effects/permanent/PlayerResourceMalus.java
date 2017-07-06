package it.polimi.ingsw.gc31.model.effects.permanent;

public class PlayerResourceMalus implements Malus{
    private MalusEnum type;
    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }
}
