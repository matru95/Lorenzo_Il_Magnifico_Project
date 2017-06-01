package it.polimi.ingsw.gc31.model;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceFactory;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

public class Player {
	private final UUID playerID;
	private final String playerName;
	private final PlayerColor playerColor;
	private GameBoard board;
	private int playerOrder; // From 1 to 4
	private Boolean isMovedThisTurn;

	private Map<String, Resource> res;
	
	//Cards
	private Card[] cards;
	private FaithCard[] faithCards;
	private FamilyMember[] familyMembers;

	private final Logger logger = Logger.getLogger(Player.class.getName());


	public Player(UUID playerID, String playerName, PlayerColor playerColor) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.playerColor = playerColor;

		this.isMovedThisTurn = false;
		
		res = new HashMap<>();

//		Cards
		this.cards = new Card[24];
		this.familyMembers = new FamilyMember[4];

		initResources();
	}

    @Override
    public String toString() {
	    return null;
	    //TODO
    }

    public void setGameBoard(GameBoard gameBoard) {
		this.board = gameBoard;
	}

    private void initResources() {

        for(ResourceName resourceName: ResourceName.values()) {
            String resourceNameString = resourceName.toString();
            try {
                int initialNumOf = resInitNumOf(resourceNameString);
                this.res.put(resourceNameString,
                        ResourceFactory.getResource(resourceNameString,
                                initialNumOf));
            } catch (NoResourceMatch noResourceMatch) {
            	logger.log(Level.SEVERE, noResourceMatch.getMessage());
            }
        }

    }

    private int resInitNumOf(String resourceNameString) throws NoResourceMatch {
	    switch (resourceNameString) {
            case "GOLD":
                return this.getPlayerOrder() + 4;
            case "WOOD":
                return 2;
            case "FAITHPOINTS":
                return 0;
            case "SERVANTS":
                return 3;
            case "STONE":
                return 2;
            case "VICTORYPOINTS":
                return 0;
            case "WARPOINTS":
                return 0;
            default:
                throw new NoResourceMatch();

        }
    }

	public void initFamilyMembers() {
	    int i = 0;
        for (DiceColor diceColor: DiceColor.values()) {
            this.familyMembers[i] = new FamilyMember(diceColor, this, board);
            i++;
        }
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
			if(color.equals(member.getColor())) {
				return member;
			}
		}
		return null;
	}

	public void doPlayerActions() {
	    //TODO
	}

	public UUID getPlayerID() {
		return playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public PlayerColor getPlayerColor() {
		return playerColor;
	}

    public Boolean getMovedThisTurn() {
        return isMovedThisTurn;
    }

	public FaithCard[] getFaithCards() {
		return faithCards;
	}

    public GameBoard getBoard() {
        return board;
    }

    public Map<String, Resource> getRes() {
        return res;
    }

    public Card[] getCards() {
        return cards;
    }

	public void setRes(Map<String, Resource> res) {
		this.res = res;
	}
}
