package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Chooses which version of the program to run
 */
public class Main {
	
	/**
	 * Chooses which version of the program to run
	 * @param args Program arguments
	 */
	public static void main(String[] args){
		
		
		Properties properties = Utilities.loadPropertiesFile("gameProperties.properties");
		boolean useGUI = Boolean.parseBoolean(properties.getProperty("useGUI"));
		
		if(useGUI) {
			System.out.println("GUI not implemented");
		}else {
			CLI cli = new CLI();
			cli.run();
		}
	}
}
