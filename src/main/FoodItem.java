package main;

import java.io.File;
import java.io.Serializable;
import java.util.Properties;

public class FoodItem extends InventoryItem implements Serializable{
	
	/**
	 * Where food item properties are stored
	 */
	private static final String FOOD_ITEM_DIRECTORY = "food_items/";
	
	/**
	 * How much one of this item decreases hunger
	 */
	private int hungerDecrease;
	
	/**
	 * Init a FoodItem from a properties file
	 * @param propertiesFile The file describing the food item's properties
	 */
	public FoodItem(String propertiesFile){
		Properties properties = Utilities.loadPropertiesFile(getDirectory(propertiesFile));
		setName(properties.getProperty("name"));
		setPrice(Integer.parseInt(properties.getProperty("price")));
		setHungerDecrease(Integer.parseInt(properties.getProperty("hungerDecrease")));
	}

	/**
	 * Get the location of a properties file describing a food item
	 * @param name The name of the food item
	 * @return The file location
	 */
	public static String getDirectory(String name) {
		return FOOD_ITEM_DIRECTORY + name + ".properties";
	}

	/**
	 * Get the amount this food item decreases hunger by
	 * @return The amount this food item decreases hunger by
	 */
	public int getHungerDecrease() {
		return hungerDecrease;
	}

	/**
	 * Set the amount this food item decreases hunger by
	 * @param hungerDecrease The amount this food item should decrease hunger by
	 */
	public void setHungerDecrease(int hungerDecrease) {
		this.hungerDecrease = Utilities.clamp(hungerDecrease, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Get a textual description of what this item specifically does
	 * @return A textual description of what this item specifically does
	 */
	@Override
	public String getAttributeDescription() {
		String result = getName() + " - This food item decreases hunger by " + hungerDecrease;
		return result;
	}
	
	/**
	 * Clone this food item
	 * @return A clone of this food item
	 */
	public FoodItem clone(){
		FoodItem clone = (FoodItem)Utilities.clone(this);
		return clone;
	}
	
	/**
	 * Get a string describing this item
	 * @return A string describing this item
	 */
	public String toString(){
		return "FoodItem: name=" + getName() + ", quantity=" + getQuantity() + ", price=" + getPrice() + ", hungerDecrease=" + hungerDecrease;
	}

	/**
	 * Check if a food item with the given name exists
	 * @param itemName The name of the item
	 * @return true if the item's description exists, false otherwise
	 */
	public static boolean exists(String itemName) {
		File file = new File(getDirectory(itemName));
		return file.exists();
	}
	
	/**
	 * Get the type of this InventoryItem
	 * @return "FoodItem"
	 */
	@Override
	public String getType() {
		return "FoodItem";
	}
}
