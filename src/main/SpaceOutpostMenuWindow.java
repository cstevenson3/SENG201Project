package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

public class SpaceOutpostMenuWindow {

	private JFrame frame;
	private GUI guiObject;
	private GameState gameState;
	private SpaceOutpost spaceOutpost;
	private JTextField txtItemToBuy;
	private JTextField txtNoToBuy;
	private JTextField txtItemName;
	private JTextField txtOutpostName;

	/**
	 * Create the SpaceOutpostMenuWindow.
	 * @param incomingGui 
	 */
	public SpaceOutpostMenuWindow(GUI incomingGui) {
		guiObject = incomingGui;
		gameState = guiObject.getGameState();
		spaceOutpost = gameState.getSpaceOutpost();
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
		guiObject.closeSpaceOutpostMenuWindow(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panelContent = new JPanel();
		panel.add(panelContent);
		panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.X_AXIS));
		
		JPanel panelOptions = new JPanel();
		panelContent.add(panelOptions);
		panelOptions.setLayout(new BoxLayout(panelOptions, BoxLayout.Y_AXIS));
		
		Component glue = Box.createGlue();
		panelOptions.add(glue);
		
		JLabel lblSpaceOutpostMenu = new JLabel("Space outpost menu");
		panelOptions.add(lblSpaceOutpostMenu);
		lblSpaceOutpostMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSpaceOutpostMenu.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panelOptions.add(rigidArea_1);
		
		JLabel lblYourInventory = new JLabel("Your inventory:");
		panelOptions.add(lblYourInventory);
		lblYourInventory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblYourInventory.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JTextPane txtpnInventory = new JTextPane();
		panelOptions.add(txtpnInventory);
		txtpnInventory.setBackground(UIManager.getColor("Label.background"));
		txtpnInventory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtpnInventory.setText(gameState.getInventory().getDescription());
		txtpnInventory.setMaximumSize(new Dimension(300,500));
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		panelOptions.add(rigidArea_5);
		
		//panelpanelItemPurchase constructor
		
		JPanel panelItemPurchase = new JPanel();
		
		//panelPurchaseConfirm constructor
		
		JPanel panelPurchaseConfirm = new JPanel();
		
		//panelItemAtt constructor
		
		JPanel panelItemAtt = new JPanel();
		
		//panelMoveToOutpost constructor
		
		JPanel panelMoveToOutpost = new JPanel();
		
