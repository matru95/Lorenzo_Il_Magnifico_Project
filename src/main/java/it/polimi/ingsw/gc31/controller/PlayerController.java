package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.DiceColor;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.view.cli.GameView;

public class PlayerController implements PlayerControllerInterface {

    private Player playerModel;
    private GameView view;

    public PlayerController(Player playerModel) {
        this.playerModel = playerModel;
        view = new GameView(playerModel, this);
    }

    @Override
    public void moveFamilyMember(DiceColor diceColor, String boardSpaceKey, int numOfServantsPaid) throws NoResourceMatch {
        SpaceWrapper position = playerModel.getBoard().getBoardSpaces().get(boardSpaceKey);
        playerModel.getSpecificFamilyMember(diceColor).moveToPosition(position, numOfServantsPaid);
    }

    @Override
    public void takeFaithEffect() {
        //TODO
    }

    @Override
    public void useLeaderCard() {
        //TODO
    }

    @Override
    public void chooseParchementBonus() {
        //TODO
    }

}