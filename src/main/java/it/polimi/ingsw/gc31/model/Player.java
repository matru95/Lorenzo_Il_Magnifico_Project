package it.polimi.ingsw.gc31.model;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

public class Player implements Serializable{
	private final UUID playerID;
	private final String playerName;
	private final PlayerColor playerColor;
	private GameBoard board;
	private int playerOrder; // From 1 to 4
	private Boolean isMovedThisTurn;

	private Map<ResourceName, Resource> res;
	private PlayerTile playerTile;
	
	//Cards
	private Map<CardColor, List<Card>> cards;
	private FaithCard[] faithCards;
	private FamilyMember[] familyMembers;

	private final transient Logger logger = Logger.getLogger(Player.class.getName());


	public Player(UUID playerID, String playerName, PlayerColor playerColor) throws NoResourceMatch {
		this.playerID = playerID;
		this.playerName = playerName;
		this.playerColor = playerColor;

		this.isMovedThisTurn = false;
		
		res = new HashMap<>();
		this.cards = new HashMap<>();

//		Cards
		this.familyMembers = new FamilyMember[4];

		initCards();
		initResources();

		//TODO playerTile parse
        this.playerTile = new PlayerTile(null, null);
	}

    @Override
    public String toString() {
	    return "";
	    //TODO
    }

    public void setGameBoard(GameBoard gameBoard) {
		this.board = gameBoard;
	}

	private void initCards() {
	    for(CardColor cardColor: CardColor.values()) {
	        this.cards.put(cardColor, new ArrayList<>());
        }
    }

    private void initResources() {

        for(ResourceName resourceName: ResourceName.values()) {
            int initialNumOf = resInitNumOf(resourceName);
            this.res.put(resourceName, new Resource(resourceName, initialNumOf));
        }
    }

    private int resInitNumOf(ResourceName resourceName) {

	    switch (resourceName) {
            case GOLD:
                return this.getPlayerOrder() + 4;
            case WOOD:
                return 2;
            case FAITHPOINTS:
                return 0;
            case SERVANTS:
                return 3;
            case STONE:
                return 2;
            case VICTORYPOINTS:
                return 0;
            case WARPOINTS:
                return 0;
            default:
                throw new NullPointerException();

        }
    }

	public void initFamilyMembers() {
	    int i = 0;
        for (DiceColor diceColor: DiceColor.values()) {
            this.familyMembers[i] = new FamilyMember(diceColor, this, board);
            i++;
        }
    }

    public int getNumOfCards(CardColor cardColor) {
		return cards.get(cardColor).size();
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

    public Map<CardColor, List<Card>> getCards() {
        return cards;
    }

	public void setRes(Map<ResourceName, Resource> res) {
		this.res = res;
	}

	public Map<ResourceName, Resource> getRes() {
		return this.res;
	}

	public static Comparator<Player> PlayerWarPointsComparator
			= (p1, p2) -> {

                Integer p1WarPoints = p1.getRes().get(ResourceName.WARPOINTS).getNumOf();
                Integer p2WarPoints = p2.getRes().get(ResourceName.WARPOINTS).getNumOf();

                //Descending order
                return p2WarPoints.compareTo(p1WarPoints);
            };
}
