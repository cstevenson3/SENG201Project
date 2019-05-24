package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Font;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JTextField;


/**
 * The planet menu window
 * @author Teren Gubb
 *
 */
public class PlanetMenuWindow {

	private JFrame frame;
	private GUI guiObject;
	private GameState gameState;
	private JTextField txtName;
	private JTextField txtFirstPilot;
	private JTextField txtSecondPilot;
	private JTextField txtPlanet;
	ArrayList<String> validPilots;


	/**
	 * Creates the PlanetMenuWindow
	 * @param incomingGui The gui object
	 */
	public PlanetMenuWindow(GUI incomingGui) {
		guiObject = incomingGui;
		gameState = guiObject.getGameState();
		validPilots = createValidPilots();
		initialize();
		frame.setVisible(true);
	}
	
	/**
	 * creates an ArrayList for valid pilots.
	 * @return An ArrayList of the names of valid pilots
	 */
	public ArrayList<String> createValidPilots() {
		ArrayList<String> localValidPilots = new ArrayList<String>();
		for(CrewMember member:gameState.getCrew().getMembers().values()){
			if(member.canPilot()){
				localValidPilots.add(member.getName());
			}
		}
		return localValidPilots;
	}
	
	/**
	 * closes the PlanetMenuWindow
	 */
	public void closeWindow() {
		frame.dispose();
	}
	
