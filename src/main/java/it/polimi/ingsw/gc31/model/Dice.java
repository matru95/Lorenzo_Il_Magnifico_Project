package it.polimi.ingsw.gc31.model;

import java.util.Random;

public final class Dice {

	private final int value;
	private final DiceColor color;

    public Dice(DiceColor color) {

        this.color = color;
        Random r = new Random();

        if (color == DiceColor.NEUTRAL) {
            this.value = 0;
        } else {
            this.value = 1 + r.nextInt(5);
        }
    }

    public int getValue() {
        return value;
    }

    public DiceColor getColor() {
        return color;
    }
}
