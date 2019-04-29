package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

public class Disease implements Serializable{
	
	/**
	 * Where the disease property files are stored
	 */
	private static final String DISEASE_DIRECTORY = "diseases/";
	
	/**
	 * Name of the disease
	 */
	private String name;
	
	/**
	 * The amount an inflicted entity's health should decrease per day
	 */
	private int dailyHealthDecrease;
	
	/**
	 * Instantiates a Disease from a file containing its properties
	 * @param propertiesFile The file containing its properties, in Properties format
	 */
	public Disease(String propertiesFile){
		Properties properties = Utilities.loadPropertiesFile(DISEASE_DIRECTORY + propertiesFile);
		setName(properties.getProperty("name"));
		setDailyHealthDecrease(Integer.parseInt(properties.getProperty("dailyHealthDecrease")));
	}
	
	/**
	 * Creates a default disease
	 */
	public Disease(){
		setName("Unnamed Disease");
		setDailyHealthDecrease(0);
	}
	
	/**
	 * Name of the disease
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name of the disease
	 * @param name Name of the disease
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The amount an inflicted entity's health should decrease per day
	 */
	public int getDailyHealthDecrease() {
		return dailyHealthDecrease;
	}

	/**
	 * The amount an inflicted entity's health should decrease per day
	 * @param dailyHealthDecrease The amount an inflicted entity's health should decrease per day
	 */
	public void setDailyHealthDecrease(int dailyHealthDecrease) {
		this.dailyHealthDecrease = dailyHealthDecrease;
	}

	public static Disease getRandomDisease() {
		ArrayList<String> diseaseNames = Utilities.namesInDirectory(DISEASE_DIRECTORY);
		int index = ((int)(Math.random() * 1000)) % diseaseNames.size();
		String diseaseName = diseaseNames.get(index);
		return new Disease(diseaseName);
	}
}
