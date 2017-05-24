package it.polimi.ingsw.gc_31.model;

import java.util.*;
import java.util.logging.Logger;

import it.polimi.ingsw.gc_31.model.board.GameBoard;

public class GameInstance {
	private Scanner in = new Scanner(System.in);
	private List<PlayerColor> availableColors = new LinkedList<>(Arrays.asList(PlayerColor.values()));

	private GameBoard gameBoard;
	private Player[] players;


	private int numOfPlayers;
	private int age;
	private int turn;


	public GameInstance() {


		numOfPlayers = askNumOfPlayers();
		players = new Player[numOfPlayers];
		
		for(int i=0; i<numOfPlayers; i++) {
			Player player = askPlayerNameAndColor(i);
			players[i] = player;
		}
		
//		Initialize first turn and first age.
		age = 1;
		turn = 1;

		in.close();
		
	}
	
	private int askNumOfPlayers() {
		System.err.println("Quanti giocatori ci sono?");
		
		try {
			int number = in.nextInt();
			System.err.println(number);
			
			if(number < 2) {
				System.err.println("Il numero minimo dei giocatori è 2");
				return askNumOfPlayers();
			} else if(number > 4) {
				System.err.println("Il numero massimo dei giocatori è 4");
				return askNumOfPlayers();
			} else {
				return number;
			}

		} catch(InputMismatchException e) {
//			Maybe find a way to handle this exception in a better way?
			System.out.println("Il numero dei giocatori deve essere un numero.");
			System.exit(1);
			return 0;
		} 

	}
	
	private Player askPlayerNameAndColor(int id) {
		int currentPlayer = id + 1;
		String colorName;
		PlayerColor chosenColor;
		
		System.out.println("Come si chiama il giocatore numero " + currentPlayer + "?");
		String playerName = in.next();
		
		while(true) {
			System.out.println("Quale colore ha?");
			
			for(PlayerColor c : availableColors) {
				System.out.println(c.name());
			}

			colorName = in.next();
			
			if(PlayerColor.contains(colorName)) {
				chosenColor = PlayerColor.valueOf(colorName.toUpperCase());
				
				if(availableColors.contains(chosenColor)) {
					availableColors.remove(chosenColor);
					break;
				} else {
					System.out.println("Quel colore è già stato scelto!");
				}

			}
		}
		
		return new Player(id, playerName, chosenColor, gameBoard);
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
