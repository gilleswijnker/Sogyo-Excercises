package nimguigame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import nim.*;
import java.util.Enumeration;
import java.util.ArrayList;

public class NimGuiGame {
	private JFrame frame;
	private JButton buttonPick;
	private JLabel label;
	private JLabel computersTurn;
	private JLabel gameState;
	private Nim game;
	private ArrayList<GuiGameListener> listeners = new ArrayList<GuiGameListener>();
	
	// radio buttons
	ButtonGroup group;
	ArrayList<JRadioButton> options = new ArrayList<JRadioButton>();
	
	public static void main(String[] args) {
		NimGuiGame gamegui = new NimGuiGame();
		gamegui.play(false);
	}

	// click on the 'pick' button
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (!executeTurn()) return;
			if (!game.doWeHaveALoser()) game.nextTurn();
			
			frame.repaint();
		}
	}
	
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
	
	private byte getNumberMatches() {
		// Which button is selected
		for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            JRadioButton button = (JRadioButton) buttons.nextElement();

            if (button.isSelected())
                return Byte.parseByte(button.getText());
        }
		return 0;
	}
		
	public void play(boolean singlePlayerGame) {
		game = new Nim();
		game.addListener(new ComputersTurnListener());
		game.setupGame(singlePlayerGame);
		setupGui();
		game.nextTurn();
	}
	
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
			gameState.setText(game.getMatchesLeft() + " matches left");
			if (game.doWeHaveALoser())
				label.setText("Player " + game.getLoser() + " looses!");
			else
				label.setText("Turn: " + game.playerOnTurn.getName());
		}
	}
	
	// listen to the game signaling it's the computers turn
	private class ComputersTurnListener implements GameListener {
		public void computersTurn() {
			try {
				byte n = game.playerOnTurn.pickMatches();
				computersTurn.setText("Computer took " + n + " matches");
			}
			catch (InvalidNumberOfMatchesException ex) {
				// cannot happen in GUI
			}
			game.nextTurn();
		};
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
	
	public void setupGui() {
		frame = new JFrame("Play nim");
		frame.setBackground(Color.GRAY);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new CloseWindowListener());
		
		// top label
		label = new JLabel();
		
		// center
		JPanel gameStatePanel = new JPanel();
		computersTurn = new JLabel();
		gameState = new JLabel();
		gameStatePanel.setLayout(new BoxLayout(gameStatePanel, BoxLayout.Y_AXIS));
		gameStatePanel.add(computersTurn);
		gameStatePanel.add(gameState);
		
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
		panel.add(label);
		panel.add(gameStatePanel);
		panel.add(radioPanel);
		
		// display game
		frame.getContentPane().add(panel);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
	
	public void addListener(GuiGameListener listener) {
		listeners.add(listener);
	}
}