package nim;

import java.util.ArrayList;

public class Nim {
	private byte matchesLeft;
	private ArrayList<GameListener> listeners = new ArrayList<GameListener>();
	
	////////////
	// players /
	////////////
	private Player player1;
	private Player player2;
	public Player playerOnTurn;

	// human player
	private class HumanPlayer extends Player {
		public HumanPlayer(String name) {
			super(name, PlayerType.HUMAN);
		}
		
		public void pickMatches(byte n) throws InvalidNumberOfMatchesException {
			pickMatchesFromTable(n);
		}
	}
	
	// computer player
	private class ComputerPlayer extends Player {
		// picks the computer will make given a number of remaining matches
		private byte[] pickAmount = {1, 1, 2, 3, 4, 1, 1, 2, 3, 4, 1};
		
		public ComputerPlayer(String name) {
			super(name, PlayerType.COMPUTER);
		}
		
		public byte pickMatches() throws InvalidNumberOfMatchesException {
			byte n = pickAmount[matchesLeft - 1];
			pickMatchesFromTable(n);
			return n;
		}
	}
	
	///////////////////
	// game interface /
	///////////////////
	public void setupGame(boolean singlePlayerGame) {
		player1 = new HumanPlayer("player 1");
		player2 = singlePlayerGame ? new ComputerPlayer("computer") : new HumanPlayer("player 2");
		matchesLeft = 11;
	}
	
	// execute next turn
	public void nextTurn() {
		if (playerOnTurn == null)
			playerOnTurn = Math.random() < 0.5 ? player1 : player2; 
		playerOnTurn = playerOnTurn.equals(player1) ? player2 : player1;
		signalPlayersTurn();
	}
	
	// how many matches are left in the game?
	public int getMatchesLeft() {
		return matchesLeft;
	}
	
	// add listeners to the game
	public void addListener(GameListener listener) {
		listeners.add(listener);
	}
	
	////////////////////
	// private methods /
	////////////////////
	
	// have a player pick matches
	private void pickMatchesFromTable(byte n) throws InvalidNumberOfMatchesException {
		// check if pick is valid
		if (n > 0 && n <= 4 && n <= matchesLeft ) {
			matchesLeft -= n;
			if (matchesLeft == 0) {
				signalPlayerLooses();
			}
		} else {
			throw new InvalidNumberOfMatchesException();
		}
	}
	
	// fire event: players turn
	private void signalPlayersTurn() {
		if (playerOnTurn.getPlayerType() == PlayerType.COMPUTER) {
			// computers turn
			for (GameListener listener : listeners) {
				if (!(listener instanceof ComputersTurnListener)) continue;
				((ComputersTurnListener) listener).computersTurn(playerOnTurn);
			}
		} else {
			// human players turn
			for (GameListener listener : listeners) {
				if (!(listener instanceof HumansTurnListener)) continue;
				((HumansTurnListener) listener).humansTurn(playerOnTurn);
			}
		}
	}

	// fire event: player looses
	private void signalPlayerLooses() {
		for (GameListener listener : listeners) {
			if (!(listener instanceof PlayerLoosesListener)) continue;
			((PlayerLoosesListener) listener).playerLooses(playerOnTurn);
		}
	}
}
