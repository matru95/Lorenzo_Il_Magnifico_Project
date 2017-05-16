package it.polimi.ingsw.GC_31;

import java.io.Console;
import java.util.Scanner;

public class GameInstance {
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
		
		age = 1;
		turn = 1;
	}
	
	private int askNumOfPlayers() {
		System.out.println("Quanti giocatori ci sono?");
		Scanner in = new Scanner(System.in);
		int numOfPlayers = in.nextInt();
		return numOfPlayers;
		
	}
	
	private Player askPlayerNameAndColor(int id) {
		Scanner in = new Scanner(System.in);
		String color;
		
//		TODO Add current player number
		System.out.println("Come si chiama il giocatore?");
		String name = in.next();
		
		while(true) {
//			TODO 
			System.out.println("Quale colore ha?");
			color = in.next();
			if(PlayerColor.contains(color)) {
				break;
			}
		}
		
		Player player = new Player(id, name, PlayerColor.valueOf(color));
		return player;
		
		
	}
}
