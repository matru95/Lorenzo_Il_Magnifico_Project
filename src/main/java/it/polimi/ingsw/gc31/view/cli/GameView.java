package it.polimi.ingsw.gc31.view.cli;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import it.polimi.ingsw.gc31.controller.PlayerController;
import it.polimi.ingsw.gc31.model.DiceColor;
import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.cards.CardParser;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.ResourceName;
import it.polimi.ingsw.gc31.view.parser.CardViewParser;
import it.polimi.ingsw.gc31.view.parser.ViewParser;

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
        printHeader();
        printBoard();
        printPlayer();
        printFamilyMembers();
        //printParchementQuery();
    }

    private void printHeader() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("GameID: [" + gameBoardModel.getGameInstance().getInstanceID() + "]", "AGE:[" + gameBoardModel.getGameInstance().getAge() + "] TURN:[" + gameBoardModel.getGameInstance().getTurn() + "]");
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(51).add(51));
        System.out.println(at.render() + "\n");
    }

    private void printBoard() {

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("GREEN TOWER", "BLUE TOWER", "YELLOW TOWER", "PURPLE TOWER");
        at.addRule();
        ViewParser cardParser = new CardViewParser();

        for (int floorID = 3; floorID >= 0; floorID--) {

            String greenCardString = gameBoardModel.getTowerByColor(CardColor.GREEN).getTowerSpace().get(floorID).getCard().toString();
            String blueCardString = gameBoardModel.getTowerByColor(CardColor.BLUE).getTowerSpace().get(floorID).getCard().toString();
            String yellowCardString = gameBoardModel.getTowerByColor(CardColor.YELLOW).getTowerSpace().get(floorID).getCard().toString();
            String purpleCardString = gameBoardModel.getTowerByColor(CardColor.PURPLE).getTowerSpace().get(floorID).getCard().toString();
            at.addRow(cardParser.parse(greenCardString), cardParser.parse(blueCardString), cardParser.parse(yellowCardString), cardParser.parse(purpleCardString));
            at.addRule();
        }

        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(25).add(25).add(25).add(25));
        System.out.println(at.render() + "\n");
    }

    private void printPlayer() {

        AsciiTable at = new AsciiTable();
        at.addRule();

        at.addRow(null, null, null, "PLAYERS");
        at.addRule();

        at.addRow("[" + playerModel.getPlayerColor() + "]: " + playerModel.getPlayerName(),
                "[" + playerModel.getPlayerColor() + "]: " + playerModel.getPlayerName(),
                "[" + playerModel.getPlayerColor() + "]: " + playerModel.getPlayerName(),
                "[" + playerModel.getPlayerColor() + "]: " + playerModel.getPlayerName()
                );
        at.addRule();
        for(ResourceName resName: ResourceName.values()) {
            at.addRow(resName.toString() + ":[" + playerModel.getRes().get(resName).getNumOf() + "]",
                    resName.toString() + ":[" + playerModel.getRes().get(resName).getNumOf() + "]",
                    resName.toString() + ":[" + playerModel.getRes().get(resName).getNumOf() + "]",
                    resName.toString() + ":[" + playerModel.getRes().get(resName).getNumOf() + "]"
                    );
            at.addRule();
        }
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(25).add(25).add(25).add(25));
        System.out.println(at.render() + "\n");
    }

    private void printFamilyMembers() {
        AsciiTable at = new AsciiTable();
        at.addRule();

        at.addRow(null, null, null, "MY FAMILY MEMBERS");
        at.addRule();

        at.addRow("BLACK:[" + playerModel.getSpecificFamilyMember(DiceColor.BLACK).getValue() + "]",
                "WHITE:["+ playerModel.getSpecificFamilyMember(DiceColor.WHITE).getValue() + "]",
                "ORANGE:["+ playerModel.getSpecificFamilyMember(DiceColor.ORANGE).getValue() + "]",
                "NEUTRAL:["+ playerModel.getSpecificFamilyMember(DiceColor.NEUTRAL).getValue() + "]"
        );
        at.addRule();
        at.addRow(playerModel.getSpecificFamilyMember(DiceColor.BLACK).getCurrentPosition().getPositionID(),
                playerModel.getSpecificFamilyMember(DiceColor.WHITE).getCurrentPosition().getPositionID(),
                playerModel.getSpecificFamilyMember(DiceColor.ORANGE).getCurrentPosition().getPositionID(),
                playerModel.getSpecificFamilyMember(DiceColor.NEUTRAL).getCurrentPosition().getPositionID()
        );
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(25).add(25).add(25).add(25));
        System.out.println(at.render() + "\n");

    }

    private void printParchementQuery() {
        System.out.println("Choose a BONUS for parchement between this: \n" +
            "Type 0 for: 1 Wood && 1 Stone \n" +
            "Type 1 for: 2 Servants \n" +
            "Type 2 for: 2 Golds \n" +
            "Type 3 for: 2 Military Points \n" +
            "Type 4 for: 1 Faith Points \n");
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