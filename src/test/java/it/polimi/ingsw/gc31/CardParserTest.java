package it.polimi.ingsw.gc31;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.parser.CardParser;
import it.polimi.ingsw.gc31.model.effects.Effect;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CardParserTest extends TestCase{
    CardParser parser;
    Logger logger = Logger.getLogger(this.getClass().getName());
    List<Card> cards;

    @Override
    public void setUp() {
        this.parser = new CardParser("src/config/Card.json");
        parser.parse();
        this.cards = parser.getCards();
    }

    @Override
    public void tearDown() {
        this.parser = null;
        this.cards = null;
    }

    @Test
    public void testCardParserShouldParseCards() {
        ObjectMapper mapper =new ObjectMapper();
        File jsonInputFile = new File("src/config/Card.json");
        int lengthCardsJson = 0;

        try {
            JsonNode rootNode = mapper.readTree(jsonInputFile);
            JsonNode cardNode = rootNode.path("cards");
            for(JsonNode card: cardNode) {
                lengthCardsJson++;
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "JSON file for cards not found");
        }

        assertEquals(lengthCardsJson, cards.size());
    }

    @Test
    public void testCardParserShouldInsertNumOfParchments() {
        Card testCard = cards.get(20);

        assertEquals(1, testCard.getNumOfInstantParchment());
    }

    @Test
    public void testCardParserShouldInsertCost() {
        Card testCard = cards.get(27);

        assertEquals(1, testCard.getCost().size());
    }

    @Test
    public void testCardParserShouldInsertMultiplier() {
        Card testCard = cards.get(27);

        Effect testCardMultiplier = testCard.getNormalEffects().get(0);
        assertNotNull(testCardMultiplier);
    }

    @Test
    public void testCardParserShouldInsertExchange() {
        Card testCard = cards.get(28);

        assertEquals(2, testCard.getExchanges().size());
    }

    @Test
    public void testCardParserShouldHaveInstantMultiplier() {
        Card testCard = cards.get(65);

        assertNotNull(testCard.getInstantEffects().get(0));
    }

}
