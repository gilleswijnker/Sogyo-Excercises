package nim;

public abstract class Player {
	private String name;
	private PlayerType playertype;
	
	public Player(String name, PlayerType playertype) {
		this.name = name;
		this.playertype = playertype;
	}
	
	public void pickMatches(byte n) throws InvalidNumberOfMatchesException {};
	public byte pickMatches() throws InvalidNumberOfMatchesException {
		return 0;
	};
	
	public String getName() {
		return name;
	}
	
	public PlayerType getPlayerType() {
		return playertype;
	}
}

enum PlayerType {
	HUMAN, COMPUTER
}