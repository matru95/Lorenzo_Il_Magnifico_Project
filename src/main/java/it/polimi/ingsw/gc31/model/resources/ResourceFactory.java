package it.polimi.ingsw.gc31.model.resources;


public class ResourceFactory {
    private ResourceFactory() {
    }

    public static Resource getResource(String type, int numOf) throws NoResourceMatch {

        switch(type.toUpperCase()) {
            case "GOLD":
                return new Gold(numOf);
            case "WOOD":
                return new Wood(numOf);
            case "STONE":
                return new Stone(numOf);
            case "SERVANTS":
                return new Servants(numOf);
            case "WARPOINTS":
                return new WarPoints(numOf);
            case "FAITHPOINTS":
                return new FaithPoints(numOf);
            default:
                throw new NoResourceMatch();
        }
    }
}