	/**
	 * begins the clsoe window process in the GUI.
	 */
	public void finishedWindow() {
		guiObject.closePlanetMenuWindow(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panelContent = new JPanel();
		frame.getContentPane().add(panelContent);
		panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
		
		Component glue = Box.createGlue();
		panelContent.add(glue);
		
		JLabel lblPlanetMenu = new JLabel("Planet Menu:");
		lblPlanetMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPlanetMenu.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelContent.add(lblPlanetMenu);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		panelContent.add(rigidArea_3);
		
		JPanel panelPanelContainer = new JPanel();
		panelContent.add(panelPanelContainer);
		panelPanelContainer.setLayout(new BoxLayout(panelPanelContainer, BoxLayout.X_AXIS));
		
		JPanel panelButtons = new JPanel();
		panelPanelContainer.add(panelButtons);
		panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
		
		JLabel lblYouAreCurrently = new JLabel("You are currently on " + gameState.getPlanet() + " :");
		panelButtons.add(lblYouAreCurrently);
		lblYouAreCurrently.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		panelButtons.add(rigidArea_2);
		
		JButton btnReturnToCrew = new JButton("Return to crew menu");
		panelButtons.add(btnReturnToCrew);
		btnReturnToCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panelButtons.add(rigidArea_1);
		
		//panelPlanetSearch Constructor
		
		JPanel panelPlanetSearch = new JPanel();
		
		//panelPilotTo Constructor
		
		JPanel panelPilotTo = new JPanel();
		
		JButton btnHaveACrew = new JButton("Have a crew member search the planet");
		panelButtons.add(btnHaveACrew);
		btnHaveACrew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPilotTo.setVisible(false);
				panelPlanetSearch.setVisible(true);
			}
		});
		btnHaveACrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panelButtons.add(rigidArea);
		
		JButton btnPilotToA = new JButton("Pilot to a different planet (2 crew, 1 action)");
		btnPilotToA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPlanetSearch.setVisible(false);
				panelPilotTo.setVisible(true);
			}
		});
		panelButtons.add(btnPilotToA);
		btnPilotToA.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		panelPanelContainer.add(rigidArea_4);
		
		// panelPlanetSearch Values
		
		panelPlanetSearch.setVisible(false);
		panelPanelContainer.add(panelPlanetSearch);
		panelPlanetSearch.setLayout(new BoxLayout(panelPlanetSearch, BoxLayout.Y_AXIS));
		
		JLabel lblSearchPlanet = new JLabel("Search Planet:");
		lblSearchPlanet.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSearchPlanet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelPlanetSearch.add(lblSearchPlanet);
		
		Component rigidArea_9 = Box.createRigidArea(new Dimension(20, 20));
		panelPlanetSearch.add(rigidArea_9);
		
		JLabel lblWhichCrewMember = new JLabel("Which crew member should search the planet?");
		lblWhichCrewMember.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelPlanetSearch.add(lblWhichCrewMember);
		lblWhichCrewMember.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		panelPlanetSearch.add(rigidArea_5);
		
		for(CrewMember crewMember:gameState.getCrew().getMembers().values()){
			JLabel label = new JLabel(crewMember.getName() + " with search skill " + crewMember.getSearchSkill());
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			label.setFont(new Font("Tahoma", Font.PLAIN, 16));
			panelPlanetSearch.add(label);
		} 
		
		Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
		panelPlanetSearch.add(rigidArea_6);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtName.setText("Name");
		panelPlanetSearch.add(txtName);
		txtName.setColumns(10);
		txtName.setMaximumSize(new Dimension(150,20));
		
		Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
		panelPlanetSearch.add(rigidArea_7);
		
		//lblPlanetWarn constructor
		
		JLabel lblPlanetWarn = new JLabel("");
		
		JButton btnSearchPlanet = new JButton("Search planet");
		btnSearchPlanet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(gameState.getCrew().getMembers().containsKey(txtName.getText())){
					InventoryItem itemFound = null;
					try {
						itemFound = gameState.searchCurrentPlanet(txtName.getText());
					} catch (AllPartsFoundException e3) {
						System.out.println("All parts found");
						guiObject.setGUIContext(GUIContext.END_GAME);
					} catch (OutOfActionsException e1) {
						btnSearchPlanet.setText("This crew member is out of actions, could not search");
					}
					if(itemFound == null){
						btnSearchPlanet.setText("Searching this planet found nothing");
					}else{
						btnSearchPlanet.setText("Searching this planet found: " + itemFound.getDescription());
					}
				}else {
					btnSearchPlanet.setText("Could not find that crew member, try again:");
				}
			}
		});
		
		//lblPlanetWarn values
		
		panelPlanetSearch.add(lblPlanetWarn);
		btnSearchPlanet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSearchPlanet.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelPlanetSearch.add(btnSearchPlanet);
		
		Component rigidArea_8 = Box.createRigidArea(new Dimension(20, 20));
		panelPlanetSearch.add(rigidArea_8);
		
		//panelpilotto variables
		
		panelPanelContainer.add(panelPilotTo);
		panelPilotTo.setVisible(false);
		panelPilotTo.setLayout(new BoxLayout(panelPilotTo, BoxLayout.Y_AXIS));
		
		JLabel lblPilotTo = new JLabel("Pilot to another planet:");
		lblPilotTo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPilotTo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelPilotTo.add(lblPilotTo);
		
		Component rigidArea_10 = Box.createRigidArea(new Dimension(20, 20));
		panelPilotTo.add(rigidArea_10);
		
		JLabel lblChooseYourFirst = new JLabel("Choose your first pilot:");
		lblChooseYourFirst.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChooseYourFirst.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelPilotTo.add(lblChooseYourFirst);
		
		for(String allowedPilot : validPilots){
			JLabel lblPilot = new JLabel(allowedPilot);
			lblPilot.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblPilot.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelPilotTo.add(lblPilot);
		}
		
		Component rigidArea_16 = Box.createRigidArea(new Dimension(20, 20));
		panelPilotTo.add(rigidArea_16);
		
		txtFirstPilot = new JTextField();
		txtFirstPilot.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelPilotTo.add(txtFirstPilot);
		txtFirstPilot.setColumns(10);
		txtFirstPilot.setMaximumSize(new Dimension(200,20));
		
		Component rigidArea_11 = Box.createRigidArea(new Dimension(20, 20));
		panelPilotTo.add(rigidArea_11);
		
		JLabel lblChooseYourSecond = new JLabel("Choose your Second pilot:");
		lblChooseYourSecond.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChooseYourSecond.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelPilotTo.add(lblChooseYourSecond);
		
		for(String allowedPilot : validPilots){
			JLabel lblPilot_1 = new JLabel(allowedPilot);
			lblPilot_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblPilot_1.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelPilotTo.add(lblPilot_1);
		}
		
		Component rigidArea_17 = Box.createRigidArea(new Dimension(20, 20));
		panelPilotTo.add(rigidArea_17);
		
		txtSecondPilot = new JTextField();
		txtSecondPilot.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelPilotTo.add(txtSecondPilot);
		txtSecondPilot.setColumns(10);
		txtSecondPilot.setMaximumSize(new Dimension(200,20));
		
		Component rigidArea_12 = Box.createRigidArea(new Dimension(20, 20));
		panelPilotTo.add(rigidArea_12);
		
		JLabel lblPlanets = new JLabel("Planets:");
		lblPlanets.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPlanets.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelPilotTo.add(lblPlanets);
		
		for(Planet planet:gameState.getPlanets().values()){
			JLabel lblPlanet = new JLabel(planet.getName());
			lblPlanet.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblPlanet.setFont(new Font("Tahoma", Font.PLAIN, 16));
			panelPilotTo.add(lblPlanet);
		}
		
		Component rigidArea_13 = Box.createRigidArea(new Dimension(20, 20));
		panelPilotTo.add(rigidArea_13);
		
		JLabel lblWhichPlanetDo = new JLabel("Which planet do you want to go to?");
		lblWhichPlanetDo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblWhichPlanetDo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelPilotTo.add(lblWhichPlanetDo);
		
		txtPlanet = new JTextField();
		txtPlanet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelPilotTo.add(txtPlanet);
		txtPlanet.setColumns(10);
		txtPlanet.setMaximumSize(new Dimension(200,20));
		
		Component rigidArea_14 = Box.createRigidArea(new Dimension(20, 20));
		panelPilotTo.add(rigidArea_14);
		
		JLabel lblPilotWarn = new JLabel("");
		lblPilotWarn.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPilotWarn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelPilotTo.add(lblPilotWarn);
		
		// lblPilotResult constructor
		
		JLabel lblPilotResult = new JLabel("");
		
		JButton btnFly = new JButton("Fly!");
		btnFly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblChooseYourFirst.setText("Choose your first pilot:");
				lblChooseYourSecond.setText("Choose your Second pilot:");
				lblWhichPlanetDo.setText("Which planet do you want to go to?");
				lblPilotWarn.setText("");
				lblPilotResult.setText("");
							
				String pilot1 = txtFirstPilot.getText();
				boolean pilot1bool = true;
				if(!gameState.getCrew().getMembers().containsKey(pilot1)){
					lblChooseYourFirst.setText("Could not find that crew member, try again:");
					pilot1bool = false;
				}
				if(gameState.getCrew().getMembers().get(pilot1).getActionsRemaining() <= 0){
					lblChooseYourFirst.setText("That crew member is out of actions, try again:");
					pilot1bool = false;
				}
				if(!validPilots.contains(pilot1)){
					lblChooseYourFirst.setText("Not a valid pilot, try again:");
					pilot1bool = false;
				}
				
				String pilot2 = txtSecondPilot.getText();
				boolean pilot2bool = true;
				if(pilot2.equals(pilot1)){
					lblChooseYourSecond.setText("That pilot is already selected, try another:");
					pilot2bool = false;
				}
				if(!gameState.getCrew().getMembers().containsKey(pilot2)){
					lblChooseYourSecond.setText("Could not find that crew member, try again:");
					pilot2bool = false;
				}
				if(gameState.getCrew().getMembers().get(pilot2).getActionsRemaining() <= 0){
					lblChooseYourSecond.setText("That crew member is out of actions, try again:");
					pilot2bool = false;
				}
				if(!validPilots.contains(pilot2)){
					lblChooseYourSecond.setText("Not a valid pilot, try again:");
					pilot2bool = false;
				}
				
				String planetName = txtPlanet.getText();
				if((gameState.getPlanets().containsKey(planetName)) && pilot1bool && pilot2bool){
					try {
						boolean asteroidBeltEvent = false;
						asteroidBeltEvent = gameState.pilotShipTo(planetName, pilot1, pilot2);
						if(asteroidBeltEvent){
							lblPilotWarn.setText("An asteroid belt was encountered, shield health reduced");
						}
						lblPilotResult.setText("Successfully travelled to " + planetName);
						}catch (OutOfActionsException e1) {
							lblPilotWarn.setText("Either pilot is out of actions, could not travel to " + planetName);
						}catch(InvalidCrewRoleException e2){
							lblPilotWarn.setText("At least one crew member selected cannot pilot, could not travel to " + planetName);
						}catch (ShieldHealthDepletedException e1) {
							lblPilotWarn.setText("The ship's shield was destroyed, game ended.");
							guiObject.setGUIContext(GUIContext.END_GAME);
							finishedWindow();
						}
				}else{
					if((gameState.getPlanets().containsKey(planetName)) == false) {
						lblWhichPlanetDo.setText("Could not find that planet, try again:");
					}
				}
			
			}
		});
		
		// lblPilotResult
		
		lblPilotResult.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPilotResult.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelPilotTo.add(lblPilotResult);
		btnFly.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnFly.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelPilotTo.add(btnFly);
		
		Component rigidArea_15 = Box.createRigidArea(new Dimension(20, 20));
		panelPilotTo.add(rigidArea_15);
		
		Component glue_1 = Box.createGlue();
		panelContent.add(glue_1);
		
		btnReturnToCrew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameState.setViewContext(GameStateViewContext.CREW_MENU);
				finishedWindow();
			}
		});
	}

}
