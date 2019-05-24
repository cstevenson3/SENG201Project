package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PauseMenuWindow {

	private JFrame frame;
	private GUI guiObject;
	private GameState gameState;
	
	/**
	 * Create the PauseMenuWindow.
	 * @param incomingGui 
	 */
	public PauseMenuWindow(GUI incomingGui) {
		guiObject = incomingGui;
		gameState = guiObject.getGameState();
		initialize();
		frame.setVisible(true);
	}
	
	/**
	 * closes the window.
	 */
	public void closeWindow() {
		frame.dispose();
	}
	
	/**
	 * begins the close window process in the GUI.
	 */
	public void finishedWindow() {
		guiObject.closePauseMenuWindow(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component glue = Box.createGlue();
		panel.add(glue);
		
		JLabel lblPauseMenuOptions = new JLabel("Pause Menu");
		lblPauseMenuOptions.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPauseMenuOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblPauseMenuOptions);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		panel.add(rigidArea_3);
		
		JButton btnReturnToGame = new JButton("Return to game");
		btnReturnToGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameState.setViewContext(GameStateViewContext.CREW_MENU);
				finishedWindow();
			}
		});
		btnReturnToGame.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnReturnToGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnReturnToGame);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel.add(rigidArea_1);
		
		JButton btnSaveGame = new JButton("Save game");
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveGameWindow saveGame = new SaveGameWindow(guiObject);
				closeWindow();
			}
		});
		btnSaveGame.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSaveGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnSaveGame);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panel.add(rigidArea);
		
		JButton btnReturnToMain = new JButton("Return to main menu");
		btnReturnToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiObject.setGUIContext(GUIContext.MAIN_MENU);
				finishedWindow();
			}
		});
		btnReturnToMain.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnReturnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnReturnToMain);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		panel.add(rigidArea_2);
		
		JButton btnOpenHelp = new JButton("Open help");
		btnOpenHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiObject.setGUIContext(GUIContext.HELP);
				finishedWindow();
			}
		});
		btnOpenHelp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnOpenHelp.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnOpenHelp);
		
		Component glue_1 = Box.createGlue();
		panel.add(glue_1);
	}

}
