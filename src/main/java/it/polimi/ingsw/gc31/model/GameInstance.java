package it.polimi.ingsw.gc31.model;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.states.GamePrepState;
import it.polimi.ingsw.gc31.model.states.State;
import it.polimi.ingsw.gc31.model.states.TurnState;

public class GameInstance implements Serializable, Runnable {

	private GameBoard gameBoard;
	private ArrayList<Player> players;

	private int age;
	private int turn;

	private UUID instanceID;

	private State state;

	private transient Logger logger = Logger.getLogger(GameInstance.class.getName());

    //currentPlayer is for saving the order of players that use CouncilsPalace Bonus
    //TODO reinizializza playerOrder a 0 ogni turno
    private int playerOrder;

	public GameInstance(UUID instanceID) {

		this.instanceID = instanceID;
		this.players = new ArrayList<>();

        //Initialize first turn and first age.
		age = 1;
		turn = 1;

		this.state = null;
        playerOrder = 0;

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
            playersArray.add(player.toString());
        }

        return playersArray;
    }

	@Override
	public void run() {
	    this.generatePlayerOrders();
//	    Game preparation state
		this.state = new GamePrepState();
		this.state.doAction(this);

//		Turns
        this.state = new TurnState();
        this.state.doAction(this);
	}

    public boolean addPlayer(Player player) {
		if(this.players.size() < 4) {
			players.add(player);
			return true;
		}
		return false;
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

    public void putPlayerInQueue(Player p) {
        p.setPlayerOrder(playerOrder);
        playerOrder++;
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
}
