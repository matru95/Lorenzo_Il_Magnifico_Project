package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.Dice;
import it.polimi.ingsw.gc31.model.DiceColor;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class DiceTest {

    @Test
    public void neutralDiceShouldBeZero() {
        Dice neutralDiceTester = new Dice(DiceColor.NEUTRAL);
        neutralDiceTester.throwDice();

        assertEquals("Neutral dice should be 0", neutralDiceTester.getValue(), 0);
    }

    @Test
    public void blackDiceShouldBeBetweenOneAndSix() {
        boolean isBetweenOneAndSix;

        Dice blackDiceTester = new Dice(DiceColor.BLACK);
        blackDiceTester.throwDice();

        isBetweenOneAndSix = blackDiceTester.getValue() <= 6 && blackDiceTester.getValue() >=1;

        assertTrue("Black Dice value should be between 1 and 6", isBetweenOneAndSix);
    }

    @Test
    public void whiteDiceShouldBeBetweenOneAndSix() {
        boolean isBetweenOneAndSix;

        Dice whiteDiceTester = new Dice(DiceColor.WHITE);
        whiteDiceTester.throwDice();

        isBetweenOneAndSix = whiteDiceTester.getValue() <= 6 && whiteDiceTester.getValue() >= 1;

        assertTrue("White Dice value should be between 1 and 6", isBetweenOneAndSix);
    }


    @Test
    public void orangeDiceShouldBeBetweenOneAndSix() {
        boolean isBetweenOneAndSix;

        Dice orangeDiceTester = new Dice(DiceColor.ORANGE);
        orangeDiceTester.throwDice();

        isBetweenOneAndSix = orangeDiceTester.getValue() <= 6 && orangeDiceTester.getValue() >= 1;

        assertTrue("Orange Dice value should be between 1 and 6", isBetweenOneAndSix);
    }

}