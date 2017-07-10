package it.polimi.ingsw.gc31.model.effects.permanent;

public class FirstActionMalus implements Malus {
    private MalusEnum type;
    private boolean skipFirstRound=false;

    /**
     * Constructor of FirstActionMalus
     * @param type the type of the Malus
     * @param skipFirstRound
     */
    public FirstActionMalus(MalusEnum type, boolean skipFirstRound) {
        this.type = type;
        this.skipFirstRound = skipFirstRound;
    }

    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    public boolean isSkipFirstRound() {
        return skipFirstRound;
    }
}
