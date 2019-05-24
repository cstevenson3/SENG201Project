package main;



import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;


/**
 * The save-game loader window
 * @author Teren Gubb
 *
 */
public class LoadGameWindow {

	private JFrame frame;
	private GUI guiObject;

	/**
	 * creates the LoadGameWindow.
	 * @param incomingGUI The gui object
	 */
	public LoadGameWindow(GUI incomingGUI) {
		guiObject = incomingGUI;
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
		guiObject.closeLoadGameWindow(this);
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
		
		JLabel lblLoadMenu = new JLabel("Load Menu");
		lblLoadMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLoadMenu.setFont(new Font("Tahoma", Font.BOLD, 24));
		frame.getContentPane().add(lblLoadMenu);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut);
		
		JLabel lblSelectASave = new JLabel("Select a save to load:");
		lblSelectASave.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSelectASave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.getContentPane().add(lblSelectASave);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_1);
		
		JPanel panelSaves = new JPanel();
		frame.getContentPane().add(panelSaves);
		panelSaves.setLayout(new BoxLayout(panelSaves, BoxLayout.Y_AXIS));
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panelSaves.add(verticalStrut_2);
		
		
		ArrayList<String> saves = GameState.getSavedGameNames();
		if (saves.size() > 0) {
			for (String save : saves ) {
				JButton button = new JButton(save);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						guiObject.setGameStateContext(GameState.loadSave(save));
						guiObject.setGUIContext(GUIContext.IN_GAME);
						finishedWindow();
					}
				});
				button.setFont(new Font("Tahoma", Font.PLAIN, 16));
				button.setAlignmentX(Component.CENTER_ALIGNMENT);
				panelSaves.add(button);
			}
		}
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panelSaves.add(verticalStrut_3);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_4);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiObject.setGUIContext(GUIContext.MAIN_MENU);
				finishedWindow();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(btnBack);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		frame.getContentPane().add(verticalGlue_1);
	}

}
