package it.polimi.ingsw.gc_31.model.resources;

public class ResourceFactory {

    private ResourceFactory() {}

    public static Resource getResource(String type, int numOf) {

        if("Gold".equalsIgnoreCase(type)) return new Gold(numOf);
        else if("Wood".equalsIgnoreCase(type)) return new Wood(numOf);
        else if("Stone".equalsIgnoreCase(type)) return new Stone(numOf);
        else if("Servants".equalsIgnoreCase(type)) return new Servants(numOf);
        else if("WarPoints".equalsIgnoreCase(type)) return new WarPoints(numOf);
        else if("FaithPoints".equalsIgnoreCase(type)) return new FaithPoints(numOf);
        else if("VictoryPoints".equalsIgnoreCase(type)) return new VictoryPoints(numOf);

        return null;
    }
}