		JButton btnReturnToCrew = new JButton("Return to crew menu");
		btnReturnToCrew.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelOptions.add(btnReturnToCrew);
		btnReturnToCrew.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnPurchaseAnItem = new JButton("Purchase an item");
		btnPurchaseAnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMoveToOutpost.setVisible(false);
				panelItemAtt.setVisible(false);
				panelPurchaseConfirm.setVisible(false);
				panelItemPurchase.setVisible(true);
			}
		});
		btnPurchaseAnItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelOptions.add(btnPurchaseAnItem);
		btnPurchaseAnItem.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnViewAnItems = new JButton("View an item's attributes");
		btnViewAnItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMoveToOutpost.setVisible(false);
				panelPurchaseConfirm.setVisible(false);
				panelItemPurchase.setVisible(false);
				panelItemAtt.setVisible(true);
			}
		});
		btnViewAnItems.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelOptions.add(btnViewAnItems);
		btnViewAnItems.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnMoveToA = new JButton("Move to a different space outpost");
		btnMoveToA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPurchaseConfirm.setVisible(false);
				panelItemPurchase.setVisible(false);
				panelItemAtt.setVisible(false);
				panelMoveToOutpost.setVisible(true);
			}
		});
		btnMoveToA.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnMoveToA.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelOptions.add(btnMoveToA);
		
		Component glue_1 = Box.createGlue();
		panelOptions.add(glue_1);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		panelContent.add(rigidArea_2);
		

		panelItemPurchase.setVisible(false);
		panelContent.add(panelItemPurchase);
		panelItemPurchase.setLayout(new BoxLayout(panelItemPurchase, BoxLayout.Y_AXIS));
		
		JLabel lblNameTheItem = new JLabel("Name the item you would like to purchase:");
		lblNameTheItem.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNameTheItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelItemPurchase.add(lblNameTheItem);
		
		txtItemToBuy = new JTextField();
		txtItemToBuy.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtItemToBuy.setMaximumSize(new Dimension(200,30));
		panelItemPurchase.add(txtItemToBuy);
		txtItemToBuy.setColumns(10);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		panelItemPurchase.add(rigidArea_3);
		
		JLabel lblHowManyWould = new JLabel("How many would you like to purchase?");
		lblHowManyWould.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHowManyWould.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelItemPurchase.add(lblHowManyWould);
		
		txtNoToBuy = new JTextField();
		txtNoToBuy.setMaximumSize(new Dimension(100,30));
		panelItemPurchase.add(txtNoToBuy);
		txtNoToBuy.setColumns(10);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		panelItemPurchase.add(rigidArea_4);
		
		//lblItemWarn constructor
		
		JLabel lblItemWarn = new JLabel();
		
		//lblPurchaseXY constructor
		JLabel lblPurchaseXY = new JLabel();
		
		JButton btnPurchase = new JButton("Purchase");
		btnPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblNameTheItem.setText("Name the item you would like to purchase:");
				lblItemWarn.setText("");
				
				String itemName = txtItemToBuy.getText();
				if(spaceOutpost.getItemsAvailable().containsKey(itemName) && spaceOutpost.getItemsAvailable().get(itemName).getQuantity() > 0){
					try {
						int purchaseQuantity = Integer.parseInt(txtNoToBuy.getText());
						int totalCost = spaceOutpost.getItemsAvailable().get(itemName).getPrice() * purchaseQuantity;
						if ((purchaseQuantity > spaceOutpost.getItemsAvailable().get(itemName).getQuantity()) == false) {
							lblPurchaseXY.setText("Purchase " + purchaseQuantity + " " + itemName + "(s) at a total cost of " + totalCost + "?");
							panelItemPurchase.setVisible(false);
							panelPurchaseConfirm.setVisible(true);
						}else {
							lblItemWarn.setText("That quantity is more than the Outpost has.");
						}
					}catch (NumberFormatException e1) {
							lblItemWarn.setText("That is not a valid number.");
					}
				}else{
					System.out.println("This item is not available, try another:");
				}
			}
		});
		
		// lblItemWarn variables
		
		lblItemWarn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblItemWarn.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelItemPurchase.add(lblItemWarn);
		btnPurchase.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPurchase.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelItemPurchase.add(btnPurchase);
		
		// panelPurchaseConfirm variables
		
		panelPurchaseConfirm.setVisible(false);
		panelContent.add(panelPurchaseConfirm);
		panelPurchaseConfirm.setLayout(new BoxLayout(panelPurchaseConfirm, BoxLayout.Y_AXIS));
		
		// lblPurchaseXY variables
		
		lblPurchaseXY.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPurchaseXY.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelPurchaseConfirm.add(lblPurchaseXY);
		
		Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
		panelPurchaseConfirm.add(rigidArea_6);
		
		JLabel lblPurchaseWarn = new JLabel("");
		lblPurchaseWarn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPurchaseWarn.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelPurchaseConfirm.add(lblPurchaseWarn);
		
		JPanel panelYesNo = new JPanel();
		panelPurchaseConfirm.add(panelYesNo);
		panelYesNo.setLayout(new BoxLayout(panelYesNo, BoxLayout.X_AXIS));
		
		JButton btnNo = new JButton("No");
		btnNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelYesNo.add(btnNo);
		
		Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
		panelYesNo.add(rigidArea_7);
		
		JButton btnYes = new JButton("Yes");
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemName = txtItemToBuy.getText();
				int purchaseQuantity = Integer.parseInt(txtNoToBuy.getText());
				if(gameState.purchaseItemFromCurrentSpaceOutpost(itemName, purchaseQuantity)){
					lblPurchaseWarn.setText("Item purchased successfully");
				}else{
					lblPurchaseWarn.setText("Item purchase failed, insufficient funds");
				}
			}
		});
		btnYes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelYesNo.add(btnYes);
		
		// panelItemAtt variables
		
		panelItemAtt.setVisible(false);
		panelContent.add(panelItemAtt);
		panelItemAtt.setLayout(new BoxLayout(panelItemAtt, BoxLayout.Y_AXIS));
		
		JLabel lblWhichItemWould = new JLabel("Which item would you like to view?");
		lblWhichItemWould.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblWhichItemWould.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelItemAtt.add(lblWhichItemWould);
		
		JTextPane txtpnItemDescrip = new JTextPane();
		txtpnItemDescrip.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtpnItemDescrip.setBackground(UIManager.getColor("Label.background"));
		txtpnItemDescrip.setMaximumSize(new Dimension(200,30));
		panelItemAtt.add(txtpnItemDescrip);
		
		Component rigidArea_8 = Box.createRigidArea(new Dimension(20, 20));
		panelItemAtt.add(rigidArea_8);
		
		txtItemName = new JTextField();
		txtItemName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtItemName.setMaximumSize(new Dimension(200,30));
		panelItemAtt.add(txtItemName);
		txtItemName.setColumns(10);
		
		Component rigidArea_9 = Box.createRigidArea(new Dimension(20, 20));
		panelItemAtt.add(rigidArea_9);
		
		JButton btnView = new JButton("View");
		btnView.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtpnItemDescrip.setText("");
				if(spaceOutpost.getItemsAvailable().containsKey(txtItemName.getText())){
					txtpnItemDescrip.setText(spaceOutpost.getItemsAvailable().get(txtItemName.getText()).getAttributeDescription());
				}else{
					txtpnItemDescrip.setText("This item is not available, try another:");
				}
			}
		});
		btnView.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelItemAtt.add(btnView);
		
		//panelMoveToOutpost variables
		
		panelMoveToOutpost.setVisible(false);
		panelContent.add(panelMoveToOutpost);
		panelMoveToOutpost.setLayout(new BoxLayout(panelMoveToOutpost, BoxLayout.Y_AXIS));
		
		JLabel lblSpaceOutpostsYou = new JLabel("Space outposts you can move to:");
		lblSpaceOutpostsYou.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSpaceOutpostsYou.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelMoveToOutpost.add(lblSpaceOutpostsYou);
		
		for(SpaceOutpost otherOutpost:gameState.getSpaceOutposts().values()){
			JLabel label = new JLabel(otherOutpost.getName());
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			label.setFont(new Font("Tahoma", Font.PLAIN, 16));
			panelMoveToOutpost.add(label);
		}
		
		Component rigidArea_10 = Box.createRigidArea(new Dimension(20, 20));
		panelMoveToOutpost.add(rigidArea_10);
		
		JLabel lblEnterTheNameOf = new JLabel("Enter the name of the space outpost you would like to move to:");
		lblEnterTheNameOf.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblEnterTheNameOf.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelMoveToOutpost.add(lblEnterTheNameOf);
		
		txtOutpostName = new JTextField();
		txtOutpostName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtOutpostName.setMaximumSize(new Dimension(200,30));
		panelMoveToOutpost.add(txtOutpostName);
		txtOutpostName.setColumns(10);
		
		JButton btnTravel = new JButton("Travel!");
		btnTravel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameState.setSpaceOutpost(txtOutpostName.getText());
			}
		});
		btnTravel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnTravel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelMoveToOutpost.add(btnTravel);
		
		for(SpaceOutpost otherOutpost:gameState.getSpaceOutposts().values()){
			JLabel label = new JLabel(otherOutpost.getName());
			label.setFont(new Font("Tahoma", Font.PLAIN, 16));
			panelMoveToOutpost.add(label);
		}
		
		JPanel panelOutpostInfo = new JPanel();
		panelContent.add(panelOutpostInfo);
		panelOutpostInfo.setLayout(new BoxLayout(panelOutpostInfo, BoxLayout.Y_AXIS));
		
		JLabel lblOutpost = new JLabel("Outpost Info:");
		lblOutpost.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblOutpost.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelOutpostInfo.add(lblOutpost);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panelOutpostInfo.add(rigidArea);
		
		JLabel lblYouAreAt = new JLabel("You are at space outpost " + spaceOutpost.getName());
		panelOutpostInfo.add(lblYouAreAt);
		lblYouAreAt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblYouAreAt.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JTextPane txtOutpostDescription = new JTextPane();
		panelOutpostInfo.add(txtOutpostDescription);
		txtOutpostDescription.setBackground(UIManager.getColor("Label.background"));
		txtOutpostDescription.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtOutpostDescription.setText(spaceOutpost.getDescription());
		txtOutpostDescription.setMaximumSize(new Dimension(300,500));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelContent.add(horizontalGlue);
		btnReturnToCrew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameState.setViewContext(GameStateViewContext.CREW_MENU);
				finishedWindow();
			}
		});
		
		if(gameState.getSpaceOutposts().values().size() > 1){
			
		}
	}

}
