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
    public void playerShouldHaveOrder() throws NoResourceMatch {
        Player playerForTesting = new Player(0, "Endi", PlayerColor.BLUE);
        Player[] playerListForTesting = new Player[1];

        playerListForTesting[0] = playerForTesting;

        GameInstance gameInstanceForTesting = new GameInstance(1, playerListForTesting);
        GameBoard gameBoardForTesting = new GameBoard(gameInstanceForTesting);

        gameInstanceForTesting.setGameBoard(gameBoardForTesting);
        playerForTesting.setGameBoard(gameBoardForTesting);
        playerForTesting.initFamilyMembers();
        playerForTesting.setPlayerOrder(1);

        assertEquals(playerForTesting.getPlayerOrder(), 1);
    }
}
