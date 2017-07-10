package it.polimi.ingsw.gc31.model.effects.permanent;

public class PlayerResourceMalus implements Malus{
    private MalusEnum type;
    private boolean loseForEveryResource;

    /**
     * Constructor of PlayerResourceMalus
     * @param type the type of the Malus
     * @param loseForEveryResource a boolean used to applay the malus
     */
    public PlayerResourceMalus(MalusEnum type, boolean loseForEveryResource) {
        this.type = type;
        this.loseForEveryResource = loseForEveryResource;
    }

    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    public boolean isLoseForEveryResource() {
        return loseForEveryResource;
    }
}
