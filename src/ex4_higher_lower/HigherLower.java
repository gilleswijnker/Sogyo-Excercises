import java.util.InputMismatchException;
import java.util.Scanner;

/* Guessing game
 * Deviated from original assignment to practice all kind of things
 */

public class HigherLower {
	public static void main (String[] args) {
		GuessingGame game = initializeGame(args);
		game.playGame();
	}
	
	// initialize the game
	private static GuessingGame initializeGame(String[] args) {
		switch (args.length) {
			case 0: return new GuessingGame();
			
			case 1: return new GuessingGame(Integer.parseInt(args[0]));
			
			case 2: return new GuessingGame(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
					
			default: throw new IllegalArgumentException("Too many arguments:\nExpecting 0, 1 or 2 arguments");
		}
	}
}

// the actual game
class GuessingGame {
	private int toGuess;
	private int guessesLeft;
	private GeneralPlayer player1;
	private GeneralPlayer player2;
	private enum GuessOutcome {
		HIGHER, LOWER, CORRECT
	}

	// constructors
	public GuessingGame(int upperBound, int maxGuesses) {
		// initialize players
		player1 = new Player();
		player2 = new Player();
		player1.setName(UserInput.GetUserInputString("Give player 1 name: "));
		player2.setName(UserInput.GetUserInputString("Give player 2 name: "));
		
		// initialize numbers
		toGuess = (int) (Math.random() * upperBound) + 1;
		System.out.println("Number is: " + toGuess); // for test purposes only
		guessesLeft = maxGuesses;
	}
	
	public GuessingGame(int upperBound) {
		this(upperBound, upperBound);
	}
	
	public GuessingGame() {
		this(50, 50);
	}
	
	// play the game
	public void playGame() {
		// choose random player to start
		GeneralPlayer playerOnTurn = Math.random() < 0.5 ? player1 : player2;
		
		while (takeAnotherTurn(playerOnTurn)) {
			// switch player
			playerOnTurn = playerOnTurn.equals(player1) ? player2 : player1;
		};
	}
	
	/* Single turn:
	 *   show the game status
	 *   have a player guess the number
	 *   print the result
	 */
	private boolean takeAnotherTurn(GeneralPlayer playerOnTurn) {
		showGameStatus(guessesLeft, playerOnTurn.getName());
		GuessOutcome result = doGuess(playerOnTurn); 
		
		if (result == GuessOutcome.CORRECT) {
			System.out.println("Yeay! You've got it right");
			return false;
		} else if (--guessesLeft == 0) {
			// out of guesses
			System.out.println("No more guesses left...\nThe number was " + toGuess);
			return false;
		} else {
			// wrong guess
			String higherOrLower = result.toString().toLowerCase();
			System.out.println("Nope, the number I've got in mind is " + higherOrLower);
			return true;
		}
	}
	
	// check the player's guess
	private GuessOutcome doGuess(GeneralPlayer playerOnTurn) {
		int guess = playerOnTurn.Guess();
		if (guess == toGuess) {
			return GuessOutcome.CORRECT;
		} else if (guess < toGuess) {
			return GuessOutcome.HIGHER;
		} else {
			return GuessOutcome.LOWER;
		}
	}
	
	// print game status
	private void showGameStatus(int guessesLeft, String name) {
		String prompt = String.format("\nGuesses left: %s\nPlayer on turn: %s", guessesLeft, name);
		System.out.println(prompt);
	}
}

// Interface the game expects a player to have
interface GeneralPlayer {
	public abstract void setName(String name);
	public abstract String getName();
	public abstract int Guess();
}

// implementation of player
class Player implements GeneralPlayer {
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	// player makes a guess
	public int Guess() {
		return UserInput.GetUserInputInteger("Make a guess: ");
	}
}

// helper class
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
	
	// Get string from user
	public static String GetUserInputString(String prompt) {
		System.out.print(prompt);
		return reader.nextLine();
	}
}
