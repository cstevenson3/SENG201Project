package main;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import main.CLI.CLIOutOfActionsListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class CrewMemberCreatorWindow {

	private JFrame frame;
	private GUI guiObject;
	private JTextField txtCrewMemberName;
	private ArrayList<String> roles = CrewMember.getAllRoles();
	private final Component glue_1 = Box.createGlue();
	private JTextField txtCrewMemberRole;

	/**
	 * Creates the CrewMemberCreator Window.
	 * @param incomingGUI
	 */
	public CrewMemberCreatorWindow(GUI incomingGUI) {
		guiObject = incomingGUI;
		initialize();
		frame.setVisible(true);
	}
	
	
	//Closer methods
	
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
		guiObject.closeCrewMemberCreatorWindow(this);
	}
	
	//Other methods
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		Component glue = Box.createGlue();
		frame.getContentPane().add(glue);
		
		JPanel panelCrewCreator = new JPanel();
		frame.getContentPane().add(panelCrewCreator);
		panelCrewCreator.setLayout(new BoxLayout(panelCrewCreator, BoxLayout.Y_AXIS));
		
		Component verticalGlue = Box.createVerticalGlue();
		panelCrewCreator.add(verticalGlue);
		
		JLabel lblCrewCreator = new JLabel("Crew Creator");
		panelCrewCreator.add(lblCrewCreator);
		lblCrewCreator.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblCrewCreator.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panelCrewCreator.add(verticalStrut);
		
		JLabel lblGiveThisCrew = new JLabel("Give this crew member a name:");
		panelCrewCreator.add(lblGiveThisCrew);
		lblGiveThisCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGiveThisCrew.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		txtCrewMemberName = new JTextField();
		panelCrewCreator.add(txtCrewMemberName);
		txtCrewMemberName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtCrewMemberName.setText("Name");
		txtCrewMemberName.setColumns(10);
		txtCrewMemberName.setMaximumSize(new Dimension(200,30));
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panelCrewCreator.add(verticalStrut_1);
		
		JLabel lblAssignThisMember = new JLabel("Assign this member a role:");
		panelCrewCreator.add(lblAssignThisMember);
		lblAssignThisMember.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblAssignThisMember.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		txtCrewMemberRole = new JTextField();
		txtCrewMemberRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtCrewMemberRole.setText("Role");
		panelCrewCreator.add(txtCrewMemberRole);
		txtCrewMemberRole.setColumns(10);
		txtCrewMemberRole.setMaximumSize(new Dimension(200,30));
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panelCrewCreator.add(verticalStrut_2);
		
		JPanel panelButtons = new JPanel();
		panelCrewCreator.add(panelButtons);
		panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
		
		JButton btnBackToStart = new JButton("Return to Start Conditions");
		btnBackToStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guiObject.setGUIContext(GUIContext.START_GAME);
				guiObject.setLastGUIContext(GUIContext.MAIN_MENU);
				guiObject.resetCrewMembers(); 
				finishedWindow();
			}
		});
		panelButtons.add(btnBackToStart);
		btnBackToStart.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panelButtons.add(rigidArea);
		
		JButton btnAcceptCrewMember = new JButton("Accept crew member");
		btnAcceptCrewMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String crewMemberName = txtCrewMemberName.getText();
				String crewMemberRole = txtCrewMemberRole.getText();
				lblGiveThisCrew.setText("Give this crew member a name:");
				lblAssignThisMember.setText("Assign this member a role:");
				if ((!guiObject.containsCrewMember(crewMemberName)) && CrewMember.isValidRole(crewMemberRole)) {
					CrewMember crewMember = new CrewMember(crewMemberRole);
					crewMember.setName(crewMemberName);
					guiObject.addCrewMember(crewMemberName, crewMember);
					guiObject.addCrewFinished();
					if (guiObject.getCrewFinished() == guiObject.getNumberOfCrew()) {
						guiObject.setGUIContext(GUIContext.IN_GAME);
						guiObject.setLastGUIContext(GUIContext.MAIN_MENU);
						guiObject.makeGameState();
						finishedWindow();
					}else {
						finishedWindow();
					}
				}else {
					if (guiObject.containsCrewMember(crewMemberName)) {
						lblGiveThisCrew.setText("There is already a crew member with this name.");
						
					}
					if(!CrewMember.isValidRole(crewMemberRole)) {
						lblAssignThisMember.setText("This role is not valid.");
					}
				}
			}
		});
		panelButtons.add(btnAcceptCrewMember);
		btnAcceptCrewMember.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		panelCrewCreator.add(verticalGlue_1);
		
		JPanel panelRoleDescriptions = new JPanel();
		frame.getContentPane().add(panelRoleDescriptions);
		panelRoleDescriptions.setLayout(new BoxLayout(panelRoleDescriptions, BoxLayout.Y_AXIS));
		
		JLabel lblRoleDescriptions = new JLabel("Role Descriptions");
		lblRoleDescriptions.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblRoleDescriptions.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelRoleDescriptions.add(lblRoleDescriptions);
		
		JTextPane txtpnRoleDescriptions = new JTextPane();
		txtpnRoleDescriptions.setBackground(UIManager.getColor("Button.background"));
		txtpnRoleDescriptions.setFont(new Font("Tahoma", Font.PLAIN, 16));
		for (String role:roles) {
			CrewMember rolePlaceholder = new CrewMember(role);
			txtpnRoleDescriptions.setText(txtpnRoleDescriptions.getText() + "\n" + "\n" + rolePlaceholder.getRoleDescription());
		}
		txtpnRoleDescriptions.setMaximumSize(new Dimension(400,1000));
		panelRoleDescriptions.add(txtpnRoleDescriptions);
		frame.getContentPane().add(glue_1);
	}

}
