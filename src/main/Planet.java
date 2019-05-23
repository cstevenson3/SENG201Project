package main;

import java.io.Serializable;
import java.util.Properties;

/**
 * Planets can be traveled to and searched for items
 * @author Cameron Stevenson
 *
 */
public class Planet implements Serializable{
	
	/**
	 * Where the properties files describing planets are
	 */
	private static final String PLANET_DIRECTORY = "planets/";
	
	/**
	 * The name of the planet
	 */
	private String name;
	
	/**
	 * The item on this planet
	 */
	private InventoryItem item;
	
	/**
	 * The odds of finding this item when it is searched for
	 */
	private float itemSearchOdds;
	
	/**
	 * Create a planet from a properties file
	 * @param propertiesFile The name of the file containing its properties
	 */
	public Planet(String propertiesFile){
		this();
		Properties properties = Utilities.loadPropertiesFile(getDirectory(propertiesFile));
		setName(properties.getProperty("name"));
		String itemName = properties.getProperty("item");
		InventoryItem item = InventoryItem.findAndLoadItem(itemName);
		int itemQuantity = Integer.parseInt(properties.getProperty("itemQuantity"));
		item.setQuantity(itemQuantity);
		setItem(item);
		try{
			setItemSearchOdds(Float.parseFloat(properties.getProperty("itemSearchOdds")));
		}catch(NullPointerException e){
			//leave odds as default
		}
	}

	/**
	 * Create a planet from default settings
	 */
	public Planet() {
		Properties properties = Utilities.loadPropertiesFile(getDirectory("default"));
		setName(properties.getProperty("name"));
		try{
			setItemSearchOdds(Float.parseFloat(properties.getProperty("itemSearchOdds")));
		}catch(NullPointerException e){
			//System.out.println("If not set already please set default itemSearchOdds in " + getDirectory("default"));
		}
	}
	
	/**
	 * Get the file location of the properties file describing a planet with a given name
	 * @param name The name of the planet
	 * @return The file location
	 */
	private String getDirectory(String name){
		return PLANET_DIRECTORY + name + ".properties";
	}

	/**
	 * Searches the planet for its item, random chance of finding it. If the item is found it is removed from the planet
	 * @return null if no item is found, otherwise an InventoryItem
	 */
	public InventoryItem search(){
		if(this.item == null){
			return null;
		}else{
			boolean roll = itemSearchOdds > Math.random();
			if(roll){
				InventoryItem toReturn = this.item.clone();
				this.item = null;
				return toReturn;
			}else{
				return null;
			}
		}
	}
	
	/**
	 * Get the name of the planet
	 * @return The name of the planet
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Set the name of the planet
	 * @param name The name the planet should have
	 */
	public void setName(String name){
		if(name == null){
			this.name = "unnamed";
		}else{
			this.name = name;
		}
	}
	
	/**
	 * Get the item on the planet
	 * @return The item on the planet
	 */
	public InventoryItem getItem(){
		return this.item;
	}
	
	/**
	 * Set the item on the planet
	 * @param item The item which should be on the planet
	 */
	public void setItem(InventoryItem item){
		this.item = item;
	}
	
	/**
	 * Get the odds of finding the planet's item when searching
	 * @return The odds of finding the planet's item when searching
	 */
	public float getItemSearchOdds(){
		return this.itemSearchOdds;
	}
	
	/**
	 * Set the odds of finding the planet's item when searching
	 * @param odds The odds of finding the planet's item when searching
	 */
	public void setItemSearchOdds(float odds){
		this.itemSearchOdds = Utilities.clamp(odds, 0, 1);
	}
}