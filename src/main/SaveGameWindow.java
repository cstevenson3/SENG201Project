package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SaveGameWindow {

	private JFrame frame;
	private GUI guiObject;
	private GameState gameState;
	private JTextField txtSaveName;

	/**
	 * Create the SaveGameWindow.
	 * @param guiObject 
	 */
	public SaveGameWindow(GUI incomingGui) {
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
	 * begins the process of closing the window in the GUI.
	 */
	public void finishedWindow() {
		guiObject.closeSaveGameWindow(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		Component glue = Box.createGlue();
		frame.getContentPane().add(glue);
		
		JPanel panelSaveContent = new JPanel();
		frame.getContentPane().add(panelSaveContent);
		panelSaveContent.setLayout(new BoxLayout(panelSaveContent, BoxLayout.Y_AXIS));
		
		JLabel lblSaveGame = new JLabel("Save Game");
		lblSaveGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSaveGame.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelSaveContent.add(lblSaveGame);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panelSaveContent.add(rigidArea);
		
		JLabel lblNameYourSave = new JLabel("Name your save:");
		lblNameYourSave.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNameYourSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelSaveContent.add(lblNameYourSave);
		
		txtSaveName = new JTextField();
		txtSaveName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSaveName.setText("Save name");
		panelSaveContent.add(txtSaveName);
		txtSaveName.setColumns(10);
		txtSaveName.setMaximumSize(new Dimension(200,30));
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panelSaveContent.add(rigidArea_1);
		
		JLabel lblSaveWarn = new JLabel("");
		lblSaveWarn.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelSaveContent.add(lblSaveWarn);
		
		//PanelOverwrite constructor
		
		JPanel panelOverwrite = new JPanel();
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtSaveName.getText() == null || txtSaveName.getText().equals("")){
					lblSaveWarn.setText("Save name cannot be empty");
				}
				if(GameState.isValidSave(txtSaveName.getText())){
					panelSaveContent.setVisible(false);
					panelOverwrite.setVisible(true);
				}else {
					gameState.saveAs(txtSaveName.getText());
					finishedWindow();
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelSaveContent.add(btnSave);
		
		//PanelOverwrite

		frame.getContentPane().add(panelOverwrite);
		panelOverwrite.setVisible(false);
		panelOverwrite.setLayout(new BoxLayout(panelOverwrite, BoxLayout.Y_AXIS));
		
		JLabel lblSaveAlreadyExists = new JLabel("Save already exists, Overwrite?");
		lblSaveAlreadyExists.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSaveAlreadyExists.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelOverwrite.add(lblSaveAlreadyExists);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		panelOverwrite.add(rigidArea_3);
		
		JPanel panel = new JPanel();
		panelOverwrite.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton btnNo = new JButton("No");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finishedWindow();
			}
		});
		btnNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnNo);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		panel.add(rigidArea_2);
		
		JButton btnYes = new JButton("Yes");
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameState.saveAs(txtSaveName.getText());
				finishedWindow();
			}
		});
		btnYes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnYes);
		
		Component glue_1 = Box.createGlue();
		frame.getContentPane().add(glue_1);
	}

}
