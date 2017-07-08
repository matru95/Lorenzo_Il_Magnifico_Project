package it.polimi.ingsw.gc31.model.effects;

import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.effects.permanent.Malus;
import it.polimi.ingsw.gc31.model.effects.permanent.MalusEnum;
import it.polimi.ingsw.gc31.model.effects.permanent.PlayerResourceMalus;
import it.polimi.ingsw.gc31.model.effects.permanent.ResourceMalus;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import jdk.nashorn.internal.ir.ObjectNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddResEffect extends Effect{
    private List<Resource> resourcesToAdd;

    /**
     * Constructor of AddResEffect
     * @param resourcesToAdd A list of resources that need to be added to the player
     */
    public AddResEffect(List<Resource> resourcesToAdd){
        this.resourcesToAdd = resourcesToAdd;
    }

    /**
     * Performs the AddResEffect effect.
     * @param player The player that casts the effect.
     * @return ServerMessage
     */
    @Override
    public ServerMessage exec(Player player) {
        Map<ResourceName, Resource> playerResources = player.getRes();

        List<Malus> maluses= player.getMaluses();
        List<Resource> gainFewerStack = null;
        Map<ResourceName,Resource> resDebuff= new HashMap<>();
        if(maluses!=null){
            for(Malus malus: maluses) {
                if(malus.getMalusType()== MalusEnum.RESOURCEMALUS){
                    gainFewerStack = ((ResourceMalus) malus).getGainFewerStack();

                    //MAP NEEDED FOR NEXT OPERATION
                    for(Resource resource : gainFewerStack){
                        resDebuff.put(resource.getResourceName(),resource);
                    }
                }
            }
        }
        for(Resource resourceToAdd: resourcesToAdd) {
            ResourceName resourceName = resourceToAdd.getResourceName();
            int value=0;

            //DOUBLE IF ADDED FOR AVOIDING NULLPOINTEREXCEPTION
            if(resDebuff.get(resourceName)!=null) {
                value = resourceToAdd.getNumOf()-resDebuff.get(resourceName).getNumOf();
            }
            if (resDebuff.get(resourceName)==null){
                value= resourceToAdd.getNumOf();
            }

            playerResources.get(resourceName).addNumOf(value);
        }
        return null;
    }

    /**
     * Null method
     * @return ObjectNode
     */
    @Override
    public ObjectNode toJson() {
        return null;
    }

    public List getResourcesToAdd() {
        return resourcesToAdd;
    }

    public void setResourcesToAdd(List resourcesToAdd) {
        this.resourcesToAdd = resourcesToAdd;
    }
}