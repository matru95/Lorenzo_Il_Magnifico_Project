package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.*;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.ProductionWrapper;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.board.TowerSpaceWrapper;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.parser.CardParser;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.states.State;
import it.polimi.ingsw.gc31.model.states.TurnState;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
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

        this.firstPlayer = new Player(UUID.randomUUID(), "Pippo", PlayerColor.BLUE);
        this.secondPlayer = new Player(UUID.randomUUID(), "Endi", PlayerColor.RED);

        this.gameInstance = new GameInstance(UUID.randomUUID());
        this.gameInstance.addPlayer(this.firstPlayer);
        this.gameInstance.addPlayer(this.secondPlayer);

        this.gameBoard = new GameBoard(gameInstance);

        this.firstPlayer.setGameBoard(gameBoard);
        this.secondPlayer.setGameBoard(gameBoard);

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
    public void testFamilyMemberShouldMoveToTowerSpacePositionAndGetCard() throws NoResourceMatch {
        FamilyMember familyMember = playerWithTurn.getSpecificFamilyMember(DiceColor.WHITE);

        List<SpaceWrapper> availableSpaces = familyMember.checkPossibleMovements();
        List<SpaceWrapper> availableTowerSpaces = new ArrayList<>();

        SpaceWrapper chosenPosition = gameBoard.getSpaceById(1);

        boolean condition = false;

        for(SpaceWrapper spaceWrapper: availableSpaces) {
            if(spaceWrapper.getPositionID() == chosenPosition.getPositionID()) {
                condition = true;
            }
        }

        assertTrue(condition);


    }

    @Test
    public void testFamilyMemberShouldHaveDiceValue() {
        FamilyMember familyMember = playerWithTurn.getSpecificFamilyMember(DiceColor.WHITE);
        Dice dice = this.gameBoard.getDiceByColor(DiceColor.WHITE);

        int diceValue = dice.getValue();
        familyMember.setValueFromDice();

        assertEquals(diceValue, familyMember.getValue());
    }

    @Test
    public void testFamilyMemberShouldHavePossibleMovements() {
        FamilyMember familyMember = playerWithTurn.getSpecificFamilyMember(DiceColor.WHITE);
        List<SpaceWrapper> possibleMovements = familyMember.checkPossibleMovements();
        assertTrue(possibleMovements.size() > 0);
    }

    @Test
    public void testFamilyMemberShouldMoveToPositionAndOccupyIt() throws NoResourceMatch {
        FamilyMember familyMember = playerWithTurn.getSpecificFamilyMember(DiceColor.WHITE);
        Dice dice = this.gameBoard.getDiceByColor(DiceColor.BLACK);
        dice.throwDice();
        familyMember.setValueFromDice();
        ProductionWrapper position = (ProductionWrapper) gameBoard.getBoardSpaces().get(17);
        familyMember.moveToPosition(position, 0);
        assertTrue(position.isOccupied());
    }

    @Test
    public void testFamilyMemberShouldNotReturnGreenTowerWrappers(){
        FamilyMember familyMember = playerWithTurn.getSpecificFamilyMember(DiceColor.WHITE);
        CardParser cardParser = new CardParser("src/config/Card.json");
        cardParser.parse();
        List<Card> playerCards = cardParser.getCards();

        int i;
        for(i=0;i<6;i++){
            this.playerWithTurn.addCard(playerCards.get(i));
        }

        boolean check = true;

        List <SpaceWrapper> availableWrapper = familyMember.checkPossibleMovements();

        for(SpaceWrapper spaceWrapper: availableWrapper){
            if(spaceWrapper.getClass() == TowerSpaceWrapper.class) {
                if(((TowerSpaceWrapper) spaceWrapper).getColor()==CardColor.GREEN) {
                    check=false;
                }
            }
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
