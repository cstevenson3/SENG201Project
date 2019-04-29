package main;

import java.io.Serializable;
import java.util.Properties;

public class Planet implements Serializable{
	
	private static final String PLANET_DIRECTORY = "planets/";
	
	private String name;
	private InventoryItem item;
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
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		if(name == null){
			this.name = "unnamed";
		}else{
			this.name = name;
		}
	}
	
	public InventoryItem getItem(){
		return this.item;
	}
	
	public void setItem(InventoryItem item){
		this.item = item;
	}
	
	public float getItemSearchOdds(){
		return this.itemSearchOdds;
	}
	
	public void setItemSearchOdds(float odds){
		this.itemSearchOdds = Utilities.clamp(odds, 0, 1);
	}
}