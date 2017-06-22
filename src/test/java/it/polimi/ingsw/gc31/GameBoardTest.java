package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.controller.PlayerController;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.cards.CardParser;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.view.cli.GameView;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class GameBoardTest {

    public static List<Card> cards;

    @Test
    public void ShouldInitSpacesCorrectly() throws NoResourceMatch {
        Player p1 = new Player(UUID.randomUUID(), "MATRU", PlayerColor.BLUE);
        Player p2 = new Player(UUID.randomUUID(), "LEO", PlayerColor.RED);
        Player p3 = new Player(UUID.randomUUID(), "ENDI", PlayerColor.GREEN);
        Player p4 = new Player(UUID.randomUUID(), "DOG", PlayerColor.YELLOW);
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
        for(Map.Entry<String,SpaceWrapper> entry: gameBoard.getBoardSpaces().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getPositionID());
        }
        assertTrue(true);
    }
}
