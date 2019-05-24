package main;



import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;

public class EndGameWindow {

	private JFrame frame;
	private GUI guiObject;
	private GameState gameState;
	
	/**
	 * creates the EndGameWindow.
	 * @param incomingGUI
	 */
	public EndGameWindow(GUI incomingGUI) {
		guiObject = incomingGUI;
		gameState = guiObject.getGameState();
		initialize();
		frame.setVisible(true);
	}
	
	/**
	 * closes the Window.
	 */
	public void closeWindow() {
		frame.dispose();
	}
	
	/**
	 * begins the process of closing the window.
	 */
	public void finishedWindow() {
		guiObject.closeEndGameWindow(this);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelEndGameContent = new JPanel();
		frame.getContentPane().add(panelEndGameContent, BorderLayout.CENTER);
		panelEndGameContent.setLayout(new BoxLayout(panelEndGameContent, BoxLayout.Y_AXIS));
		
		Component glue = Box.createGlue();
		panelEndGameContent.add(glue);
		
		JLabel lblYourGameIs = new JLabel("Your game is over!");
		lblYourGameIs.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblYourGameIs.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelEndGameContent.add(lblYourGameIs);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		panelEndGameContent.add(rigidArea_2);
		
		JLabel lblGameSummary = new JLabel("Game Summary:");
		lblGameSummary.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblGameSummary.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelEndGameContent.add(lblGameSummary);
		
		JLabel lblNewLabel = new JLabel("Ship name: " + gameState.getShipName());
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelEndGameContent.add(lblNewLabel);
		
		JLabel lblDaysTaken = new JLabel("Days taken:" + gameState.getDaysElapsed());
		lblDaysTaken.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblDaysTaken.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelEndGameContent.add(lblDaysTaken);
		
		JLabel lblPiecesFound = new JLabel("Pieces found: " + gameState.getPiecesCollected() + "/" + gameState.getPiecesRequired());
		lblPiecesFound.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPiecesFound.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelEndGameContent.add(lblPiecesFound);
		
		JLabel lblAllPiecesFoundnot = new JLabel(gameState.getPiecesCollected() >= gameState.getPiecesRequired() ? "All pieces collected" : "Not all pieces collected");
		lblAllPiecesFoundnot.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblAllPiecesFoundnot.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelEndGameContent.add(lblAllPiecesFoundnot);
		
		JLabel lblFinalScore = new JLabel("Final Score: "+ gameState.getScore());
		lblFinalScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblFinalScore.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelEndGameContent.add(lblFinalScore);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panelEndGameContent.add(rigidArea);
		
		JPanel panelEndGameSettings = new JPanel();
		panelEndGameContent.add(panelEndGameSettings);
		panelEndGameSettings.setLayout(new BoxLayout(panelEndGameSettings, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelEndGameSettings.add(horizontalGlue);
		
		JButton btnReturnToMain = new JButton("Return to main menu");
		panelEndGameSettings.add(btnReturnToMain);
		btnReturnToMain.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panelEndGameSettings.add(rigidArea_1);
		
		JButton btnExit = new JButton("Exit");
		panelEndGameSettings.add(btnExit);
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelEndGameSettings.add(horizontalGlue_1);
		
		Component glue_1 = Box.createGlue();
		panelEndGameContent.add(glue_1);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiObject.setGUIContext(GUIContext.SHOULD_EXIT);
			}
		});
		btnReturnToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiObject.setGUIContext(GUIContext.MAIN_MENU);
				finishedWindow();
			}
		});
	}

}
