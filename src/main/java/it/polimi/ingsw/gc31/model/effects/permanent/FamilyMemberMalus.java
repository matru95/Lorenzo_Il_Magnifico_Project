package it.polimi.ingsw.gc31.model.effects.permanent;


public class FamilyMemberMalus implements Malus{
    private MalusEnum type;
    private int diceFewer=0;

    public FamilyMemberMalus(MalusEnum type, int diceFewer) {
        this.type = type;
        this.diceFewer = diceFewer;
    }

    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    public int getDiceFewer() {
        return diceFewer;
    }
}
