package main;



import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Properties;


/**
 * The main menu window
 * @author Teren Gubb
 *
 */
public class MainMenuWindow {

	private JFrame frame;
	private GUI guiObject;
	private Properties gameProperties;
	
	/**
	 * Create the MainMenu Window.
	 * @param incomingGUI The gui object
	 */
	public MainMenuWindow(GUI incomingGUI) {
		guiObject = incomingGUI;
		gameProperties = guiObject.getProperties();
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
	 * starts the process of closing the window in the GUI.
	 */
	public void finishedWindow() {
		guiObject.closeMainMenuWindow(this);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		Component verticalGlue = Box.createVerticalGlue();
		frame.getContentPane().add(verticalGlue);
		
		JLabel lblGameName = new JLabel(gameProperties.getProperty("gameName"));
		lblGameName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblGameName.setFont(new Font("Tahoma", Font.BOLD, 30));
		frame.getContentPane().add(lblGameName);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_1);
		
		JLabel lblMainMenu = new JLabel("Main Menu");
		lblMainMenu.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblMainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(lblMainMenu);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_2);
		
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiObject.setGUIContext(GUIContext.START_GAME);
				finishedWindow();
			}
		});
		btnStartGame.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnStartGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(btnStartGame);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_3);
		
		JButton btnLoadGame = new JButton("Load Game");
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiObject.setGUIContext(GUIContext.LOAD_GAME);
				finishedWindow();
			}
		});
		btnLoadGame.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLoadGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(btnLoadGame);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_4);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guiObject.setGUIContext(GUIContext.HELP);
				finishedWindow();
			}
		});
		btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnHelp.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(btnHelp);
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_5);
		
		JButton btnExitGame = new JButton("Exit Game");
		btnExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guiObject.setGUIContext(GUIContext.SHOULD_EXIT);
				finishedWindow();
			}
		});
		btnExitGame.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnExitGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(btnExitGame);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		frame.getContentPane().add(verticalGlue_1);
	}

}
