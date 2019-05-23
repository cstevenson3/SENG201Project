package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

/**
 * SpaceOutposts contain items for sale
 * @author Cameron Stevenson
 *
 */
public class SpaceOutpost implements Serializable{
	
	/**
	 * Where the properties files describing space outposts are
	 */
	private static final String SPACE_OUTPOST_DIRECTORY = "space_outposts/";
	
	/**
	 * The name of the outpost
	 */
	private String name;
	
	/**
	 * A HashMap from item names to inventory items of items for sale
	 */
	private HashMap<String, InventoryItem> itemsAvailable;
	
	
	/**
	 * Init SpaceOutpost from a properties file describing it
	 * @param propertiesFile The name of the space outpost
	 */
	public SpaceOutpost(String propertiesFile){
		itemsAvailable = new HashMap<String, InventoryItem>();
		
		Properties properties = Utilities.loadPropertiesFile(getDirectory(propertiesFile));
		setName(properties.getProperty("name"));
		
		//load in items
		for(String itemType : InventoryItem.getItemTypes()){
			String itemsCommaSV = properties.getProperty(itemType);
			if(itemsCommaSV != null){
				ArrayList<String> itemsColonSV = new ArrayList<String>(Arrays.asList(itemsCommaSV.split(",")));
				for(String itemColonSV:itemsColonSV){
					String[] itemProperties = itemColonSV.split(":");
					String itemName = itemProperties[0];
					int quantity = Integer.parseInt(itemProperties[1]);
					
					InventoryItem item = null;
					switch(itemType){
					case "FoodItem":
						item = new FoodItem(itemName);
						break;
					case "MedicalItem":
						item = new MedicalItem(itemName);
						break;
					}
					
					item.setQuantity(quantity);
					itemsAvailable.put(itemName, item);
				}
			}
		}
	}

	/**
	 * Set the name of this space outpost
	 * @param name The name of this space outpost
	 */
	private void setName(String name) {
		this.name = name == null ? "unnamed" : name;
	}

	/**
	 * Get a HashMap from item names to inventory items for sale
	 * @return A HashMap from item names to inventory items for sale
	 */
	public HashMap<String, InventoryItem> getItemsAvailable(){
		return itemsAvailable;
	}
	
	/**
	 * Check if an item is in enough quantity to be purchased
	 * @param itemName The name of the item
	 * @param quantity The quantity to be purchased 
	 * @return true if purchasable, false otherwise
	 */
	public boolean canPurchase(String itemName, int quantity){
		if(itemsAvailable.containsKey(itemName)){
			int quantityAvailable = itemsAvailable.get(itemName).getQuantity();
			if(quantityAvailable >= quantity){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Purchase an item from this space outpost
	 * @param name The name of the item
	 * @param quantity The quantity to purchase
	 * @param inventory The inventory to add the purchased item to
	 * @return true if successful, false otherwise
	 */
	public boolean purchaseItem(String name, int quantity, Inventory inventory){
		if(canPurchase(name, quantity)){
			int price = itemsAvailable.get(name).getPrice();
			int totalCost = price * quantity;
			if(totalCost <= inventory.getMoney()){
				int quantityAvailable = itemsAvailable.get(name).getQuantity();
				itemsAvailable.get(name).setQuantity(quantityAvailable - quantity);
				inventory.decreaseMoney(totalCost);
				InventoryItem newItem = itemsAvailable.get(name).clone();
				newItem.setQuantity(quantity);
				inventory.addItem(newItem);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the file location of the properties file describing a space outpost
	 * @param name The name of the space outpost
	 * @return The file location
	 */
	private String getDirectory(String name) {
		return SPACE_OUTPOST_DIRECTORY + name + ".properties";
	}

	/**
	 * Get the name of this space outpost
	 * @return The name of this space outpost
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get a textual description of this space outpost
	 * @return A textual description of this space outpost
	 */
	public String getDescription(){
		String result = name + " contains:" + "\n";
		for(InventoryItem item:itemsAvailable.values()){
			result += item.getDescription() + "\n";
		}
		return result;
	}
}
