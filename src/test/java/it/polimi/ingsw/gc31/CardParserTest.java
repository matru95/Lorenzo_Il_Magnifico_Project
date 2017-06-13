package it.polimi.ingsw.gc31;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardParser;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CardParserTest extends TestCase{
    CardParser parser;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void setUp() {
        this.parser = new CardParser("src/config/Card.json");
    }


    @Test
    public void testCardParserShouldParseCards() {
        parser.parse();
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

        List<Card> cards = parser.getCards();
        assertEquals(lengthCardsJson, cards.size());
    }

    @Test
    public void testCardParserShouldInsertNumOfParchments() {
        parser.parse();
        List<Card> cards = parser.getCards();
        Card testCard = cards.get(20);

        assertEquals(1, testCard.getNumOfInstantParchment());
    }

    @Test
    public void testCardParserShouldInsertCost() {
        parser.parse();
        List<Card> cards = parser.getCards();
        Card testCard = cards.get(27);

        assertEquals(1, testCard.getCost().size());
    }

    @Test
    public void testCardParserShouldInsertMultiplier() {
        parser.parse();
        List<Card> cards = parser.getCards();
        Card testCard = cards.get(27);

        Map<String, Object> testCardMultiplier = testCard.getMultiplier();
        assertNotNull(testCardMultiplier);
    }

    @Test
    public void testCardParserShouldInsertExchange() {
        parser.parse();
        List<Card> cards = parser.getCards();
        Card testCard = cards.get(28);

        assertEquals(2, testCard.getExchanges().size());
    }

}
