package main;

import java.awt.EventQueue;

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
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.JTextField;

/**
 * The consume menu window
 * @author Teren Gubb
 *
 */
public class ConsumeMenuWindow {

	private JFrame frame;
	private GUI guiObject;
	private GameState gameState;
	private JTextPane txtCrew;
	private JTextField txtCrewMember;
	private JTextField txtItem;
	private JTextField txtAmount;


	/**
	 * creates the ConsumeMenuWindow.
	 * @param incomingGui The gui object
	 */
	public ConsumeMenuWindow(GUI incomingGui) {
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
		guiObject.closeConsumeMenuWindow(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelConsumeAnItem = new JPanel();
		frame.getContentPane().add(panelConsumeAnItem);
		panelConsumeAnItem.setLayout(new BoxLayout(panelConsumeAnItem, BoxLayout.Y_AXIS));
		
		Component glue = Box.createGlue();
		panelConsumeAnItem.add(glue);
		
		JLabel lblConsumeAnItem = new JLabel("Consume an Item:");
		lblConsumeAnItem.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblConsumeAnItem.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelConsumeAnItem.add(lblConsumeAnItem);
		

		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panelConsumeAnItem.add(rigidArea);
		
		JPanel panelItemsCrew = new JPanel();
		panelConsumeAnItem.add(panelItemsCrew);
		panelItemsCrew.setLayout(new BoxLayout(panelItemsCrew, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelItemsCrew.add(horizontalGlue);
		
		JPanel panelItems = new JPanel();
		panelItemsCrew.add(panelItems);
		panelItems.setLayout(new BoxLayout(panelItems, BoxLayout.Y_AXIS));
		
		JLabel lblYouHaveThese_1 = new JLabel("You have these medical Items:");
		lblYouHaveThese_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblYouHaveThese_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelItems.add(lblYouHaveThese_1);
		
		for(MedicalItem medicalItem:gameState.getInventory().getMedicalItems().values()) {
			JLabel Label = new JLabel(medicalItem.getAttributeDescription());
			panelConsumeAnItem.add(Label);
		}
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		panelItems.add(rigidArea_2);
		
		JLabel lblYouHaveThese = new JLabel("You have these food items:");
		lblYouHaveThese.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelItems.add(lblYouHaveThese);
		lblYouHaveThese.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		for(FoodItem foodItem:gameState.getInventory().getFoodItems().values()) {
			JLabel Label = new JLabel(foodItem.getAttributeDescription());
			panelConsumeAnItem.add(Label);
		}
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		panelItemsCrew.add(rigidArea_3);
		
		JPanel panelCrew = new JPanel();
		panelItemsCrew.add(panelCrew);
		panelCrew.setLayout(new BoxLayout(panelCrew, BoxLayout.Y_AXIS));
		
		JLabel lblYourCrew = new JLabel("Your Crew is:");
		lblYourCrew.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblYourCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelCrew.add(lblYourCrew);
		
		txtCrew = new JTextPane();
		txtCrew.setBackground(UIManager.getColor("Button.background"));
		txtCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtCrew.setMaximumSize(new Dimension(400,200));
		txtCrew.setText(gameState.getCrewStatus());
		panelCrew.add(txtCrew);
		
		
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelItemsCrew.add(horizontalGlue_1);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panelConsumeAnItem.add(rigidArea_1);
		
		JLabel lblSpecifyTheCrew = new JLabel("Specify the crew member:");
		lblSpecifyTheCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSpecifyTheCrew.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelConsumeAnItem.add(lblSpecifyTheCrew);
		
		txtCrewMember = new JTextField();
		txtCrewMember.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtCrewMember.setBackground(UIManager.getColor("TextField.background"));
		txtCrewMember.setText("Name");
		txtCrewMember.setMaximumSize(new Dimension(100,30));
		panelConsumeAnItem.add(txtCrewMember);
		txtCrewMember.setColumns(10);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		panelConsumeAnItem.add(rigidArea_4);
		
		JLabel lblSpecifyTheItem = new JLabel("Specify the item to be consumed:");
		lblSpecifyTheItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSpecifyTheItem.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelConsumeAnItem.add(lblSpecifyTheItem);
		
		txtItem = new JTextField();
		txtItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtItem.setText("Item");
		panelConsumeAnItem.add(txtItem);
		txtItem.setColumns(10);
		txtItem.setMaximumSize(new Dimension(100,30));
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		panelConsumeAnItem.add(rigidArea_5);
		
		JLabel lblHowManyShould = new JLabel("How many should be consumed:");
		lblHowManyShould.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHowManyShould.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelConsumeAnItem.add(lblHowManyShould);
		
		txtAmount = new JTextField();
		txtAmount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtAmount.setText("Amount");
		txtAmount.setMaximumSize(new Dimension(100,30));
		panelConsumeAnItem.add(txtAmount);
		txtAmount.setColumns(10);
		
		Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
		panelConsumeAnItem.add(rigidArea_7);
		
		JLabel lblWarning = new JLabel("");
		lblWarning.setForeground(UIManager.getColor("Label.foreground"));
		lblWarning.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWarning.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelConsumeAnItem.add(lblWarning);
		
		JPanel panelButtons = new JPanel();
		panelConsumeAnItem.add(panelButtons);
				panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
		
		
				
				JButton btnReturnToCrew = new JButton("Return to crew menu");
				panelButtons.add(btnReturnToCrew);
				btnReturnToCrew.setAlignmentX(Component.CENTER_ALIGNMENT);
				btnReturnToCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
				btnReturnToCrew.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gameState.setViewContext(GameStateViewContext.CREW_MENU);
						finishedWindow();
					}
				});
				
				Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
				panelButtons.add(rigidArea_6);
				
				JButton btnPromptConsumption = new JButton("Prompt consumption");
				btnPromptConsumption.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						lblWarning.setText("");
						try {
							int quantity = Integer.parseInt(txtAmount.getText());
							try {
								gameState.haveCrewMemberConsumeItem(txtCrewMember.getText(), txtItem.getText(), quantity);
							} catch (ItemNotFoundException e3) {
								lblWarning.setText("The requested item to consume was not found.");
							} catch (InsufficientQuantityException e3) {
								lblWarning.setText("You do not have enough of that item to consume");
							} catch (NotConsumableException e3) {
								lblWarning.setText("This item is not consumable");
							} catch (OutOfActionsException e1) {
								lblWarning.setText("This crew member is out of actions, did not consume");
							}
						} catch (NumberFormatException e1) {
							lblWarning.setText("That is not a Number");
						}
					}
				});
				btnPromptConsumption.setFont(new Font("Tahoma", Font.PLAIN, 16));
				panelButtons.add(btnPromptConsumption);

		
		Component glue_1 = Box.createGlue();
		panelConsumeAnItem.add(glue_1);
		
	}

}
