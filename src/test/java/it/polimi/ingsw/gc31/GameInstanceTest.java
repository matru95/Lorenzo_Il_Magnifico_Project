package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.resources.ResourceName;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class GameInstanceTest extends TestCase{
    private Player firstPlayer;
    private Player secondPlayer;
    private GameInstance gameInstance;
    private GameBoard gameBoard;

    @Override
    public void setUp() throws Exception {
        this.firstPlayer = new Player(UUID.randomUUID(), "Pippo", PlayerColor.BLUE);
        this.secondPlayer = new Player(UUID.randomUUID(), "Endi", PlayerColor.RED);

        this.gameInstance = new GameInstance(UUID.randomUUID());
        this.gameInstance.addPlayer(this.firstPlayer);
        this.gameInstance.addPlayer(this.secondPlayer);
        this.gameBoard = new GameBoard(gameInstance);

        this.gameInstance.setGameBoard(gameBoard);
        this.firstPlayer.setGameBoard(gameBoard);
        this.secondPlayer.setGameBoard(gameBoard);

    }

    @Test
    public void testFirstFamilyMemberShouldHaveGold() {
        (new Thread(gameInstance)).start();
        int firstPlayerGold = firstPlayer.getRes().get(ResourceName.GOLD).getNumOf();

        assertEquals(this.firstPlayer.getPlayerOrder()+4, firstPlayerGold);

    }
}
