package nimguigame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import nim.*;

public class NimGuiGame {
	JButton buttonPick;
	
	// radio buttons
	JRadioButton option1 = new JRadioButton("1");
	JRadioButton option2 = new JRadioButton("2");
	JRadioButton option3 = new JRadioButton("3");
	JRadioButton option4 = new JRadioButton("4");
	
	public static void main(String[] args) {
		Nim test = new Nim();
		NimGuiGame game = new NimGuiGame();
		game.go();
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("check");
		}
	}
	
	public void go() {
		JFrame frame = new JFrame("Play nim");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// top label
		JLabel label = new JLabel("Who's turn is it?");
		
		// center
		JPanel gameStatePanel = new JPanel();
		JLabel gameState = new JLabel("11 matches left");
		gameStatePanel.add(gameState);
		
		// pick-button
		buttonPick = new JButton("Pick");
		buttonPick.addActionListener(new ButtonListener());
		
		// group buttons
		ButtonGroup group = new ButtonGroup();
		group.add(option1);
		group.add(option2);
		group.add(option3);
		group.add(option4);
		
		// display radio buttons and pick-button in same frame
		JPanel radioPanel = new JPanel();
		radioPanel.add(option1);
		radioPanel.add(option2);
		radioPanel.add(option3);
		radioPanel.add(option4);
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
}

class GamePanel extends JPanel {
	public void paintComponent(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
	}
}