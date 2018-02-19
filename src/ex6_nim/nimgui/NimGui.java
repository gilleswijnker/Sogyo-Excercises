package nimgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NimGui implements ActionListener {	
	private JComboBox comboBoxNrPlayers;
	private JButton buttonStartGame;
	private JFrame frame;

	public static void main (String args[]) {
		NimGui gui = new NimGui();
	}
	
	// listen to the actual game window being closed
	private class GameClosedListener implements GuiGameListener {
		public void guiGameClosed() {
			frame.setEnabled(true);
		}
	}
	
	public NimGui() {
		frame = new JFrame("nim");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// start game button
		buttonStartGame = new JButton("start game");
		buttonStartGame.addActionListener(this);
		
		// number of players combo box
		String[] nrPlayers = {"1", "2"};
		comboBoxNrPlayers = new JComboBox(nrPlayers);
		
		// label telling what to do
		JLabel labelInfo = new JLabel("Select amount of players and start!");
		
		// assemble the window
		frame.getContentPane().add(buttonStartGame, BorderLayout.SOUTH);
		frame.getContentPane().add(comboBoxNrPlayers, BorderLayout.CENTER);
		frame.getContentPane().add(labelInfo, BorderLayout.NORTH);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
	
	// listen to the user clicking the 'start game' button
	public void actionPerformed(ActionEvent event) {
		boolean singlePlayerGame = comboBoxNrPlayers.getSelectedItem() == "1";
		NimGuiGame game = new NimGuiGame();
		game.addListener(new GameClosedListener());
		frame.setEnabled(false);
		game.play(singlePlayerGame);
	}
}