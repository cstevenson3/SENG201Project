package main;



import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The main menu help window
 * @author Teren Gubb
 *
 */
public class HelpMainMenuWindow {

	private JFrame frame;
	private GUI guiObject;
	private GameState gameState;

	/**
	 * creates the Help Window.
	 * @param incomingGUI The gui object
	 */
	public HelpMainMenuWindow(GUI incomingGUI) {
		guiObject = incomingGUI;
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
	 * begins the process of closing the window in the GUI.
	 */
	public void finishedWindow() {
		guiObject.closeHelpMainMenuWindow(this);
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
		
		JLabel lblHelpTitle = new JLabel("Help");
		lblHelpTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblHelpTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(lblHelpTitle);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gameState != null && gameState.getViewContext() != null && gameState.getViewContext() == GameStateViewContext.PAUSE_MENU) {
					guiObject.setGUIContext(GUIContext.IN_GAME);
					finishedWindow();
				} else {
					guiObject.setGUIContext(GUIContext.MAIN_MENU);
					finishedWindow();
				}
			}
		});
		
		JLabel lblHelpContent = new JLabel();
		lblHelpContent.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHelpContent.setAlignmentX(Component.CENTER_ALIGNMENT);
		if (gameState != null && gameState.getViewContext() != null && gameState.getViewContext() == GameStateViewContext.PAUSE_MENU) {
			lblHelpContent.setText("This is the Help for the game! Press the button to return to the pause menu.");
		} else {
			lblHelpContent.setText("This is the Help for the main menu! Press the button to return to the main menu.");
		}
		frame.getContentPane().add(lblHelpContent);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_1);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(btnBack);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		frame.getContentPane().add(verticalGlue_1);
	}

}
