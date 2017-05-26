package it.polimi.ingsw.gc31.model;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.gc31.model.board.GameBoard;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.model.states.GamePrepState;
import it.polimi.ingsw.gc31.model.states.State;
import it.polimi.ingsw.gc31.model.states.TurnState;

public class GameInstance {

	private Scanner in = new Scanner(System.in);
	private List<PlayerColor> availableColors = new LinkedList<>(Arrays.asList(PlayerColor.values()));

	private GameBoard gameBoard;
	private Player[] players;

	private int numOfPlayers;
	private int age;
	private int turn;

	private State gamePrepState;
	private State turnState;

	private final Logger logger = Logger.getLogger(GameInstance.class.getName());

    //currentPlayer is for saving the order of players that use CouncilsPalace Bonus
    //TODO reinizializza playerOrder a 0 ogni turno
    private int playerOrder;

	public GameInstance() throws NoResourceMatch {

		numOfPlayers = askNumOfPlayers();
		players = new Player[numOfPlayers];
		
		for(int i = 0; i < numOfPlayers; i++) {
			Player player = askPlayerNameAndColor(i);
			players[i] = player;
		}
		
        //Initialize first turn and first age.
		age = 1;
		turn = 1;

        generatePlayerOrders();

		in.close();

		this.gamePrepState = new GamePrepState();
		gamePrepState.doAction(this);
        playerOrder = 0;
	}

	public void playGame() {
        this.turnState = new TurnState();
        turnState.doAction(this);
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
            this.players[i].setPlayerOrder(rnd+1);
        }
    }

	private int askNumOfPlayers() {
	    logger.log(Level.FINEST, "Quanti giocatori ci sono?");

		try {
			int number = in.nextInt();
			logger.log(Level.FINEST,"%s", String.valueOf(number));
			
			if(number < 2) {
				logger.log(Level.FINEST, "Il numero minimo dei giocatori è 2");
				return askNumOfPlayers();
			} else if(number > 4) {
				logger.log(Level.FINEST,"Il numero massimo dei giocatori è 4");
				return askNumOfPlayers();
			} else {
				return number;
			}

		} catch(InputMismatchException e) {
//			Maybe find a way to handle this exception in a better way?
			logger.log(Level.FINE,"Il numero dei giocatori deve essere un numero.");
			System.exit(1);
			return 0;
		} 

	}
	
	private Player askPlayerNameAndColor(int id) {
		int currentPlayer = id + 1;
		String colorName;
		PlayerColor chosenColor;
		
		logger.log(Level.FINEST, "Come si chiama il giocatore numero %d?", currentPlayer);
		String playerName = in.next();
		
		while(true) {
			logger.log(Level.FINEST, "Quale colore ha?");
			
			for(PlayerColor c : availableColors) {
				logger.log(Level.FINEST,"%s", c.name());
			}

			colorName = in.next();
			
			if(PlayerColor.contains(colorName)) {
				chosenColor = PlayerColor.valueOf(colorName.toUpperCase());
				
				if(availableColors.contains(chosenColor)) {
					availableColors.remove(chosenColor);
					break;
				} else {
					logger.log(Level.FINE,"Quel colore è già stato scelto!");
				}

			}
		}

		return new Player(id, playerName, chosenColor, gameBoard);
	}

    public void putPlayerInQueque(Player p) {
        p.setPlayerOrder(playerOrder);
        playerOrder++;
    }

	public GameBoard getGameBoard() {
		return this.gameBoard;
	}

	public int getTurn() {
		return turn;
	}

	public int getAge() {

		return age;
	}

	public Player[] getPlayers() {
		return players;
	}

	public int getNumOfPlayers() {
		return numOfPlayers;
	}

}
