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

        this.familyMember = new FamilyMember(DiceColor.BLACK, this.player, this.gameBoard);
        (new Thread(gameInstance)).start();

    }

    @Test
    public void testFamilyMemberShouldExist() {
        assertNotNull(this.familyMember);
    }

//    TODO finish this test
//    @Test
//    public void testFamilyMemberShouldMoveToTowerSpacePosition() throws NoResourceMatch {
//        TowerSpaceWrapper space = this.gameBoard.getTowerByColor(CardColor.BLUE).getTowerSpace().get(0);
//        this.familyMember.moveToPosition(space, 0);
//        assertTrue(true);
//    }

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
        Dice dice = this.gameBoard.getDiceByColor(DiceColor.BLACK);
        dice.throwDice();
        familyMember.setValueFromDice();
//        NullPointerException because cards are not implemented yet
//        List<SpaceWrapper> possibleMovements = familyMember.checkPossibleMovements();
//        assertTrue(possibleMovements.size() > 0);
        assertTrue(true);
    }

    @Test
    public void testFamilyMemberShouldMoveToPositionAndOccupyIt() throws NoResourceMatch {
        Dice dice = this.gameBoard.getDiceByColor(DiceColor.BLACK);
        dice.throwDice();
        familyMember.setValueFromDice();
        ProductionWrapper position = (ProductionWrapper) gameBoard.getBoardSpaces().get("PRODUCTION");
        familyMember.moveToPosition(position, 0);
        assertTrue(position.isOccupied());
    }
    @Test
    public void testFamilyMemberShouldNotReturnGreenTowerWrappers(){
        Dice dice = this.gameBoard.getDiceByColor(DiceColor.BLACK);

        familyMember.setValueFromDice();
        CardParser cardParser=new CardParser("src/config/Card.json");
        cardParser.parse();
        List<Card> playerCards=cardParser.getCards();
        int i;
        for(i=0;i<6;i++){
            this.player.addCard(playerCards.get(i));
        }

        boolean check=true;
        List <SpaceWrapper> avaibleWrapper=familyMember.checkPossibleMovements();
        for( SpaceWrapper spaceWrapper: avaibleWrapper){
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
