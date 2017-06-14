package it.polimi.ingsw.gc31.model.cards;

public enum CardColor {
	GREEN, BLUE, YELLOW, PURPLE;

	public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
		for (E e : enumClass.getEnumConstants()) {
			if(e.name().equals(value)) { return true; }
		}
		return false;
	}

}
