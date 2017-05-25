package it.polimi.ingsw.gc31.model;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceFactory;

enum PlayerColor {
	RED, BLUE, GREEN, YELLOW;
	
	public static Boolean contains(String value) {
		String upperCaseValue = value.toUpperCase();
		for(PlayerColor color : PlayerColor.values()) {
			if(color.name().equals(upperCaseValue)) {
				return true;
			}
		}
		return false;
	}
}

public class Player {
	private final int playerID;
	private final String playerName;
	private final PlayerColor playerColor;
	private final GameBoard board;
	private int playerOrder;
	private Boolean movedThisTurn;
	private Map<String, Resource> res;
	
//	Cards
	private Card[] cards;
	private FaithCard[] faithCards;
	private FamilyMember[] familyMembers;

	
	public Player(int playerID, String playerName, PlayerColor playerColor, GameBoard board) throws NoResourceMatch {
		this.playerID = playerID;
		this.playerName = playerName;
		this.playerColor = playerColor;
		this.board = board;

		this.movedThisTurn = false;
		
		res = new HashMap<>();
        res.put("Gold", ResourceFactory.getResource("Gold", 5));

//		Cards
		this.cards = new Card[24];
		this.familyMembers = new FamilyMember[4];
		
		initFamilyMembers();
	}
	
	private void initFamilyMembers() {
		this.familyMembers[0] = new FamilyMember(DiceColor.NEUTRAL, this.playerColor, true, board);
		this.familyMembers[1] = new FamilyMember(DiceColor.WHITE, this.playerColor, false, board);
		this.familyMembers[2] = new FamilyMember(DiceColor.BLACK, this.playerColor, false, board);
		this.familyMembers[3] = new FamilyMember(DiceColor.ORANGE, this.playerColor, true, board);
	}


	public void setPlayerOrder(int order) {
		this.playerOrder = order;
	}

	public int getPlayerOrder() {
	    return this.playerOrder;
    }

	public FamilyMember[] getFamilyMembers() {
		return familyMembers;
	}
	
	public FamilyMember getSpecificFamilyMember(DiceColor color) {
		for(FamilyMember member: familyMembers) {
			if(color.equals(member.getDiceColor())) {
				return member;
			}
		}
		return null;
	}

	public void doPlayerActions() {
	    // Temporary

		System.out.println("Doing actions as " + this.playerName);
	}
	
	
	public int getPlayerID() {
		return playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public PlayerColor getPlayerColor() {
		return playerColor;
	}
}
