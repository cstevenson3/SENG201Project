package main;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Keeps track of money and items owned by the crew
 * @author Cameron Stevenson
 *
 */
public class Inventory implements Serializable{
	
	/**
	 * All items in possession of the crew
	 */
	private HashMap<String, InventoryItem> items;
	
	/**
	 * The money the crew currently has
	 */
	private int money;
	
	/**
	 * Init a default Inventory
	 */
	public Inventory(){
		money = 0;
		items = new HashMap<String, InventoryItem>();
	}
	
	/**
	 * Init an inventory with some starting money
	 * @param money Money to start with
	 */
	public Inventory(int money){
		this.money = money;
		items = new HashMap<String, InventoryItem>();
	}
	
	/**
	 * Get a textual description of the inventory
	 * @return A textual description of the inventory
	 */
	public String getDescription(){
		String result = "Inventory:\nMoney: " + money + "\n";
		for(InventoryItem item:items.values()){
			result += item.getDescription();
		}
		return result;
	}

	/**
	 * Get the amount of money in this inventory
	 * @return The amount of money in this inventory
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Decrease the amount of money if possible
	 * @param amount The amount of money to decrease by
	 * @return true if possible, false otherwise
	 */
	public boolean decreaseMoney(int amount) {
		if(amount <= money){
			money -= amount;
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Add an item to this inventory
	 * @param inventoryItem The item to be added
	 */
	public void addItem(InventoryItem inventoryItem) {
		String name = inventoryItem.getName();
		int quantity = inventoryItem.getQuantity();
		if(items.containsKey(name)){
			items.get(name).increaseQuantity(quantity);
		}else{
			items.put(name, inventoryItem);
		}
	}

	/**
	 * Remove one of a random item in this inventory
	 * @return The name of the item removed, null if nothing was removed
	 */
	public String removeRandomItem() {
		if(items.size()!=0){
			int index = ((int) (Math.random() * 1000)) % items.size();
			String itemName = (String) items.keySet().toArray()[index];
			if(items.get(itemName).getType().equals("ShipPartItem")) {
				return null;
			}
			items.get(itemName).setQuantity(items.get(itemName).getQuantity() - 1);
			return itemName;
		}
		return null;
	}
	
	/**
	 * Get the medical items in this inventory
	 * @return A HashMap from item names to medical items
	 */
	public HashMap<String, MedicalItem> getMedicalItems() {
		HashMap<String, MedicalItem> result = new HashMap<String, MedicalItem>();
		for(InventoryItem item:items.values()) {
			if(item instanceof MedicalItem) {
				result.put(item.getName(), (MedicalItem) item);
			}
		}
		return result;
	}
	
	/**
	 * Get the food items in this inventory
	 * @return A HashMap from item names to food items
	 */
	public HashMap<String, FoodItem> getFoodItems() {
		HashMap<String, FoodItem> result = new HashMap<String, FoodItem>();
		for(InventoryItem item:items.values()) {
			if(item instanceof FoodItem) {
				result.put(item.getName(), (FoodItem) item);
			}
		}
		return result;
	}
	
	/**
	 * Get all items in this inventory
	 * @return A HashMap from item names to medical items
	 */
	public HashMap<String, InventoryItem> getItems() {
		return items;
	}

	/**
	 * Remove some quantity of some item from the inventory
	 * @param itemName The name of the item
	 * @param quantity The quantity to be consumed
	 * @return The item consumed
	 * @throws ItemNotFoundException if item was not found in the inventory
	 * @throws InsufficientQuantityException if the inventory does not have enough of the item
	 */
	public InventoryItem consume(String itemName, int quantity) throws ItemNotFoundException, InsufficientQuantityException{
		if(!items.containsKey(itemName)) {
			throw new ItemNotFoundException();
		}
		try{
			items.get(itemName).consume(quantity);
			InventoryItem newItem = items.get(itemName).clone();
			newItem.setQuantity(quantity);
			if(items.get(itemName).getQuantity() == 0) {
				items.remove(itemName);
			}
			return newItem;
		}catch (InsufficientQuantityException e) {
			throw e;
		}
	}
}
