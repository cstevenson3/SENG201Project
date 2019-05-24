package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * The crew menu window
 * @author Teren Gubb
 *
 */
public class CrewMenuWindow {

	private JFrame frame;
	private GUI guiObject;
	private GameState gameState;
	private JTextField txtNameSleep;
	private JTextField txtNameRepair;
	
	/**
	 * Creates the CrewMenuWindow
	 * @param incomingGui The gui object
	 */
	public CrewMenuWindow(GUI incomingGui) {
		guiObject = incomingGui;
		gameState = guiObject.getGameState();
		initialize();
		frame.setVisible(true);
	}
	
	//Close window methods.
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
		guiObject.closeCrewMenuWindow(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		Component glue = Box.createGlue();
		frame.getContentPane().add(glue);
		
		JPanel panelTitle = new JPanel();
		frame.getContentPane().add(panelTitle);
		panelTitle.setLayout(new BoxLayout(panelTitle, BoxLayout.X_AXIS));
		
		// JPanel panelInCrewActions Constructor
		
		JPanel panelCrewShipInfo = new JPanel();
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panelTitle.add(rigidArea_1);
		
		JLabel lblYouAreOn = new JLabel("You are on day " + (gameState.getDaysElapsed() + 1) + ".");
		lblYouAreOn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelTitle.add(lblYouAreOn);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		panelTitle.add(rigidArea_3);
		
		JLabel lblPiecesCollected = new JLabel("You have collected " + gameState.getPiecesCollected() + "/" + gameState.getPiecesRequired() + " pieces.");
		lblPiecesCollected.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelTitle.add(lblPiecesCollected);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelTitle.add(horizontalGlue);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		frame.getContentPane().add(rigidArea_4);
		
		JPanel panelContent = new JPanel();
		frame.getContentPane().add(panelContent);
		panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.X_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panelContent.add(rigidArea);
		
		JPanel panelOptions = new JPanel();
		panelContent.add(panelOptions);
		panelOptions.setLayout(new BoxLayout(panelOptions, BoxLayout.Y_AXIS));
		
		JLabel lblCrewAndShip = new JLabel("Crew and Ship Menu Options:");
		lblCrewAndShip.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelOptions.add(lblCrewAndShip);
		
		Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
		panelContent.add(rigidArea_6);
		
		//PanelCrewSleep Initializer
		
		JPanel panelCrewSleep = new JPanel();
		panelContent.add(panelCrewSleep);
		panelCrewSleep.setVisible(false);
		panelCrewSleep.setLayout(new BoxLayout(panelCrewSleep, BoxLayout.Y_AXIS));
		
		//PanelCrewRepair Initializer
		
		JPanel panelRepairShip = new JPanel();
		panelContent.add(panelRepairShip);
		panelRepairShip.setVisible(false);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		panelOptions.add(rigidArea_2);
		
		JButton btnOpenPauseMenu = new JButton("Open pause menu");
		btnOpenPauseMenu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnOpenPauseMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameState.setViewContext(GameStateViewContext.PAUSE_MENU);
				finishedWindow();
			}
		});
		panelOptions.add(btnOpenPauseMenu);
		
		JButton btnOpenPlanetMenu = new JButton("Open planet menu");
		btnOpenPlanetMenu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnOpenPlanetMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameState.setViewContext(GameStateViewContext.PLANET_MENU);
				finishedWindow();
			}
		});
		panelOptions.add(btnOpenPlanetMenu);
		
		JButton btnOpenSpaceOutpost = new JButton("Open space outpost menu");
		btnOpenSpaceOutpost.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnOpenSpaceOutpost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameState.setViewContext(GameStateViewContext.SPACE_OUTPOST_MENU);
				finishedWindow();
			}
		});
		panelOptions.add(btnOpenSpaceOutpost);
		
		JButton btnMoveToNext = new JButton("Move to next day");
		btnMoveToNext.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnMoveToNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiObject.handleMovingToNextDay();
				finishedWindow();
			}
		});
		panelOptions.add(btnMoveToNext);
		
		JButton btnHaveACrew = new JButton("Have a crew member consume an item");
		btnHaveACrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnHaveACrew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameState.setViewContext(GameStateViewContext.CONSUME_MENU);
				finishedWindow();
			}
		});
		panelOptions.add(btnHaveACrew);
		
		JButton btnHaveACrew_1 = new JButton("Have a crew member sleep");
		btnHaveACrew_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnHaveACrew_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelRepairShip.setVisible(false);
				panelCrewSleep.setVisible(true);
			}
		});
		panelOptions.add(btnHaveACrew_1);
		
		JButton btnHaveACrew_2 = new JButton("Have a crew member repair the ship's shield");
		btnHaveACrew_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelOptions.add(btnHaveACrew_2);
		
		//JPanel panelCrewSleep
		
		JLabel lblSleepMenu = new JLabel("Sleep Menu:");
		lblSleepMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSleepMenu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelCrewSleep.add(lblSleepMenu);
		
		Component rigidArea_8 = Box.createRigidArea(new Dimension(20, 20));
		panelCrewSleep.add(rigidArea_8);
		
		JLabel lblWhichCrewMember = new JLabel("Which crew member should sleep?");
		lblWhichCrewMember.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWhichCrewMember.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCrewSleep.add(lblWhichCrewMember);
		
		Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
		panelCrewSleep.add(rigidArea_7);

		for (CrewMember crewMember:gameState.getCrew().getMembers().values()){
			JLabel lblCrewMember = new JLabel(crewMember.getName() + " at tiredness " + crewMember.getTiredness() + "/" + crewMember.getMaxTiredness());
			lblCrewMember.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblCrewMember.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelCrewSleep.add(lblCrewMember);
		}
		
		Component rigidArea_9 = Box.createRigidArea(new Dimension(20, 20));
		panelCrewSleep.add(rigidArea_9);
		
		JLabel lblSleepWarn = new JLabel();
		lblSleepWarn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSleepWarn.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCrewSleep.add(lblSleepWarn);
		
		txtNameSleep = new JTextField();
		txtNameSleep.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtNameSleep.setText("Name");
		txtNameSleep.setMaximumSize(new Dimension(200,20));
		panelCrewSleep.add(txtNameSleep);
		txtNameSleep.setColumns(10);
		
		Component rigidArea_10 = Box.createRigidArea(new Dimension(20, 20));
		panelCrewSleep.add(rigidArea_10);
		
		JButton btnSleep = new JButton("Sleep");
		btnSleep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSleepWarn.setText("");
				if(gameState.getCrew().getMembers().containsKey(txtNameSleep.getText())){
					try {
						gameState.getCrew().getMembers().get(txtNameSleep.getText()).sleep();
						lblSleepWarn.setText(txtNameSleep.getText() + " has slept");
						panelCrewSleep.setVisible(false);
					} catch (OutOfActionsException e5) {
						lblSleepWarn.setText("Crew member is out of actions and cannot sleep");
					}
				}else{
					lblSleepWarn.setText("Could not find that crew member, try again:");
				}
			}
		});
		btnSleep.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSleep.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCrewSleep.add(btnSleep);
		
		Component rigidArea_11 = Box.createRigidArea(new Dimension(20, 20));
		panelCrewSleep.add(rigidArea_11);
		
		// panelRepairShip
		
		panelRepairShip.setLayout(new BoxLayout(panelRepairShip, BoxLayout.Y_AXIS));
		
		JLabel lblShipRepairMenu = new JLabel("Ship Repair Menu");
		lblShipRepairMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblShipRepairMenu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelRepairShip.add(lblShipRepairMenu);
		
		Component rigidArea_12 = Box.createRigidArea(new Dimension(20, 20));
		panelRepairShip.add(rigidArea_12);
		
		JLabel lblWhichCrewMember_1 = new JLabel("Which crew member should repair the ship's shield?");
		lblWhichCrewMember_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWhichCrewMember_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRepairShip.add(lblWhichCrewMember_1);
		
		Component rigidArea_13 = Box.createRigidArea(new Dimension(20, 20));
		panelRepairShip.add(rigidArea_13);
		
		for (CrewMember crewMember:gameState.getCrew().getMembers().values()){
			JLabel lblCrewMemberRepair = new JLabel(crewMember.getName() + " with repair skill " + crewMember.getRepairSkill());
			lblCrewMemberRepair.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblCrewMemberRepair.setFont(new Font("Tahoma", Font.PLAIN, 16));
			panelRepairShip.add(lblCrewMemberRepair);
		}
		
		Component rigidArea_14 = Box.createRigidArea(new Dimension(20, 20));
		panelRepairShip.add(rigidArea_14);
		
		JLabel lblRepairWarn = new JLabel("");
		panelRepairShip.add(lblRepairWarn);
		
		txtNameRepair = new JTextField();
		txtNameRepair.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtNameRepair.setText("Name");
		txtNameRepair.setMaximumSize(new Dimension(200,20));
		panelRepairShip.add(txtNameRepair);
		txtNameRepair.setColumns(10);
		
		Component rigidArea_16 = Box.createRigidArea(new Dimension(20, 20));
		panelRepairShip.add(rigidArea_16);
		
		JButton btnRepair = new JButton("Repair");
		btnRepair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblRepairWarn.setText("");
				if(gameState.getCrew().getMembers().containsKey(txtNameRepair.getText())){
					try {
						gameState.getCrew().getMembers().get(txtNameRepair.getText()).repair(gameState.getShip());
						lblRepairWarn.setText("Ship repaired");
						panelRepairShip.setVisible(false);
					} catch (OutOfActionsException e4) {
						lblRepairWarn.setText("This crew member is out of actions, ship not repaired");
					}	
				}else{
					lblRepairWarn.setText("Could not find that crew member, try again:");
				}
			}
		});
		btnRepair.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRepair.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelRepairShip.add(btnRepair);
		
		Component rigidArea_15 = Box.createRigidArea(new Dimension(20, 20));
		panelRepairShip.add(rigidArea_15);
		
		// panelInCrewActions variables
		
		panelContent.add(panelCrewShipInfo);
		panelCrewShipInfo.setLayout(new BoxLayout(panelCrewShipInfo, BoxLayout.Y_AXIS));
		
		JLabel lblCrewShipStatus = new JLabel("Current Crew and Ship Status:");
		lblCrewShipStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCrewShipStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCrewShipInfo.add(lblCrewShipStatus);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		panelCrewShipInfo.add(rigidArea_5);
		
		JTextPane txtCrewShipStatus = new JTextPane();
		txtCrewShipStatus.setBackground(UIManager.getColor("Button.background"));
		txtCrewShipStatus.setText(gameState.getCrewStatus() + "\n" + gameState.getShipName() + " shield integrity: " + "\n" + gameState.getShipShieldHealth());
		txtCrewShipStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtCrewShipStatus.setMaximumSize(new Dimension(700,200));
		panelCrewShipInfo.add(txtCrewShipStatus);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelContent.add(horizontalGlue_1);
		btnHaveACrew_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCrewSleep.setVisible(false);
				panelRepairShip.setVisible(true);
			}
		});
		
		Component glue_1 = Box.createGlue();
		frame.getContentPane().add(glue_1);
	}

}
