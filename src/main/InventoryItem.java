package main;

import java.io.Serializable;

/**
 * An item which can be found in an inventory. This class tracks name, quantity, price
 * @author Cameron Stevenson
 *
 */
public abstract class InventoryItem implements Cloneable, Serializable{

	/**
	 * The name of this item
	 */
	private String name;
	/**
	 * The quantity of this item
	 */
	private int quantity;
	/**
	 * The price of this item
	 */
	private int price;
	
	/**
	 * The strings of item types
	 */
	private static final String[] itemTypes = {"FoodItem","MedicalItem","ShipPartItem"};
	
	/**
	 * Init an empty item
	 */
	public InventoryItem(){
		this.name = "unnamed";
		this.quantity = 0;
		this.price = 0;
	}
	
	/**
	 * Init an item with name, quantity, and price
	 * @param name The name of the item
	 * @param quantity The quantity of the item
	 * @param price The price of the item
	 */
	public InventoryItem(String name, int quantity, int price){
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	
	/**
	 * Get the name of the item
	 * @return The name of the item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the item
	 * @param name The name of the item
	 */
	public void setName(String name) {
		this.name = (name == null) ? "unnamed" : name;
	}

	/**
	 * Get the quantity of the item
	 * @return The quantity of the item
	 */
	public int getQuantity() {
		return quantity;
	}


	/**
	 * Set the quantity of the item
	 * @param quantity The quantity of the item
	 */
	public void setQuantity(int quantity) {
		this.quantity = Utilities.clamp(quantity, 0, Integer.MAX_VALUE);
	}


	/**
	 * Get the price of the item
	 * @return The price of the item
	 */
	public int getPrice() {
		return price;
	}


	/**
	 * Set the price of the item
	 * @param price The price of the item
	 */
	public void setPrice(int price) {
		this.price = Utilities.clamp(price, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Clone this item
	 * @return A clone of this item
	 */
	public InventoryItem clone(){
		InventoryItem clone = (InventoryItem)Utilities.clone(this);
		return clone;
	}

	/**
	 * Get a textual description of this item
	 * @return A textual description of this item
	 */
	public String getDescription() {
		return name + ": quantity (" + quantity + ") - price per item (" + price + ")";
	}
	
	/**
	 * A textual description of the specifics of this item
	 * @return A textual description of what this item does
	 */
	public abstract String getAttributeDescription();

	/**
	 * Increase the quantity of this item
	 * @param quantity The amount to increase quantity by
	 */
	public void increaseQuantity(int quantity) {
		this.quantity += quantity;
	}
	
	/**
	 * Work out what type of item a given name refers to, and load the appropriate item
	 * @param itemName The name of the item
	 * @return The item as loaded from its properties file
	 */
	public static InventoryItem findAndLoadItem(String itemName) {
		for(String itemType : itemTypes){
			switch(itemType){
			case "FoodItem":
				if (FoodItem.exists(itemName)){
					return (InventoryItem) (new FoodItem(itemName));
				}
				break;
			case "MedicalItem":
				if (MedicalItem.exists(itemName)){
					return (InventoryItem) (new MedicalItem(itemName));
				}
				break;
			case "ShipPartItem":
				return (InventoryItem) (new ShipPartItem(itemName));
			}
		}
		return null;
	}
	
	/**
	 * Get all item types
	 * @return An array of strings of item types
	 */
	public static String[] getItemTypes() {
		return itemTypes;
	}

	/**
	 * Consume some quantity of this item
	 * @param quantity The quantity to be consumed
	 * @throws InsufficientQuantityException if there is not enough to consume
	 */
	public void consume(int quantity) throws InsufficientQuantityException{
		if(this.quantity < quantity) {
			throw new InsufficientQuantityException();
		}
		this.quantity -= quantity;
	}

	/**
	 * Get the type of the instantiating object's class
	 * @return The type of the instantiating object's class
	 */
	public abstract String getType();
}
