package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardParser;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class CardParserTest extends TestCase{
    CardParser parser;

    @Override
    public void setUp() {
        this.parser = new CardParser("src/config/Card.json");
    }


    @Test
    public void testCardParserShouldParseCards() {
        parser.parse();
        List<Card> cards = parser.getCards();
        assertEquals(28, cards.size());
    }

    @Test
    public void testCardParserShouldInsertNumOfParchments() {
        parser.parse();
        List<Card> cards = parser.getCards();
        Card testCard = cards.get(20);

        assertEquals(1, testCard.getNumOfInstantParchment());
    }

}
