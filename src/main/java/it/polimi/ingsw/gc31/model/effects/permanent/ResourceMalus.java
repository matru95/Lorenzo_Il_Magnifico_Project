package it.polimi.ingsw.gc31.model.effects.permanent;

import it.polimi.ingsw.gc31.model.resources.Resource;

import java.util.List;

public class ResourceMalus implements Malus {
    private MalusEnum type;
    private List<Resource> gainFewerStack;
    public ResourceMalus(MalusEnum type, List<Resource> gainFewerStack){
        this.type=type;
        this.gainFewerStack=gainFewerStack;
    }
    @Override
    public void setMalusType(MalusEnum type) {
        this.type=type;
    }

    @Override
    public MalusEnum getMalusType() {
        return this.type;
    }

    public List<Resource> getGainFewerStack() {
        return gainFewerStack;
    }

    public void setGainFewerStack(List<Resource> gainFewerStack) {
        this.gainFewerStack = gainFewerStack;
    }
}
