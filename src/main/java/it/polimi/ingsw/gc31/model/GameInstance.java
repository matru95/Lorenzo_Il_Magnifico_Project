package it.polimi.ingsw.gc31.model;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.enumerations.PlayerColor;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.exceptions.NoResourceMatch;
import it.polimi.ingsw.gc31.model.states.GamePrepState;
import it.polimi.ingsw.gc31.model.states.State;

public class GameInstance implements Serializable, Runnable {

	private GameBoard gameBoard;
	private ArrayList<Player> players;
	private List<PlayerColor> availableColors;

	private int age;
	private int turn;

	private UUID instanceID;

	private State state;

	private transient Logger logger = Logger.getLogger(GameInstance.class.getName());


	public GameInstance(UUID instanceID) {

		this.instanceID = instanceID;
		this.players = new ArrayList<>();

        //Initialize first turn and first age.
		age = 1;
		turn = 1;

		this.state = null;
		this.availableColors = PlayerColor.toList();
	}

    /**
     * Returns a String object formatted as JSON which has
     * all the relevant information about the current state of the game
     * @return JSON String
     */
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode gameInstanceNode = mapper.createObjectNode();

        gameInstanceNode.put("instanceID", instanceID.toString());
        gameInstanceNode.put("age", age);
        gameInstanceNode.put("turn", turn);
        gameInstanceNode.set("players", createPlayersNode(mapper));

        try {
            return  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(gameInstanceNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Returns an ArrayNode with an array of all the players in this instance.
     * @param mapper The ObjectMapper created in the parent method
     * @return the relevant JSON array with all the players
     * @see #toString()
     */
    private ArrayNode createPlayersNode(ObjectMapper mapper) {
        ArrayNode playersArray = mapper.createArrayNode();

        for(Player player: players) {
            playersArray.add(player.toJson());
        }

        return playersArray;
    }

	@Override
	public void run() {
	    this.generatePlayerOrders();

//	    Game preparation state
		this.state = new GamePrepState();

		try {
			this.state.doAction(this);
		} catch (NoResourceMatch noResourceMatch) {
			noResourceMatch.printStackTrace();
		}

	}

    public void addPlayer(Player player) {
        PlayerColor firstAvailableColor = availableColors.get(0);
        player.setPlayerColor(firstAvailableColor);
        availableColors.remove(firstAvailableColor);
        players.add(player);
	}

	public void generatePlayerOrders() {

	    //TODO We need a list of ints that have already been generated;
        ArrayList<Integer> generatedNumbers = new ArrayList<>();
        int rnd;

        for(int i = 0; i < this.players.size(); i++) {
            do {
                rnd = new Random().nextInt(this.players.size());
            } while (generatedNumbers.contains(rnd+1));

            generatedNumbers.add(rnd+1);

            this.players.get(i).setPlayerOrder(rnd+1);
        }
    }

	public GameBoard getGameBoard() {
		return this.gameBoard;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	public int getTurn() {

		return turn;
	}

	public int getAge() {

		return age;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public int getNumOfPlayers() {
		return players.size();
	}

	public UUID getInstanceID() {
		return instanceID;
	}

	public void setState(State state){
		this.state = state;
	}

	public State getState(){
		return state;
	}

	public Player getPlayerFromId(UUID playerID) {
	    Player player = null;

	    for(Player playerIter: players) {
	        if(playerIter.getPlayerID().equals(playerID)) {
	            player = playerIter;
            }
        }

        return player;
    }

	public void setTurn(int turn) {
		this.turn = turn;
	}
}
