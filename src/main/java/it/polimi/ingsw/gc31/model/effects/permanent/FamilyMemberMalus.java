package it.polimi.ingsw.gc31.model.effects.permanent;


public class FamilyMemberMalus implements Malus{
    private MalusEnum type;
    private int diceFewer=0;

    /**
     * Constructor of FamilyMemberMalus
     * @param type the type of the Malus
     * @param diceFewer the value of the Malus
     */
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

    /**
     * Return the value of the FamilyMemberMalus
     * @return int
     */
    public int getDiceFewer() {
        return diceFewer;
    }
}
