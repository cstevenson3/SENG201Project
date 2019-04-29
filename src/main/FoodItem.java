package main;

import java.io.File;
import java.io.Serializable;
import java.util.Properties;

public class FoodItem extends InventoryItem implements Serializable{
	private static final String FOOD_ITEM_DIRECTORY = "food_items/";
	
	private int hungerDecrease;
	
	public FoodItem(String propertiesFile){
		Properties properties = Utilities.loadPropertiesFile(getDirectory(propertiesFile));
		setName(properties.getProperty("name"));
		setPrice(Integer.parseInt(properties.getProperty("price")));
		setHungerDecrease(Integer.parseInt(properties.getProperty("hungerDecrease")));
	}

	public static String getDirectory(String name) {
		return FOOD_ITEM_DIRECTORY + name + ".properties";
	}

	public int getHungerDecrease() {
		return hungerDecrease;
	}

	public void setHungerDecrease(int hungerDecrease) {
		this.hungerDecrease = Utilities.clamp(hungerDecrease, 0, Integer.MAX_VALUE);
	}
	
	@Override
	public String getAttributeDescription() {
		String result = "This food item decreases hunger by " + hungerDecrease;
		return result;
	}
	
	public FoodItem clone(){
		FoodItem clone = (FoodItem)Utilities.clone(this);
		return clone;
	}
	
	public String toString(){
		return "FoodItem: name=" + getName() + ", quantity=" + getQuantity() + ", price=" + getPrice() + ", hungerDecrease=" + hungerDecrease;
	}

	public static boolean exists(String itemName) {
		File file = new File(getDirectory(itemName));
		return file.exists();
	}
}
