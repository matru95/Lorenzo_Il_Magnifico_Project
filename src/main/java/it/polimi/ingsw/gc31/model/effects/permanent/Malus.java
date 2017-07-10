package it.polimi.ingsw.gc31.model.effects.permanent;

public interface Malus {
    /**
     * Will set the type of the Malus
     * @param type the type of the malus you want to set
     */
    void setMalusType(MalusEnum type);

    /**
     * Will return the type of the Malus
     * @return MalusEnum
     */
    MalusEnum getMalusType();
}
