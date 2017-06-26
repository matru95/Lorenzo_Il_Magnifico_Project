package it.polimi.ingsw.gc31.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import java.io.Serializable;
import java.util.Random;

public final class Dice implements Serializable{

	private int value;
	private final DiceColor color;

    public Dice(DiceColor color) {
        this.color = color;
    }

    /**
     * Method for randomizing the "value" of the dice (Zero if NEUTRAL)
     */
    public void throwDice() {

        Random r = new Random();

        if (color == DiceColor.NEUTRAL) {
            this.value = 0;
        } else {
            this.value = 1 + r.nextInt(6);
        }

    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Getter for attribute "value"
     * @return int
     */
    public int getValue() {
        return value;
    }

    /**
     * Getter for attribute "color"
     * @return DiceColor
     */
    public DiceColor getColor() {
        return color;
    }
}
