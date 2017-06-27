package it.polimi.ingsw.gc31.model;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.enumerations.DiceColor;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.parser.PlayerTileParser;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.enumerations.ResourceName;

public class Player implements Serializable {

	/**
	 * Comparator to compare Players based on their WarPoints
	 */
	public static final Comparator<Player> PlayerWarPointsComparator = (p1, p2) -> {

		Integer p1WarPoints = p1.getRes().get(ResourceName.WARPOINTS).getNumOf();
		Integer p2WarPoints = p2.getRes().get(ResourceName.WARPOINTS).getNumOf();

		//Descending order
		return p2WarPoints.compareTo(p1WarPoints);
	};

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
	private FaithTile[] faithCards;
	private FamilyMember[] familyMembers;

	//FatihTiles
	private List<FaithTile> excommunication;
	private final transient Logger logger = Logger.getLogger(Player.class.getName());


	public Player(UUID playerID, String playerName, PlayerColor playerColor) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.playerColor = playerColor;

		this.isMovedThisTurn = false;
		
		res = new HashMap<>();
		this.cards = new HashMap<>();

//		PlayerTile
        PlayerTileParser playerTileParser = new PlayerTileParser("src/config/PlayerTile.json");
        playerTileParser.parse();
        this.playerTile = playerTileParser.getPlayerTiles().get(0);

//		Cards
		this.familyMembers = new FamilyMember[4];

