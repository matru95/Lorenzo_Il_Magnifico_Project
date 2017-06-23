package it.polimi.ingsw.gc31.enumerations;

public enum PlayerColor {
	RED, BLUE, GREEN, YELLOW;

	public static Boolean contains(String value) {
		String upperCaseValue = value.toUpperCase();
		for(PlayerColor color : PlayerColor.values()) {
			if(color.name().equals(upperCaseValue)) {
				return true;
			}
		}
		return false;
	}
}
