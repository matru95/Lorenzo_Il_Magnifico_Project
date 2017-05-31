package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.*;

import java.util.UUID;

public class PlayerController {
    private Player model;

    public PlayerController(Player model) {
        this.model = model;
    }

    public void setPlayerOrder(int order) {

        this.model.setPlayerOrder(order);
    }

    public int getPlayerOrder() {

        return this.model.getPlayerOrder();
    }

    public FamilyMember[] getFamilyMembers() {

        return this.model.getFamilyMembers();
    }

    public FamilyMember getSpecificFamilyMember(DiceColor color) {

        return this.model.getSpecificFamilyMember(color);
    }

    public UUID getPlayerID() {

        return this.model.getPlayerID();
    }

    public String getPlayerName() {

        return this.model.getPlayerName();
    }

    public PlayerColor getPlayerColor() {

        return this.model.getPlayerColor();
    }

    public Boolean getMovedThisTurn() {

        return this.model.getMovedThisTurn();
    }

    public FaithCard[] getFaithCards() {

        return this.model.getFaithCards();
    }
}
