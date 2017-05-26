package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PlayerTest {

    @Test
    public void playerShouldHaveInitialResources() throws NoResourceMatch {
        GameInstance gameInstanceForTesting = new GameInstance();
        GameBoard gameBoardForTesting = new GameBoard(gameInstanceForTesting);
        Player playerForTesting = new Player(0, "Endi", PlayerColor.BLUE, gameBoardForTesting);
        playerForTesting.setPlayerOrder(1);

        assertEquals(playerForTesting.getPlayerOrder(), 1);
    }
}
