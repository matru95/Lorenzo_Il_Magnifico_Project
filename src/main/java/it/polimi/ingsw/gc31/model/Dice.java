package it.polimi.ingsw.gc31.model;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class Dice {

	private int value;
	private final DiceColor color;

    public Dice(DiceColor color) {

        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public void throwDice() {

        if(color == DiceColor.NEUTRAL) {
            this.value = 0;
        }

        int min = 1;
        int max = 6;

        this.value = ThreadLocalRandom.current().nextInt(min, max+1);
    }

    public DiceColor getColor() {
        return color;
    }
}
