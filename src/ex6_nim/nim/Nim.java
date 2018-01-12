package nim;

import java.util.Scanner;
import java.util.ArrayList;

public class Nim {
	private byte matchesLeft;
	private boolean singlePlayerGame;
	private ArrayList<GameListener> listeners = new ArrayList<GameListener>();
	
	// players
	private Player player1;
	private Player player2;
	private Player loser;
	public Player playerOnTurn;

	// classes
	private class HumanPlayer extends Player {
		public HumanPlayer(String name) {
			super(name);
		}
		
		public void pickMatches(byte n) throws InvalidNumberOfMatchesException {
			pickMatchesFromTable(n);
		}
	}
	
	private class ComputerPlayer extends Player {
		private byte[] pickAmount = {1, 1, 2, 3, 4, 1, 1, 2, 3, 4, 1};
		
		public ComputerPlayer(String name) {
			super(name);
		}
		
		public byte pickMatches() throws InvalidNumberOfMatchesException {
			byte n = pickAmount[matchesLeft - 1];
			pickMatchesFromTable(n);
			return n;
		}
	}
	
	/////////
	// test /
	/////////	
	public static void main(String[] args) {
		Nim game = new Nim();
		
		boolean singlePlayer = false;
		byte nrPlayers = 0;
		do {
			nrPlayers = UserInput.GetUserInputByte("One or two players : (1 - 2) ");
			if (nrPlayers == 1) 
				singlePlayer = true;
			else if (nrPlayers == 2)
				singlePlayer = false;
		} while (nrPlayers != 1 && nrPlayers != 2);
		game.setupGame(singlePlayer);
		playGame(game);
	}
	
	private static void playGame(Nim game) {
		while (takeAnotherTurn(game)) {};
	}
	
	private static boolean takeAnotherTurn(Nim game) {
		System.out.println("\nPlayer on turn: " + game.playerOnTurn.getName());
		while (!validTurn(game)) {};
		System.out.println("There are " + game.getMatchesLeft() + " matches left.");
		if (game.doWeHaveALoser()) {
			System.out.println(String.format("%s took the last match.\n%<s looses!", game.getLoser()));
			return false;
		}
		game.nextTurn();
		return true;
	}
	
	private static boolean validTurn(Nim game) {
		try {
			if (game.playerOnTurn.getName() == "computer")
				game.playerOnTurn.pickMatches();
			else
				game.playerOnTurn.pickMatches(UserInput.GetUserInputByte("How many matches do you want to take? "));
			return true;
		}
		catch (InvalidNumberOfMatchesException ex) {
			System.out.println("Invalid amount of matches... Pick again!");
			return false;
		}
	}
	
	//////////////
	// interface /
	//////////////	
	public void setupGame(boolean singlePlayerGame) {
		player1 = new HumanPlayer("player 1");
		player2 = singlePlayerGame ? new ComputerPlayer("computer") : new HumanPlayer("player 2");
		this.singlePlayerGame = singlePlayerGame;
		matchesLeft = 11;
		loser = null;
	}
	
	private void pickMatchesFromTable(byte n) throws InvalidNumberOfMatchesException {
		if (n > 0 && n <= 4 && n <= matchesLeft ) {
			matchesLeft -= n;
			if (matchesLeft == 0) {
				loser = playerOnTurn;
			}
		} else {
			throw new InvalidNumberOfMatchesException();
		}
	}
	
	public void nextTurn() {
		if (playerOnTurn == null)
			playerOnTurn = Math.random() < 0.5 ? player1 : player2; 
		playerOnTurn = playerOnTurn.equals(player1) ? player2 : player1;
		signalComputersTurn();
	}
	
	public void signalComputersTurn() {
		if (playerOnTurn.getName() == "computer") {
			for (GameListener listener : listeners) {
				listener.computersTurn();
			}
		}
	}
	
	public int getMatchesLeft() {
		return matchesLeft;
	}
	
	public boolean doWeHaveALoser() {
		return loser != null;
	}
	
	public String getLoser() {
		return loser.getName();
	}
	
	public void addListener(GameListener listener) {
		listeners.add(listener);
	}
}

//helper class
final class UserInput {
	private UserInput() {};
	private static Scanner reader = new Scanner(System.in);
	
	// Get integer from user
	public static int GetUserInputInteger(String prompt) {
		String s = GetUserInputString(prompt);
		try {
			return Integer.parseInt(s);
		}
		catch (NumberFormatException ex) {
			System.out.println("Sorry, but \"" + s + "\" appears not to be an integer...\nTry again please!");
			return GetUserInputInteger(prompt);
		}
	}
	
	public static byte GetUserInputByte(String prompt) {
		String s = GetUserInputString(prompt);
		try {
			return Byte.parseByte(s);
		}
		catch (NumberFormatException ex) {
			System.out.println("Sorry, but \"" + s + "\" appears not to be a byte...\nTry again please!");
			return GetUserInputByte(prompt);
		}
	}
	
	// Get string from user
	public static String GetUserInputString(String prompt) {
		System.out.print(prompt);
		return reader.nextLine();
	}
}
