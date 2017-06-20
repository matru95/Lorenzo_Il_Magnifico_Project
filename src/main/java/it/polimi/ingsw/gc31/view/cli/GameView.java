package it.polimi.ingsw.gc31.view.cli;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import it.polimi.ingsw.gc31.controller.PlayerController;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.cards.CardParser;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.util.List;
import java.util.UUID;

public class GameView implements GameBoardObserver, GameInstanceObserver, PlayerObserver {

    private GameBoard gameBoardModel;
    private Player playerModel;
    private PlayerController playerCtrl;

    public static List<Card> cards;

    public GameView(Player playerModel, PlayerController playerCtrl) {
        this.gameBoardModel = playerModel.getBoard();
        this.playerModel = playerModel;
        this.playerCtrl = playerCtrl;
    }

    public void printView() {
        printBoard();
        printPlayer();
    }

    private void printBoard() {

        AsciiTable at = new AsciiTable();
        at.addRule();

        for (int floorID = 3; floorID >= 0; floorID--) {
            /*
            at.addRow(gameBoardModel.getTowerByColor(CardColor.GREEN).getTowerSpace().get(floorID).getCard().toString(),
                      gameBoardModel.getTowerByColor(CardColor.BLUE).getTowerSpace().get(floorID).getCard().toString(),
                      gameBoardModel.getTowerByColor(CardColor.YELLOW).getTowerSpace().get(floorID).getCard().toString(),
                      gameBoardModel.getTowerByColor(CardColor.PURPLE).getTowerSpace().get(floorID).getCard().toString()
                    );*/
            at.addRow(cards.get(0).toString(), cards.get(1).toString(), cards.get(2).toString(), cards.get(3).toString());
            at.addRule();
        }

        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(20).add(20).add(20).add(20));
        System.out.println(at.render() + "\n");
    }

    private void printPlayer() {
        for(ResourceName resName: ResourceName.values()) {
            System.out.println(resName.toString() +
                    ":[" + playerModel.getRes().get(resName).getNumOf() + "]");
        }
        System.out.println();
    }

    private void printParchementQuery() {
        //TODO
        /**
        System.out.println("Choose a BONUS for parchement between this: \n" +
                "1)1 gold and etc" +
                "2)Mi servono le carte" +
                "3)Roba buona" +
                "4)Bella raga ");   */
    }

    private void printFaithEffect() {
        //TODO
        /**
        System.out.println("Do you really wanna take this faith effect?" +
                gameBoardModel.getFaithCard.getEffect.toString());  */
    }

    @Override
    public void getKeyboardInput() {
        //TODO
    }

    @Override
    public void updateBoard() {
        //TODO
    }

    public static void main(String[] args) throws NoResourceMatch {
        Player p = new Player(UUID.randomUUID(), "MATRU", PlayerColor.BLUE);
        GameInstance gameInstance = new GameInstance(UUID.randomUUID());
        gameInstance.addPlayer(p);
        GameBoard gameBoard = new GameBoard(gameInstance);
        gameInstance.setGameBoard(gameBoard);
        p.setGameBoard(gameBoard);
        PlayerController playerController = new PlayerController(p);
        GameView view = new GameView(p, playerController);
        (new Thread(gameInstance)).start();
        CardParser cardParser = new CardParser("src/config/Card.json");
        cardParser.parse();
        cards = cardParser.getCards();
        view.printView();
    }
}