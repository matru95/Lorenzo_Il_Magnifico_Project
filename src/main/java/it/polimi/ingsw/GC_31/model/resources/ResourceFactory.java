package it.polimi.ingsw.GC_31.model.resources;

import it.polimi.ingsw.GC_31.model.resources.Resource;

public class ResourceFactory {

    public static Resource getResource(String type, int numOf) {

        if("Gold".equalsIgnoreCase(type)) return new Gold(numOf);
        else if("Wood".equalsIgnoreCase(type)) return new Wood(numOf);
        else if("Stone".equalsIgnoreCase(type)) return new Wood(numOf);
        else if("Servants".equalsIgnoreCase(type)) return new Wood(numOf);
        else if("WarPoints".equalsIgnoreCase(type)) return new Wood(numOf);
        else if("FaithPoints".equalsIgnoreCase(type)) return new Wood(numOf);
        else if("VictoryPoints".equalsIgnoreCase(type)) return new Wood(numOf);

        return null;
    }
}
