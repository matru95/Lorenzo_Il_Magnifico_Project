package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.model.FamilyMember;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.Tower;
import it.polimi.ingsw.gc31.model.board.TowerSpaceWrapper;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.resources.Resource;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TowerSpaceWrapperTest extends TestCase{
    private TowerSpaceWrapper towerSpaceWrapper;
    private Player player;

    @Override
    public void setUp() throws IOException {

        GameInstance gameInstance = new GameInstance(UUID.randomUUID());
        GameBoard gameBoard = new GameBoard(gameInstance);
        gameInstance.setGameBoard(gameBoard);
        this.player = new Player(UUID.randomUUID(), "Foo");
        gameInstance.addPlayer(player);

        gameInstance.run();

        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource(ResourceName.GOLD, 1));
        gameBoard.getTowerByColor(CardColor.GREEN).drawCards();
        towerSpaceWrapper = (TowerSpaceWrapper) gameBoard.getSpaceById(1);

    }

    @Test
    public void testTowerSpaceWrapperShouldHaveExec() {
        FamilyMember familyMember = player.getSpecificFamilyMember(DiceColor.WHITE);
        List<ServerMessage> messages = towerSpaceWrapper.execWrapper(familyMember, 0);
        assertNotNull(messages);
    }


    @Test
    public void testToweSpaceWrapperShouldReturnJSON() {
        assertNotNull(towerSpaceWrapper.toJson());
    }

    @Test
    public void testTowerSpaceWrapperShouldReturnString() {
        assertNotNull(towerSpaceWrapper.toString());
    }

    @Test
    public void testTowerSpaceWrapperShouldBeAffordable() {
        FamilyMember familyMember = player.getSpecificFamilyMember(DiceColor.WHITE);

        assertTrue(towerSpaceWrapper.isAffordable(familyMember, player.getRes(), player.getPlayerColor()));
    }


    @Test
    public void testTowerSpaceWrapperShouldBeAffordableWithCard() {
        FamilyMember familyMember = player.getSpecificFamilyMember(DiceColor.WHITE);
        towerSpaceWrapper.getGameBoard().getTowerByColor(CardColor.BLUE).drawCards();
        TowerSpaceWrapper myTowerSpaceWrapper = (TowerSpaceWrapper) towerSpaceWrapper.getGameBoard().getSpaceById(5);
        player.getRes().get(ResourceName.GOLD).addNumOf(100);

        assertTrue(myTowerSpaceWrapper.isAffordable(familyMember, player.getRes(), player.getPlayerColor()));
    }

    @Test
    public void testTowerSpaceWrapperShouldBeAffordableWithOccupiedTower() {
        FamilyMember familyMember = player.getSpecificFamilyMember(DiceColor.NEUTRAL);
        towerSpaceWrapper.setFamilyMember(familyMember);
        FamilyMember myFamilyMember = player.getSpecificFamilyMember(DiceColor.WHITE);
        TowerSpaceWrapper mySpaceWrapper = (TowerSpaceWrapper) towerSpaceWrapper.getTower().getTowerSpaces().get(1);
        player.getRes().get(ResourceName.GOLD).addNumOf(100);
        player.getRes().get(ResourceName.SERVANTS).addNumOf(100);

        assertTrue(mySpaceWrapper.isAffordable(myFamilyMember, player.getRes(), player.getPlayerColor()));

    }

    @Override
    public void tearDown() {
        this.towerSpaceWrapper = null;
        this.player = null;
    }
}
