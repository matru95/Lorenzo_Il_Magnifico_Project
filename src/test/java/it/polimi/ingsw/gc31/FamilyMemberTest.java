package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.*;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.board.ProductionWrapper;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.board.TowerSpaceWrapper;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.cards.CardParser;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class FamilyMemberTest extends TestCase{
    private Player player;
    private Player secondPlayer;
    private GameBoard gameBoard;
    private GameInstance gameInstance;
    private FamilyMember familyMember;

    @Override
    public void setUp() throws Exception{

        this.player = new Player(UUID.randomUUID(), "Pippo", PlayerColor.BLUE);
        this.secondPlayer = new Player(UUID.randomUUID(), "Endi", PlayerColor.RED);

        Player[] players = new Player[2];
        players[0] = this.player;
        players[1] = this.secondPlayer;

        this.gameInstance = new GameInstance(UUID.randomUUID());
        this.gameInstance.addPlayer(this.player);
        this.gameInstance.addPlayer(this.secondPlayer);

        this.gameBoard = new GameBoard(gameInstance);

        this.player.setGameBoard(gameBoard);
        this.secondPlayer.setGameBoard(gameBoard);

        this.gameInstance.setGameBoard(gameBoard);

        this.familyMember = this.player.getSpecificFamilyMember(DiceColor.BLACK);
        gameInstance.run();

    }

    @Test
    public void testFamilyMemberShouldExist() {
        System.out.println(gameBoard.toString());
        assertNotNull(this.familyMember);
    }

    @Test
    public void testFamilyMemberShouldMoveToTowerSpacePositionAndGetCard() throws NoResourceMatch {
        List<SpaceWrapper> availableSpaces = familyMember.checkPossibleMovements();
        List<SpaceWrapper> availableTowerSpaces = new ArrayList<>();
        int positionID;
        Card chosenCard = null;

        for(SpaceWrapper spaceWrapper: availableSpaces) {
            if(spaceWrapper.getClass() == TowerSpaceWrapper.class) {
                availableTowerSpaces.add(spaceWrapper);
            }
        }

        chosenCard = ((TowerSpaceWrapper) availableTowerSpaces.get(0)).getCard();
        familyMember.moveToTower((TowerSpaceWrapper) availableTowerSpaces.get(0), 0);

        Card playerCard = player.getCards().get(chosenCard.getCardColor()).get(0);

        assertEquals(playerCard.getCardID(), chosenCard.getCardID());

    }

    @Test
    public void testFamilyMemberShouldHaveDiceValue() {
        Dice dice = this.gameBoard.getDiceByColor(DiceColor.BLACK);
        dice.throwDice();
        int diceValue = dice.getValue();
        familyMember.setValueFromDice();
        assertEquals(diceValue, familyMember.getValue());
    }

    @Test
    public void testFamilyMemberShouldHavePossibleMovements() {
        List<SpaceWrapper> possibleMovements = familyMember.checkPossibleMovements();
        assertTrue(possibleMovements.size() > 0);
    }

    @Test
    public void testFamilyMemberShouldMoveToPositionAndOccupyIt() throws NoResourceMatch {
        Dice dice = this.gameBoard.getDiceByColor(DiceColor.BLACK);
        dice.throwDice();
        familyMember.setValueFromDice();
        ProductionWrapper position = (ProductionWrapper) gameBoard.getBoardSpaces().get(17);
        familyMember.moveToPosition(position, 0);
        assertTrue(position.isOccupied());
    }

    @Test
    public void testFamilyMemberShouldNotReturnGreenTowerWrappers(){
        CardParser cardParser = new CardParser("src/config/Card.json");
        cardParser.parse();
        List<Card> playerCards = cardParser.getCards();

        int i;
        for(i=0;i<6;i++){
            this.player.addCard(playerCards.get(i));
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
        this.familyMember = null;
        assertNull(this.familyMember);
    }


}
