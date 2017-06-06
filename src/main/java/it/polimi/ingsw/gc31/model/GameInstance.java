package it.polimi.ingsw.gc31.model;

import java.util.*;
import java.util.logging.Logger;

import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.states.GamePrepState;
import it.polimi.ingsw.gc31.model.states.State;
import it.polimi.ingsw.gc31.model.states.TurnState;

public class GameInstance {

	private GameBoard gameBoard;
	private ArrayList<Player> players;

	private int numOfPlayers;
	private int age;
	private int turn;


	private UUID instanceID;

	private State gamePrepState;
	private State turnState;

	private final Logger logger = Logger.getLogger(GameInstance.class.getName());

    //currentPlayer is for saving the order of players that use CouncilsPalace Bonus
    //TODO reinizializza playerOrder a 0 ogni turno
    private int playerOrder;

	public GameInstance(UUID instanceID) {

		this.instanceID = instanceID;
		this.players = new ArrayList<>();

        //Initialize first turn and first age.
		age = 1;
		turn = 1;

		this.gamePrepState = new GamePrepState();
		gamePrepState.doAction(this);
        playerOrder = 0;
	}

	public void playGame() {
		generatePlayerOrders();
        this.turnState = new TurnState();
        turnState.doAction(this);
    }

    public boolean addPlayer(Player player) {
		if(this.players.size() < 4) {
			players.add(player);
			return true;
		}
		return false;
	}

	private void generatePlayerOrders() {

//	    We need a list of ints that have already been generated;
        ArrayList<Integer> generatedNumbers = new ArrayList<>();
        int rnd;

        for(int i = 0; i < this.numOfPlayers; i++) {
            do {
                rnd = new Random().nextInt(this.numOfPlayers);
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
		return numOfPlayers;
	}

	public UUID getInstanceID() {
		return instanceID;
	}

}
