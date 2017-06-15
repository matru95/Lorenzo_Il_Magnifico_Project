package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.DiceColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

public interface PlayerControllerInterface {
    void moveFamilyMember(DiceColor diceColor, String boardSpaceKey, int paidFamilyMembers) throws NoResourceMatch;
    void takeFaithEffect();
    void useLeaderCard();
    void chooseParchementBonus();
}