package nimgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import nim.*;
import java.util.Enumeration;
import java.util.ArrayList;

public class NimGuiGame {
	private JFrame frame;
	private JButton buttonPick;
	private JLabel gameProgress;
	private JLabel matchesLeft;
	private Nim game;
	private ArrayList<GuiGameListener> listeners = new ArrayList<GuiGameListener>();
	
	// radio buttons
	private ButtonGroup group;
	ArrayList<JRadioButton> options = new ArrayList<JRadioButton>();
	
	//////////////
	// listeners /
	//////////////
	
	// listen to the game signaling it's the computers turn
	private class ComputersTurn implements ComputersTurnListener {
		public void computersTurn(Player player) {
			try {
				byte n = player.pickMatches();
				gameProgress.setText("Computer took " + n + " matches");
			}
			catch (InvalidNumberOfMatchesException ex) {
				// cannot happen in GUI
			}
			frame.repaint();
			game.nextTurn();
		};
	}
	
	// listen to the game signaling it's a human players turn
	private class HumansTurn implements HumansTurnListener {
		public void humansTurn(Player player) {
			gameProgress.setText("Turn: " + player.getName());
			frame.repaint();
		}
	}
	
	// listen to the game indicating we've got a loser
	private class PlayerLooses implements PlayerLoosesListener {
		public void playerLooses(Player player) {
			gameProgress.setText("Player " + player.getName() + " looses!");
			frame.repaint();
		}
	}
	
	// listen to the game window being closed
	private class CloseWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent event) {
			for (GuiGameListener l: listeners) {
				l.guiGameClosed();
			}
            event.getWindow().dispose();
		}
	}

	// listen to the player clicking the 'pick' button
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (!executeTurn()) return;
			frame.repaint();
		}
	}
	
	////////////
	// methods /
	////////////
	
	// execute a human player's turn
	private boolean executeTurn() {
		byte numberMatches = getNumberMatches();
		if (numberMatches == 0) return false;

		try {
			game.playerOnTurn.pickMatches(numberMatches);
		}
		catch (InvalidNumberOfMatchesException ex) {
			// cannot happen in GUI
		}
		return true;
	}
	
	// have a human player pick a number of matches
	private byte getNumberMatches() {
		// Which radio button is selected
		for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            JRadioButton button = (JRadioButton) buttons.nextElement();

            if (button.isSelected())
                return Byte.parseByte(button.getText());
        }
		// No button selected
		return 0;
	}
	
	// play the game
	public void play(boolean singlePlayerGame) {
		game = new Nim();
		game.addListener(new ComputersTurn());
		game.addListener(new HumansTurn());
		game.addListener(new PlayerLooses());
		game.setupGame(singlePlayerGame);
		setupGui();
		game.nextTurn();
	}
	
	// subscribe listeners to events from the GUI
	public void addListener(GuiGameListener listener) {
		listeners.add(listener);
	}
	
	////////
	// GUI /
	////////
	
	// the panel which contains all game elements
	private class GamePanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(Color.GRAY);
			g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
			
			// update information on screen
			for (byte b = 1; b <= 4; b++) {
				options.get(b - 1).setEnabled(b <= game.getMatchesLeft());
			}
			group.clearSelection();
			matchesLeft.setText(game.getMatchesLeft() + " matches left");
		}
	}
	
	public void setupGui() {
		frame = new JFrame("Play nim");
		frame.setBackground(Color.GRAY);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new CloseWindowListener());
		
		// top label
		gameProgress = new JLabel();
		
		// center
		JPanel matchesLeftPanel = new JPanel();
		matchesLeft = new JLabel();
		matchesLeftPanel.setLayout(new BoxLayout(matchesLeftPanel, BoxLayout.Y_AXIS));
		matchesLeftPanel.add(matchesLeft);
		
		// pick-button
		buttonPick = new JButton("Pick");
		buttonPick.addActionListener(new ButtonListener());
		
		// group buttons
		group = new ButtonGroup();
		JPanel radioPanel = new JPanel();
		
		for (byte b = 1; b <= 4; b++) {
			JRadioButton btn = new JRadioButton(Byte.toString(b));
			options.add(btn);
			group.add(btn);
			radioPanel.add(btn);
		}
		
		// display radio buttons and pick-button in same frame
		radioPanel.add(buttonPick);
		
		// construct game panel
		JPanel panel = new GamePanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(gameProgress);
		panel.add(matchesLeftPanel);
		panel.add(radioPanel);
		
		// display game
		frame.getContentPane().add(panel);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}