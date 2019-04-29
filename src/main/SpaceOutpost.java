package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

public class SpaceOutpost implements Serializable{
	private static final String SPACE_OUTPOST_DIRECTORY = "space_outposts/";
	
	
	private String name;
	private HashMap<String, InventoryItem> itemsAvailable;
	
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

	private void setName(String name) {
		this.name = name == null ? "unnamed" : name;
	}

	public HashMap<String, InventoryItem> getItemsAvailable(){
		return itemsAvailable;
	}
	
	public boolean canPurchase(String itemName, int quantity){
		if(itemsAvailable.containsKey(itemName)){
			int quantityAvailable = itemsAvailable.get(itemName).getQuantity();
			if(quantityAvailable >= quantity){
				return true;
			}
		}
		return false;
	}
	
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
	
	private String getDirectory(String name) {
		return SPACE_OUTPOST_DIRECTORY + name + ".properties";
	}

	public String getName() {
		return name;
	}
	
	public String getDescription(){
		String result = name + " contains:" + "\n";
		for(InventoryItem item:itemsAvailable.values()){
			result += item.getDescription() + "\n";
		}
		return result;
	}
}
