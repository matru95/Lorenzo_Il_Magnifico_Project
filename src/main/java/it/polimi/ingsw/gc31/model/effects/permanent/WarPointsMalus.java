package it.polimi.ingsw.gc31.model.effects.permanent;

import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;

public class WarPointsMalus implements Malus {
    private MalusEnum type;
    private List<Resource> forEveryRes=new ArrayList<>();
    private List <Resource> loseRes=new ArrayList<>();

    public WarPointsMalus(MalusEnum type, List<Resource> forEveryRes, List<Resource> loseRes) {
        this.type = type;
        this.forEveryRes = forEveryRes;
        this.loseRes = loseRes;
    }

    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    public List<Resource> getForEveryRes() {
        return forEveryRes;
    }

    public List<Resource> getLoseRes() {
        return loseRes;
    }
}
