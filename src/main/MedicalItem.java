package main;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Medical items can heal crew members and cure diseases
 * @author Cameron Stevenson
 *
 */
public class MedicalItem extends InventoryItem implements Serializable, Cloneable{
	/**
	 * Where the properties files describing medical items are stored
	 */
	private static final String MEDICAL_ITEM_DIRECTORY = "medical_items/";
	
	/**
	 * How much this medical item increases a crew member's health when used
	 */
	private int healthIncrease;
	/**
	 * The names of diseases this medical item cures
	 */
	private ArrayList<String> diseasesCured;
	
	/**
	 * Init a MedicalItem from a properties file describing it
	 * @param propertiesFile The name of the medical item
	 */
	public MedicalItem(String propertiesFile){
		Properties properties = Utilities.loadPropertiesFile(getDirectory(propertiesFile));
		setName(properties.getProperty("name"));
		setPrice(Integer.parseInt(properties.getProperty("price")));
		setHealthIncrease(Integer.parseInt(properties.getProperty("healthIncrease")));
		
		diseasesCured = new ArrayList<String>();
		String diseasesCuredString = properties.getProperty("diseasesCured");
		if(diseasesCuredString != null && !diseasesCuredString.equals("")){
			String[] CSV = diseasesCuredString.split(",");
			for(String diseaseCured : CSV){
				diseasesCured.add(diseaseCured);
			}
		}
	}
	
	/**
	 * Checks whether this medical item cures a given disease
	 * @param disease The disease to check
	 * @return true if this item cures this disease, false otherwise
	 */
	public boolean cures(Disease disease){
		return cures(disease.getName());
	}
	
	/**
	 * Checks whether this medical item cures a given disease
	 * @param disease The name of the disease to check
	 * @return true if this item cures this disease, false otherwise
	 */
	public boolean cures(String diseaseName){
		return diseasesCured.contains(diseaseName);
	}
	
	/**
	 * Get the file location of the properties file describing a medical item
	 * @param name The name of the item
	 * @return The file location
	 */
	private static String getDirectory(String name) {
		return MEDICAL_ITEM_DIRECTORY + name + ".properties";
	}

	/**
	 * Get the increase in health this medical item gives
	 * @return The increase in health this medical item gives
	 */
	public int getHealthIncrease() {
		return healthIncrease;
	}

	/**
	 * Set the amount this medical item increases health when used
	 * @param healthIncrease The amount this medical item should increase health when used
	 */
	public void setHealthIncrease(int healthIncrease) {
		this.healthIncrease = Utilities.clamp(healthIncrease, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Get a textual description of this medical item
	 * @return A textual description of this medical item
	 */
	@Override
	public String getAttributeDescription() {
		String result = getName() + " - This medical item increases health by " + healthIncrease + (diseasesCured.size()==0? "":" and cures the following diseases:\n");
		for(String disease:diseasesCured){
			result += disease + "\n";
		}
		return result;
	}
	
	/**
	 * Clone this medical item
	 * @return A clone of this medical item
	 */
	public MedicalItem clone(){
		MedicalItem clone = (MedicalItem)Utilities.clone(this);
		return clone;
	}

	/**
	 * Check if an item's description exists
	 * @param itemName The name of the item
	 * @return true if the item description exists, false otherwise
	 */
	public static boolean exists(String itemName) {
		File file = new File(getDirectory(itemName));
		return file.exists();
	}

	/**
	 * Get the type of this InventoryItem
	 * @return "MedicalItem"
	 */
	@Override
	public String getType() {
		return "MedicalItem";
	}
}
