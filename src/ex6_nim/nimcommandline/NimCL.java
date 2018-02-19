package nimcommandline;

import nim.*;
import java.util.Scanner;

public class NimCL {
	private Nim game;
	
	/////////
	// test /
	/////////
	private class ComputersTurn implements ComputersTurnListener {
		public void computersTurn(Player player) {
			ExecuteTurn(player, false);
		}
	}
	
	private class HumansTurn implements HumansTurnListener {
		public void humansTurn(Player player) {
			ExecuteTurn(player, true);
		}
	}
	
	private void ExecuteTurn(Player player, boolean isHuman) {
		try {
			System.out.println("\nPlayer on turn: " + player.getName());
			if (isHuman) 
				player.pickMatches(UserInput.GetUserInputByte("How many matches do you want to take? "));
			else
				player.pickMatches();
			System.out.println("There are " + game.getMatchesLeft() + " matches left.");
			game.nextTurn();
		}
		catch (InvalidNumberOfMatchesException ex) {
			System.out.println("Invalid amount of matches... Pick again!");
			ExecuteTurn(player, isHuman);
		}
	}
	
	private class PlayerLooses implements PlayerLoosesListener {
		public void playerLooses(Player player) {
			System.out.println(String.format("%s took the last match.\n%<s looses!", player.getName()));
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		NimCL nimCommandLine = new NimCL();
		nimCommandLine.setupGame();
		nimCommandLine.startGame();
	}
	
	public void setupGame() {
		game = new Nim();
		
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
		game.addListener(new ComputersTurn());
		game.addListener(new HumansTurn());
		game.addListener(new PlayerLooses());
	}
	
	public void startGame() {
		game.nextTurn();
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