		initCards();
		initResources();

	}

	public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode playerNode = mapper.createObjectNode();

        playerNode.put("playerID", playerID.toString());
        playerNode.put("playerName", playerName);
        playerNode.put("playerColor", playerColor.toString());
        playerNode.put("playerOrder", playerOrder);
        playerNode.put("playerOrder", playerOrder);
        playerNode.put("isMovedThisTurn", isMovedThisTurn);
        playerNode.set("res", createResourceJson(mapper));
        playerNode.set("familyMembers", createFamilyMembersJson(mapper));
        playerNode.set("cards", createCardsJson(mapper));

        return playerNode;
    }

    @Override
    public String toString() {
	    ObjectMapper mapper = new ObjectMapper();
        ObjectNode playerNode = toJson();

		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(playerNode);
		} catch (JsonProcessingException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return "";
		}

	}

	private ArrayNode createFamilyMembersJson(ObjectMapper mapper) {
	    ArrayNode familyMembersArray = mapper.createArrayNode();

	    for(FamilyMember familyMember: familyMembers) {
	        familyMembersArray.add(familyMember.toJson());
        }

        return familyMembersArray;
    }

	private ObjectNode createResourceJson(ObjectMapper mapper) {
		ObjectNode playerResourceNode = mapper.createObjectNode();

		for(Map.Entry<ResourceName, Resource> singleResource: res.entrySet()) {
			playerResourceNode.put(singleResource.getKey().toString(), singleResource.getValue().getNumOf());
		}



		return playerResourceNode;
	}

	private ObjectNode createCardsJson(ObjectMapper mapper) {
	    ObjectNode cardsNode = mapper.createObjectNode();

	    for(Map.Entry<CardColor, List<Card>> singleCard: cards.entrySet()) {
            cardsNode.set(singleCard.getKey().toString(), createSingleCardRowJson(mapper, singleCard.getKey()));
        }

        return cardsNode;
    }

    /**
     * Creates and returns the single array for a card color the player has
     * @param mapper
     * @param color
     * @return Array of cards the player has of a certain color
     */
    private ArrayNode createSingleCardRowJson(ObjectMapper mapper, CardColor color) {
		ArrayNode cardsNode = mapper.createArrayNode();

		List<Card> cardField = cards.get(color);
        for(Card card: cardField) {
            cardsNode.add(card.toJson());
        }

		return cardsNode;
	}

	/**
	 * Setter for attribute "gameBoard" and init "familyMembers"
	 * @param gameBoard GameBoard
	 */
    public void setGameBoard(GameBoard gameBoard) {
		this.board = gameBoard;
		this.initFamilyMembers();
	}

	/**
	 * Method for init all objects Card ("cards") of the Player
	 */
	private void initCards() {
	    for(CardColor cardColor: CardColor.values()) {
	        this.cards.put(cardColor, new ArrayList<>());
        }
    }

	/**
	 * Method for init all objects Resource ("res") of the Player
	 */
	private void initResources() {

        for(ResourceName resourceName: ResourceName.values()) {
            int initialNumOf = resInitNumOf(resourceName);
            this.res.put(resourceName, new Resource(resourceName, initialNumOf));
        }
    }

	/**
	 * Method that init a Resource to an int value, based on his ResourceName.
	 * @param resourceName
	 * @return int: the initial  value of that Resource.
	 */
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

	/**
	 * Method that init familyMembers for a Player
	 */
	public void initFamilyMembers() {
	    int i = 0;
        for (DiceColor diceColor: DiceColor.values()) {
            this.familyMembers[i] = new FamilyMember(diceColor, this, board);
            i++;
        }
    }

	public void doPlayerActions() {
		logger.fine("Doing player Actions!");
	}

	/**
	 * Getter for attribute "numOfCards"
	 * @return int
	 */
    public int getNumOfCards(CardColor cardColor) {
		return cards.get(cardColor).size();
	}

	/**
	 * Setter for attribute "playerOrder"
	 * @param order int
	 */
	public void setPlayerOrder(int order) {
		this.playerOrder = order;
	}

	/**
	 * Getter for attribute "playerOrder"
	 * @return int
	 */
	public int getPlayerOrder() {
	    return this.playerOrder;
    }

	/**
	 * Getter for attribute "familyMembers"
	 * @return FamilyMember[]
	 */
	public FamilyMember[] getFamilyMembers() {
		return familyMembers;
	}

	/**
	 * Getter for a specific FamilyMember in "familyMembers", based on DiceColor.
	 * @param color DiceColor
	 * @return FamilyMember of that color
	 */
	public FamilyMember getSpecificFamilyMember(DiceColor color) {
		for(FamilyMember member: familyMembers) {
		    DiceColor familyMemberColor = member.getColor();
			if(color.equals(familyMemberColor)) {
				return member;
			}
		}
		return null;
	}

	/**
	 * Getter for attribute "playerID"
	 * @return UUID
	 */
	public UUID getPlayerID() {
		return playerID;
	}

	/**
	 * Getter for attribute "playerName"
	 * @return String
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Getter for attribute "playerColor"
	 * @return PlayerColor
	 */
	public PlayerColor getPlayerColor() {
		return playerColor;
	}

	/**
	 * Getter for attribute "isMovedThisTurn"
	 * @return Boolean
	 */
    public Boolean getMovedThisTurn() {
        return isMovedThisTurn;
    }

	/**
	 * Getter for attribute "faithCards"
	 * @return FaithTile[]
	 */
	public FaithTile[] getFaithCards() {
		return faithCards;
	}

	/**
	 * Getter for attribute "board"
	 * @return GameBoard
	 */
    public GameBoard getBoard() {
        return board;
    }

	/**
	 * Getter for attribute "cards"
	 * @return Map with a CardColor as Key and a List of objects Card as Value
	 */
    public Map<CardColor, List<Card>> getCards() {
        return cards;
    }

	/**
	 * Method for adding a Card to my "cards"
	 * @param card Card
	 */
	public void addCard(Card card){
		this.cards.get(card.getCardColor()).add(card);
	}

	/**
	 * Setter for attribute "res"
	 * @param res: Map with a ResourceName as Key and an object Resource as Value
	 */
	public void setRes(Map<ResourceName, Resource> res) {
		this.res = res;
	}

	/**
	 * Getter for attribute "res"
	 * @return Map with a ResourceName as Key and an object Resource as Value
	 */
	public Map<ResourceName, Resource> getRes() {
		return this.res;
	}

	/**
	 * Getter for attribute "playerTile"
	 * @return PlayerTile
	 */
	public PlayerTile getPlayerTile() {
		return playerTile;
	}

	/**
	 * Setter for attribute "playerTile"
	 * @param playerTile PlayerTile
	 */
	public void setPlayerTile(PlayerTile playerTile) {
		this.playerTile = playerTile;
	}

}
