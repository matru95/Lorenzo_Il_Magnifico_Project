package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.exceptions.MovementInvalidException;
import it.polimi.ingsw.gc31.model.*;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.ProductionWrapper;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.board.TowerSpaceWrapper;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.parser.CardParser;
import it.polimi.ingsw.gc31.model.states.GamePrepState;
import it.polimi.ingsw.gc31.model.states.State;
import it.polimi.ingsw.gc31.model.states.TurnState;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;
import java.util.UUID;


public class FamilyMemberTest extends TestCase{
    private Player firstPlayer;
    private Player secondPlayer;
    private Player playerWithTurn;
    private GameBoard gameBoard;
    private GameInstance gameInstance;

    @Override
    public void setUp() throws Exception{
        this.firstPlayer = new Player(UUID.randomUUID(), "FirstPlayer");
        this.secondPlayer = new Player(UUID.randomUUID(), "SecondPlayer");

        this.gameInstance = new GameInstance(UUID.randomUUID());
        this.gameInstance.addPlayer(this.firstPlayer);
        this.gameInstance.addPlayer(this.secondPlayer);

        this.gameBoard = new GameBoard(gameInstance);
        this.gameInstance.setGameBoard(gameBoard);

        this.gameInstance.run();


        State turnState = new TurnState();
        gameInstance.setState(turnState);
        gameInstance.getState().doAction(gameInstance);

        playerWithTurn = ((TurnState) gameInstance.getState()).getOrderedPlayers()[0];
    }

    @Test
    public void testFamilyMemberShouldExist() {
        assertNotNull(playerWithTurn.getFamilyMembers()[0]);
    }

    @Test
    public void testFamilyMemberShouldMoveToPosition() {
        FamilyMember testFamilyMember = playerWithTurn.getSpecificFamilyMember(DiceColor.WHITE);
        SpaceWrapper position = gameInstance.getGameBoard().getSpaceById(23);
        try {
            testFamilyMember.moveToPosition(position, 0);
        } catch (MovementInvalidException e) {
        }

        assertEquals(position, testFamilyMember.getCurrentPosition());
    }

    @Test
    public void testFamilyMemberShouldNotMoveTwice() {
        boolean condition = false;
        FamilyMember testFamilyMember = playerWithTurn.getSpecificFamilyMember(DiceColor.WHITE);
        SpaceWrapper position = gameInstance.getGameBoard().getSpaceById(23);

        try {
            testFamilyMember.moveToPosition(position, 23);
        } catch (MovementInvalidException e) {
            condition = !condition;
        }
        try {
            testFamilyMember.moveToPosition(position, 23);
        } catch (MovementInvalidException e) {
            condition = !condition;
        }

        assertTrue(condition);
    }

    @Test
    public void testFamilyMemberShouldHaveDiceValue() {
        FamilyMember familyMember = playerWithTurn.getSpecificFamilyMember(DiceColor.WHITE);
        Dice dice = this.gameBoard.getDiceByColor(DiceColor.WHITE);

        int diceValue = dice.getValue();

        assertEquals(diceValue, familyMember.getValue());
    }

    @Test
    public void testFamilyMemberShouldMoveToPositionAndOccupyIt() throws MovementInvalidException {
        FamilyMember familyMember = playerWithTurn.getSpecificFamilyMember(DiceColor.WHITE);
        Dice dice = this.gameBoard.getDiceByColor(DiceColor.BLACK);
        dice.throwDice();
        familyMember.setValueFromDice();
        ProductionWrapper position = (ProductionWrapper) gameBoard.getBoardSpaces().get(17);
        familyMember.moveToPosition(position, 0);
        assertTrue(position.isOccupied());
    }

    @Test
    public void testFamilyMemberShouldNotGetCardAfterLimitIsReached(){
        FamilyMember familyMember = playerWithTurn.getSpecificFamilyMember(DiceColor.WHITE);
        CardParser cardParser = new CardParser("src/config/Card.json");
        cardParser.parse();
        List<Card> playerCards = cardParser.getCards();
        boolean check = true;

        int i;
        for(i=0;i<6;i++){
            this.playerWithTurn.addCard(playerCards.get(i));
        }


        try {
            familyMember.moveToPosition(gameBoard.getSpaceById(1), 0);
            System.out.println("moving");
            check = false;
        } catch (MovementInvalidException e) {
            check = true;
        }

        assertTrue(check);
    }


    @Override
    public void tearDown() throws Exception {
        this.firstPlayer = null;
        this.gameBoard = null;
        this.gameInstance = null;
        this.playerWithTurn = null;
    }


}
