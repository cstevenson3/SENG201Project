package main;



import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Properties;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextField;

/**
 * The start-game menu window
 * @author Teren Gubb
 *
 */
public class StartGameWindow {

	private JFrame frame;
	private GUI guiObject;
	private Properties properties;
	private int minDays;
	private int maxDays;
	private int days;
	private int minCrew;
	private int maxCrew;
	private int crewAmount;
	private JTextField txtShipName;
	

	/**
	 * Create the Start Window.
	 * @param incomingGUI The gui object
	 */
	public StartGameWindow(GUI incomingGUI) {
		guiObject = incomingGUI;
		properties = guiObject.getProperties();
		
		minDays = Integer.parseInt(properties.getProperty("minDays"));
		maxDays = Integer.parseInt(properties.getProperty("maxDays"));
		days = minDays;
		
		minCrew = Integer.parseInt(properties.getProperty("minCrew"));
		maxCrew = Integer.parseInt(properties.getProperty("maxCrew"));
		crewAmount = minCrew;
		initialize();
		frame.setVisible(true);
	}
	// Close Methods
	
	/**
	 * closes the window.
	 */
	public void closeWindow() {
		frame.dispose();
	}
	
	/**
	 * starts the process of closing the window via the GUI.
	 */
	public void finishedWindow() {
		guiObject.closeStartGameWindow(this);
	}
	
	//Other Methods
	
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
		
		JLabel lblGameStartConditions = new JLabel("Game Start Conditions");
		lblGameStartConditions.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblGameStartConditions.setFont(new Font("Tahoma", Font.BOLD, 24));
		frame.getContentPane().add(lblGameStartConditions);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut);
		
		JLabel lblHowManyDays = new JLabel("How many days should the game last?");
		lblHowManyDays.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHowManyDays.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(lblHowManyDays);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_2);
		
		JLabel lblDays = new JLabel("Days : " + days);
		lblDays.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblDays.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.getContentPane().add(lblDays);
		
		JPanel panelDays = new JPanel();
		frame.getContentPane().add(panelDays);
		panelDays.setLayout(new BoxLayout(panelDays, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelDays.add(horizontalGlue);
		
		JLabel lblMinimumDays = new JLabel("Minimum Days: " + minDays);
		lblMinimumDays.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelDays.add(lblMinimumDays);
		
		JSlider sliderDays = new JSlider();
		sliderDays.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				days = sliderDays.getValue();
				lblDays.setText("Days : " + days);
			}
		});
		sliderDays.setValue(minDays);
		sliderDays.setSnapToTicks(true);
		sliderDays.setPaintTicks(true);
		sliderDays.setMinimum(minDays);
		sliderDays.setMaximum(maxDays);
		sliderDays.setMaximumSize(new Dimension(250,50));
		panelDays.add(sliderDays);
		
		JLabel lblMaximumDays = new JLabel("Maximum Days: " + maxDays);
		lblMaximumDays.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelDays.add(lblMaximumDays);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelDays.add(horizontalGlue_1);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_1);
		
		JLabel lblCrewAmount = new JLabel("How big do you want your crew to be?");
		lblCrewAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblCrewAmount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.getContentPane().add(lblCrewAmount);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_3);
		
		JLabel lblNumberOfCrew = new JLabel("Number of crew: " + crewAmount);
		lblNumberOfCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNumberOfCrew.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(lblNumberOfCrew);
		
		JPanel panelCrewAmount = new JPanel();
		frame.getContentPane().add(panelCrewAmount);
		panelCrewAmount.setLayout(new BoxLayout(panelCrewAmount, BoxLayout.X_AXIS));
		
		Component horizontalGlue_5 = Box.createHorizontalGlue();
		panelCrewAmount.add(horizontalGlue_5);
		
		JLabel lblMinCrew = new JLabel("Minimum Crew: " + minCrew);
		lblMinCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelCrewAmount.add(lblMinCrew);
		
		JSlider sliderCrew = new JSlider();
		sliderCrew.setPaintTicks(true);
		sliderCrew.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				crewAmount = sliderCrew.getValue();
				lblNumberOfCrew.setText("Number of crew: " + crewAmount);
			}
		});
		sliderCrew.setValue(crewAmount);
		sliderCrew.setMinimum(minCrew);
		sliderCrew.setMaximum(maxCrew);
		sliderCrew.setMaximumSize(new Dimension(250,50));
		panelCrewAmount.add(sliderCrew);
		
		JLabel lblMaxCrew = new JLabel("Maximum Crew: " + maxCrew);
		lblMaxCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelCrewAmount.add(lblMaxCrew);
		
		Component horizontalGlue_6 = Box.createHorizontalGlue();
		panelCrewAmount.add(horizontalGlue_6);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_4);
		
		JLabel lblWhatWouldYou = new JLabel("What would you like your ship's name to be?");
		lblWhatWouldYou.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblWhatWouldYou.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.getContentPane().add(lblWhatWouldYou);
		
		txtShipName = new JTextField();
		txtShipName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtShipName.setText("Default McShippyface");
		frame.getContentPane().add(txtShipName);
		txtShipName.setColumns(10);
		txtShipName.setMaximumSize(new Dimension(200,30));
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		frame.getContentPane().add(verticalStrut_5);
		
		JPanel panelOptions = new JPanel();
		frame.getContentPane().add(panelOptions);
		panelOptions.setLayout(new BoxLayout(panelOptions, BoxLayout.X_AXIS));
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panelOptions.add(horizontalGlue_2);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiObject.setGUIContext(GUIContext.MAIN_MENU);
				finishedWindow();
			}
		});
		btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelOptions.add(btnBack);
		
		JButton btnContinue = new JButton("Continue to crew member creation");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiObject.setNumberOfDays(days);
				guiObject.setNumberOfCrew(crewAmount);
				guiObject.setShipName(txtShipName.getText());
				float piecesPerDay = Float.parseFloat(properties.getProperty("itemsPerDay"));
				int piecesRequired = (int) (piecesPerDay * days);
				guiObject.setPiecesRequired(piecesRequired);
				guiObject.setGUIContext(GUIContext.CREW_CREATION);
				guiObject.setLastGUIContext(GUIContext.START_GAME);
				finishedWindow();
			}
		});
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panelOptions.add(rigidArea);
		btnContinue.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnContinue.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelOptions.add(btnContinue);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panelOptions.add(horizontalGlue_3);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		frame.getContentPane().add(verticalGlue_1);
	}

}
