package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.DiceColor;

public interface PlayerControllerInterface {
    void moveFamilyMember(DiceColor diceColor, String boardSpaceKey);
    void takeFaithEffect();
    void useLeaderCard();
    void chooseParchementBonus();
}