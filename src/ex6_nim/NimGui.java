import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NimGui {
	
	public static void main (String args[]) {
		NimGuiStartGame gui = new NimGuiStartGame();
	}
}

class NimGuiStartGame implements ActionListener {
	private JComboBox comboBoxNrPlayers;
	private JButton buttonStartGame;
	private JFrame frame;
	
	public NimGuiStartGame() {
		frame = new JFrame("nim");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buttonStartGame = new JButton("start game");
		buttonStartGame.addActionListener(this);
		
		String[] nrPlayers = {"1", "2"};
		comboBoxNrPlayers = new JComboBox(nrPlayers);
		
		JLabel labelInfo = new JLabel("Select amount of players and start!");
		frame.getContentPane().add(buttonStartGame, BorderLayout.SOUTH);
		frame.getContentPane().add(comboBoxNrPlayers, BorderLayout.CENTER);
		frame.getContentPane().add(labelInfo, BorderLayout.NORTH);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) {
		System.out.println(comboBoxNrPlayers.getSelectedItem());
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}