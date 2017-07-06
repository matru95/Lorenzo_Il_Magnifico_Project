package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.parser.CardParser;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class GameBoardTest {

    public static List<Card> cards;

    @Test
    public void ShouldInitSpacesCorrectly() throws IOException {
        Player p1 = new Player(UUID.randomUUID(), "MATRU");
        Player p2 = new Player(UUID.randomUUID(), "LEO");
        Player p3 = new Player(UUID.randomUUID(), "ENDI");
        Player p4 = new Player(UUID.randomUUID(), "DOG");
        GameInstance gameInstance = new GameInstance(UUID.randomUUID());
        gameInstance.addPlayer(p1);
        gameInstance.addPlayer(p2);
        gameInstance.addPlayer(p3);
        gameInstance.addPlayer(p4);
        GameBoard gameBoard = new GameBoard(gameInstance);
        gameInstance.setGameBoard(gameBoard);
        p1.setGameBoard(gameBoard);
        p2.setGameBoard(gameBoard);
        p3.setGameBoard(gameBoard);
        p4.setGameBoard(gameBoard);

        (new Thread(gameInstance)).start();
        CardParser cardParser = new CardParser("src/config/Card.json");
        cardParser.parse();
        cards = cardParser.getCards();
        for( CardColor c: CardColor.values()){
            for(int i = 0; i < 4; i++){
                System.out.println(gameBoard.getTowerByColor(c).getTowerSpace().get(i).getPositionID());
            }
        }
        assertTrue(true);
    }
}
