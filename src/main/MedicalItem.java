package main;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

public class MedicalItem extends InventoryItem implements Serializable, Cloneable{
	private static final String MEDICAL_ITEM_DIRECTORY = "medical_items/";
	
	private int healthIncrease;
	private ArrayList<String> diseasesCured;
	
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
	
	public boolean cures(Disease disease){
		return cures(disease.getName());
	}
	
	public boolean cures(String diseaseName){
		return diseasesCured.contains(diseaseName);
	}
	
	
	private static String getDirectory(String name) {
		return MEDICAL_ITEM_DIRECTORY + name + ".properties";
	}

	public int getHealthIncrease() {
		return healthIncrease;
	}

	public void setHealthIncrease(int healthIncrease) {
		this.healthIncrease = Utilities.clamp(healthIncrease, 0, Integer.MAX_VALUE);
	}
	
	@Override
	public String getAttributeDescription() {
		String result = getName() + " - This medical item increases health by " + healthIncrease + (diseasesCured.size()==0? "":" and cures the following diseases:\n");
		for(String disease:diseasesCured){
			result += disease + "\n";
		}
		return result;
	}
	
	public MedicalItem clone(){
		MedicalItem clone = (MedicalItem)Utilities.clone(this);
		return clone;
	}

	public static boolean exists(String itemName) {
		File file = new File(getDirectory(itemName));
		return file.exists();
	}

	@Override
	public String getType() {
		return "MedicalItem";
	}
}
